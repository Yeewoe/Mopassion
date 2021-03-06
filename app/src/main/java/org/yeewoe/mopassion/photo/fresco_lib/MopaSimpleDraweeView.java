package org.yeewoe.mopassion.photo.fresco_lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.file.service.FileService;
import org.yeewoe.mopassion.template.media.TMediaImage;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MopaSimpleDraweeView extends SimpleDraweeView {

    private static final int GET_URL_SUCCESS = 0;
    private static final int GET_URL_FAIL = 1;

    // 默认的宽高比例
    public static final float DEF_RATIO = 2.0f;

    // TODO 渐变优化
    public static final int FADE_DURATION = 0;

    private Context mContext;
    private Postprocessor mPostProcessor;               // 后处理器（在图片下载完成后对图片进行处理-截取/缩放等）
    private ControllerListener mControllerListener;     // 下载监听器
    private ResizeOptions mResizeOptions;               // 图片缩放，优先级非常高；缩放的尺寸并不是按照指定的尺寸，而是根据内部来计算出一个合适的值；
    private ScalingUtils.ScaleType mDraweeViewScaleType;// 图片缩放类型，默认为CENTER_CROP
    private Drawable mProgressBar;                      // 加载进度
    private Drawable mPlaceholderDrawable;              // 加载背景
    private Drawable mGifChartOverlay;                  // GIF的覆盖物图
    private Drawable mLongChartOverlay;                 // 长图的覆盖物图
    private Drawable mNumberChartOverlay;               // 数字的覆盖物图
    private boolean mAutoPlayAnimations = true;        // 是否自动播放GIF图-自动播放
    private boolean isCutGif = false;                    // 是否裁剪GIF
    private double mHeightRatio;                        // 宽高比例
    private int mTargetImageSize = -1;                  // 指定的图片尺寸
    private int mIsReplacePngBg2TargetColor = -1;      // 是否处理PNG图片的透明背景为指定颜色
    private ImageRequest.ImageType mImageType;          // 图片类型-默认
    private ImageFormat mImageFormat = ImageFormat.JPEG;// 图片类型-默认JPEG
    private ImageRequest.RequestLevel mLowestPermittedRequestLevel;// 图片加载的请求类型-默认FULL_FETCH
    private String uriPathTag;                          // 加载图片的TAG，用于不重复加载图片
    private String filekeyPathTag;                          // 加载图片的TAG，用于不重复加载图片

    private FileService fileService = new FileService();    // 加载服务

    private static Executor executor = Executors.newSingleThreadExecutor();

    public MopaSimpleDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        init(context);
    }

    public MopaSimpleDraweeView(Context context) {
        super(context);
        init(context);
    }

    public MopaSimpleDraweeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public MopaSimpleDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {
            this.mContext = context;
            mPlaceholderDrawable = new ColorDrawable(getContext().getResources().getColor(R.color.gray_light));
        }
        mPostProcessor = new MopaBasePostProcessor(this);
        mImageType = ImageRequest.ImageType.DEFAULT;
        mControllerListener = new DefaultBaseControllerListener();
        mDraweeViewScaleType = ScalingUtils.ScaleType.CENTER_CROP;
        mLowestPermittedRequestLevel = ImageRequest.RequestLevel.FULL_FETCH;
    }

    /**
     * 是否设置GIF标识
     */
    public MopaSimpleDraweeView setGifChartIdentify(final boolean isShowGifIdentify) {
        if (isShowGifIdentify) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mGifChartOverlay = mContext.getDrawable(R.mipmap.identify_gif);
            } else {
                mGifChartOverlay = mContext.getResources().getDrawable(R.mipmap.identify_gif);
            }
        } else {
            mGifChartOverlay = null;
        }
        return this;
    }

    /**
     * 设置长图标识
     */
    public MopaSimpleDraweeView setLongChartIdentify(final int imageWidth, final int imageHeight) {
        return setLongChartIdentify(imageHeight > imageWidth * DEF_RATIO);
    }

    /**
     * 设置数字标识，内部会判断是否大于1
     */
    public MopaSimpleDraweeView setNumberChartIdentify(int number) {
        if (number > 1) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.identify_number);
            byte[] chunk = bitmap.getNinePatchChunk();
            if (NinePatch.isNinePatchChunk(chunk)) {
                mNumberChartOverlay = new MopaNinePatchDrawable(getResources(), bitmap, chunk, NinePatchChunk.deserialize(chunk).mPaddings, null, number + "");
            } else {
                mNumberChartOverlay = null;
            }
        } else {
            mNumberChartOverlay = null;
        }
        return this;
    }

    /**
     * 加载url图片
     */
    public void setDraweeViewFileKey(final String filekey) {
        setControllerByTypeFileKey(filekey, "NORMAL");
    }

    /**
     * 加载url图片
     */
    public void setRoundDraweeViewFileKey(final String filekey) {
        setControllerByTypeFileKey(filekey, "ROUND");
    }

    /**
     * 加载url图片
     */
    public MopaSimpleDraweeView setDraweeViewUrl(final String url) {
        return setDraweeViewUri(Uri.parse(url));
    }

    /**
     * 加载本地资源图片
     */
    public MopaSimpleDraweeView setDraweeViewResId(final int resId) {
        return setDraweeViewUri(getUriByResId(resId));
    }

    /**
     * 加载uri图片
     */
    public MopaSimpleDraweeView setDraweeViewUri(final Uri uri) {
        return setControllerByType(uri, "NORMAL");
    }

    /**
     * 加载url圆形图片
     */
    public MopaSimpleDraweeView setRoundDraweeViewUrl(final String url) {
        return setRoundDraweeViewUri(Uri.parse(url));
    }

    /**
     * 加载本地资源圆形图片
     */
    public MopaSimpleDraweeView setRoundDraweeViewResId(final int resId) {
//        return setRoundDraweeViewUri(getUriByResId(resId));
        setRoundingHierarchy();
        setImageURI(getUriByResId(resId));
        return this;
    }

    /**
     * 加载uri圆形图片
     */
    public MopaSimpleDraweeView setRoundDraweeViewUri(final Uri uri) {
        return setControllerByType(uri, "ROUND");
    }

    /**
     * 根据图片宽高设置控件尺寸
     */
    public MopaSimpleDraweeView setWidthAndHeight(final float width, final float height) {
        float picRatio = height / width;
        if (picRatio >= DEF_RATIO) {
            picRatio = DEF_RATIO;
        }
        this.setHeightRatio(picRatio);
        return this;
    }

    public MopaSimpleDraweeView setMaxWidthLayoutParams(final int maxWidth, final int width, final int height) {
        float widthRatio = (float) width / (float) maxWidth;
        float tmpheight = ((float) height / widthRatio);

        float ratio = tmpheight / maxWidth;
        if (ratio > DEF_RATIO) {
            ratio = DEF_RATIO;
        }
        tmpheight = maxWidth * ratio;

        // 设置单张图片的布局
        ViewGroup.LayoutParams layoutparams = getLayoutParams();
        layoutparams.height = (int) tmpheight;
        layoutparams.width = maxWidth;
        setLayoutParams(layoutparams);
        return this;
    }

    /**
     * 指定的PNG图片的透明背景颜色为白色
     */
    public MopaSimpleDraweeView replacePNGBackground2White() {
        return replacePNGBackground(Color.WHITE);
    }

    /**
     * 如果不为-1说明需要替换PNG的透明背景色
     */
    public int isReplacePNGBackground() {
        return mIsReplacePngBg2TargetColor;
    }

    /**
     * 是否需要替换PNG图片的透明背景为指定的颜色，需要则设置指定的颜色值
     */
    public MopaSimpleDraweeView replacePNGBackground(int targetColor) {
        if (targetColor == -1) {
            throw new RuntimeException("颜色值不能指定为-1");
        }
        this.mIsReplacePngBg2TargetColor = targetColor;
        return this;
    }


    /**
     * 截取图片的大小
     * TODO：待重构
     */
    public int getTargetImageSize() {
        return mTargetImageSize;
    }

    /**
     * TODO: 待重构
     */
    public void setTargetImageSize(int targetImageSize) {
        this.mTargetImageSize = targetImageSize;
    }

    public ImageFormat getImageFormat() {
        return mImageFormat;
    }

    /**
     * 是否剪切GIF的第一帧
     */
    public void setIsCutGif(boolean isCutGif) {
        this.isCutGif = isCutGif;
    }

    /**
     * 是否自动播放GIF，默认False
     */
    public MopaSimpleDraweeView setAutoPlayAnimations(boolean mAutoPlayAnimations) {
        this.mAutoPlayAnimations = mAutoPlayAnimations;
        return this;
    }

    public MopaSimpleDraweeView setControllerListener(ControllerListener mControllerListener) {
        this.mControllerListener = mControllerListener;
        return this;
    }

    public MopaSimpleDraweeView setResizeOptions(ResizeOptions resizeOptions) {
        this.mResizeOptions = resizeOptions;
        return this;
    }

    public MopaSimpleDraweeView setProgressBar(Drawable mProgressBar) {
        this.mProgressBar = mProgressBar;
        return this;
    }

    public MopaSimpleDraweeView setDraweeViewScaleType(ScalingUtils.ScaleType mDraweeViewScaleType) {
        this.mDraweeViewScaleType = mDraweeViewScaleType;
        return this;
    }

    public MopaSimpleDraweeView setPlaceholderDrawable(Drawable placeholderDrawable) {
        this.mPlaceholderDrawable = placeholderDrawable;
        return this;
    }

    public MopaSimpleDraweeView setLowestPermittedRequestLevel(ImageRequest.RequestLevel mLowestPermittedRequestLevel) {
        this.mLowestPermittedRequestLevel = mLowestPermittedRequestLevel;
        return this;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////私有方法///////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 图片加载完成时，为控件设置Tag
     */
    private class DefaultBaseControllerListener extends BaseControllerListener<ImageInfo> {
        @Override
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            super.onFinalImageSet(id, imageInfo, animatable);
            MopaSimpleDraweeView.this.setTag(R.id.uriPath, uriPathTag);
            MopaSimpleDraweeView.this.setTag(R.id.filekeyPath, filekeyPathTag);
        }
    }

    // 正常
    private void setHierarchy() {
        if (mContext == null) {
            throw new IllegalArgumentException("Context is not null");
        }

        final GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(mContext.getResources())
//                .setFadeDuration(this.getFadeDuration())
                .setOverlay(this.getMyOverlay())
                .setActualImageScaleType(this.getDraweeViewScaleType())
                .setProgressBarImage(this.getProgressBar(), ScalingUtils.ScaleType.CENTER_INSIDE)
                .setPlaceholderImage(this.getPlaceholderDrawable(), ScalingUtils.ScaleType.CENTER_CROP)
                .build();
        super.setHierarchy(hierarchy);
    }

    // 圆角
    protected void setRoundingHierarchy() {
        setRoundingHierarchy(15f);
    }

    // 圆角
    protected void setRoundingHierarchy(float radius) {
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(radius);

        final GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(mContext.getResources())
                .setFadeDuration(this.getFadeDuration())
                .setOverlay(this.getMyOverlay())
                .setActualImageScaleType(this.getDraweeViewScaleType())
                .setProgressBarImage(this.getProgressBar(), ScalingUtils.ScaleType.CENTER_INSIDE)
                .setPlaceholderImage(this.getPlaceholderDrawable(), ScalingUtils.ScaleType.CENTER_CROP)
                .setRoundingParams(roundingParams)
                .build();
        super.setHierarchy(hierarchy);
    }

    // 圆形
    protected void setCircleHierarchy() {
        if (mContext == null) {
            throw new IllegalArgumentException("Context is not null");
        }

        Drawable defaultIcon;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            defaultIcon = mContext.getDrawable(R.mipmap.round_default_icon);
        } else {
            defaultIcon = mContext.getResources().getDrawable(R.mipmap.round_default_icon);
        }

        final GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(mContext.getResources())
                .setFadeDuration(getFadeDuration())
                .setPlaceholderImage(defaultIcon)
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setRoundingParams(RoundingParams.asCircle())
                .build();
        super.setHierarchy(hierarchy);
    }

    private ImageRequest getImageRequest(MopaSimpleDraweeView view, Uri uri) {
        switch (mImageFormat) {
            case JPEG:
            case PNG:
                return ImageRequestBuilder
                        .newBuilderWithSource(uri)
                        .setImageType(view.getImageType())
                        .setLowestPermittedRequestLevel(view.getLowestPermittedRequestLevel())
                        .setPostprocessor(view.getPostProcessor())//修改图片
                        .setResizeOptions(view.getResizeOptions())
                        .setAutoRotateEnabled(true)
                        .setLocalThumbnailPreviewsEnabled(true)
                        .setProgressiveRenderingEnabled(false)
                        .build();
            case GIF:
                if (uri.getScheme().toLowerCase().contains("file") && isCutGif()) {
                    // 针对本地Gif预览时做特殊处理，裁剪出第一帧并显示
                    File file = new File(uri.getPath());
                    File cutFile = Utils.getCopyFile(file);
                    Uri newUri = new Uri.Builder().scheme("file").path(cutFile.getAbsolutePath()).build();
                    return ImageRequestBuilder
                            .newBuilderWithSource(newUri)
                            .setLowestPermittedRequestLevel(view.getLowestPermittedRequestLevel())
                            .setPostprocessor(view.getPostProcessor())
                            .setResizeOptions(view.getResizeOptions())
                            .setAutoRotateEnabled(true).build();
                } else {
                    return ImageRequestBuilder
                            .newBuilderWithSource(uri)
                            .setLowestPermittedRequestLevel(view.getLowestPermittedRequestLevel())
                            .setAutoRotateEnabled(true)
                            .build();
                }
        }
        throw new RuntimeException("must have a ImageRequest");
    }

    private void setControllerByTypeFileKey(final String filekey, final String type) {
        filekeyPathTag = filekey;
//        if (noRepeatLoadImageFilekey(filekeyPathTag)) {
        if (true) {
            final String url = KeyUrlHelper.getInstance().loadUrl(filekey); // 读取关联
            final Handler handler = new Handler() {
                @Override public void handleMessage(Message msg) {
                    if (msg.what == GET_URL_SUCCESS) {
                        String url = (String) msg.obj;
                        KeyUrlHelper.getInstance().saveUrl(filekey, url); // 保存关联
                        uriPathTag = url;
                        setImageFormat(uriPathTag);
                        innerSetControllerByType(Uri.parse(url), type);
                    } else if (msg.what == GET_URL_FAIL) {
                        // TODO 失败处理
                    }
                }
            };

            if (!TextUtils.isEmpty(url)) {
                DataSource<Boolean> inDiskCache = Fresco.getImagePipeline().isInDiskCache(getImageRequest(this, Uri.parse(url)));

                DataSubscriber<Boolean> subscriber = new BaseDataSubscriber<Boolean>() {
                    @Override protected void onNewResultImpl(DataSource<Boolean> dataSource) {
                        if (!dataSource.isFinished()) {
                            return;
                        }
                        boolean isInCache = dataSource.getResult();

                        if (isInCache) {
                            Message msg = Message.obtain();
                            msg.what = GET_URL_SUCCESS;
                            msg.obj = url;
                            handler.sendMessage(msg);
                        } else {
                            getUrl(filekey, url, handler, type);
                        }
                    }

                    @Override protected void onFailureImpl(DataSource<Boolean> dataSource) {

                    }
                };
                inDiskCache.subscribe(subscriber, executor);
            } else {
                getUrl(filekey, url, handler, type);
            }

        }
    }

    private void getUrl(String filekey, String url, final Handler handler, String type) {
        if (!TextUtils.isEmpty(url) && url.startsWith("file://")) {
            Message msg = Message.obtain();
            msg.what = GET_URL_SUCCESS;
            msg.obj = url;
            handler.sendMessage(msg);
            return ;
        }
        fileService.getDownloadUrl(filekey, "", new Callback() {
            @Override public <T> void callback(CallbackInfo<T> info) {
                Message msg = Message.obtain();
                if (info.bError) {
                    msg.what = GET_URL_FAIL;
                    msg.arg1 = info.errorCode;
                } else {
                    msg.what = GET_URL_SUCCESS;
                    msg.obj = info.mT;
                }
                handler.sendMessage(msg);
            }
        });
    }

    private MopaSimpleDraweeView setControllerByType(final Uri uri, final String type) {
        uriPathTag = uri.toString();
        this.setImageFormat(uriPathTag);
        if (noRepeatLoadImage(uriPathTag)) {
            innerSetControllerByType(uri, type);
        }
        return this;
    }

    private void innerSetControllerByType(Uri uri, String type) {
        if ("NORMAL".equals(type)) {
            setHierarchy();
        } else if ("ROUND".equals(type)) {
            setRoundingHierarchy();
        } else if ("CIRCLE".equals(type)) {
            setCircleHierarchy();
        }

        final AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(this.getAutoPlayAnimations())//自动播放图片动画
                .setControllerListener(this.getControllerListener())
                .setImageRequest(getImageRequest(this, uri))
                .setOldController(this.getController())
                .build();
        super.setController(controller);
    }

    /**
     * 获取遮盖物
     * 数字 > GIF > 长图
     */
    private Drawable getMyOverlay() {
        if (null != mNumberChartOverlay) {
            return mNumberChartOverlay;
        } else if (null != mGifChartOverlay) {
            return mGifChartOverlay;
        } else if (null != mLongChartOverlay) {
            return mLongChartOverlay;
        }
        return new ColorDrawable(Color.TRANSPARENT);
    }

    private ControllerListener getControllerListener() {
        return mControllerListener;
    }

    private ScalingUtils.ScaleType getDraweeViewScaleType() {
        return mDraweeViewScaleType;
    }

    private int getFadeDuration() {
        return FADE_DURATION;
    }

    private Drawable getPlaceholderDrawable() {
        return mPlaceholderDrawable;
    }

    private ImageRequest.RequestLevel getLowestPermittedRequestLevel() {
        return mLowestPermittedRequestLevel;
    }

    private ImageRequest.ImageType getImageType() {
        return mImageType;
    }

    private Postprocessor getPostProcessor() {
        return mPostProcessor;
    }

    private ResizeOptions getResizeOptions() {
        return mResizeOptions;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mHeightRatio > 0.0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) (width * mHeightRatio);
            super.setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private boolean getAutoPlayAnimations() {
        return mAutoPlayAnimations;
    }

    private Drawable getProgressBar() {
        return mProgressBar;
    }

    /**
     * 判定Tag和Url是否相等，相等代表图片已经加载过，不需要从新加载
     */
    private boolean noRepeatLoadImage(String imgUrl) {
        return !(TextUtils.isEmpty(imgUrl) || TextUtils.isEmpty(this.getTag(R.id.uriPath) + "")) && !(this.getTag(R.id.uriPath) + "").equals(imgUrl);
    }

    private boolean noRepeatLoadImageFilekey(String filekey) {
        return !(TextUtils.isEmpty(filekey) || TextUtils.isEmpty(this.getTag(R.id.filekeyPath) + "")) && !(this.getTag(R.id.filekeyPath) + "").equals(filekey);
    }

    /**
     * 是否设置长图标识
     */
    public MopaSimpleDraweeView setLongChartIdentify(final boolean isLongChartIdentify) {
        if (isLongChartIdentify) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mLongChartOverlay = mContext.getDrawable(R.mipmap.identify_long);
            } else {
                mLongChartOverlay = mContext.getResources().getDrawable(R.mipmap.identify_long);
            }
        } else {
            mLongChartOverlay = null;
        }
        return this;
    }

    private Uri getUriByResId(int resId) {

        // 增加对资源id类型的图片类型判断
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), resId, opts);
        if (opts.outMimeType != null && opts.outMimeType.equals("image/png")) {
            mImageFormat = ImageFormat.PNG;
        }

        return Uri.parse("res://" + getContext().getPackageName() + "/" + resId);
    }

    private boolean isCutGif() {
        return isCutGif;
    }

    private void setHeightRatio(double ratio) {
        if (ratio != mHeightRatio) {
            mHeightRatio = ratio;
            requestLayout();
        }
    }

    private void setImageFormat(final String url) {
        if (url.toLowerCase().endsWith("gif")) {
            mImageFormat = ImageFormat.GIF;
        } else if (url.toLowerCase().endsWith("jpeg") || url.toLowerCase().endsWith("jpg")) {
            mImageFormat = ImageFormat.JPEG;
        } else if (url.toLowerCase().endsWith("png")) {
            mImageFormat = ImageFormat.PNG;
        }
    }

    public void setImageMedia(final TMediaImage image) {
        if (image == null || !Checks.check(image.fileKey)) {
            return ;
        }

        setDraweeViewFileKey(image.fileKey);
    }

    public void setRoundDraweeViewMedia(final TMediaImage image) {
        if (image == null || !Checks.check(image.fileKey)) {
            return ;
        }

        setRoundDraweeViewFileKey(image.fileKey);
    }

}
