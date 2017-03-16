package org.yeewoe.mopassion.photo.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopassion.app.file.service.FileService;
import org.yeewoe.mopassion.photo.fresco_lib.KeyUrlHelper;
import org.yeewoe.mopassion.photo.fresco_lib.LoadingProgressDrawable;
import org.yeewoe.mopassion.photo.photoview_lib.PhotoView;
import org.yeewoe.mopassion.template.media.TMediaImage;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MopaPhotoView extends PhotoView {
    private static final int GET_URL_SUCCESS = 0;
    private static final int GET_URL_FAIL = 1;

    private DraweeHolder<GenericDraweeHierarchy> mDraweeHolder;
    private FileService fileService = new FileService();
    private Executor executor = Executors.newSingleThreadExecutor();

    public MopaPhotoView(Context context) {
        this(context, null);
    }

    public MopaPhotoView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public MopaPhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        selfInit();
    }

    private void selfInit() {
        if (mDraweeHolder == null) {
            final GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
                    .setProgressBarImage(new LoadingProgressDrawable(getContext())).build();

            mDraweeHolder = DraweeHolder.create(hierarchy, getContext());
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mDraweeHolder.onDetach();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mDraweeHolder.onAttach();
    }

    @Override
    protected boolean verifyDrawable(Drawable dr) {
        super.verifyDrawable(dr);
        return dr == mDraweeHolder.getHierarchy().getTopLevelDrawable();
    }

    @Override
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        mDraweeHolder.onDetach();
    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        mDraweeHolder.onAttach();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDraweeHolder.onTouchEvent(event) || super.onTouchEvent(event);
    }

    public void setImageUri(final TMediaImage tMediaImage, final ResizeOptions options) {
        final String url = KeyUrlHelper.getInstance().loadUrl(tMediaImage.fileKey); // 读取关联
        final Handler handler = new Handler() {
            @Override public void handleMessage(Message msg) {
                if (msg.what == GET_URL_SUCCESS) {
                    String url = (String) msg.obj;
                    KeyUrlHelper.getInstance().saveUrl(tMediaImage.fileKey, url); // 保存关联
                    setImageUri(url, options);
                } else if (msg.what == GET_URL_FAIL) {
                    // TODO 失败处理
                }
            }
        };

        if (!TextUtils.isEmpty(url)) {
            DataSource<Boolean> inDiskCache = Fresco.getImagePipeline().isInDiskCache(Uri.parse(url));

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
                        getUrl(tMediaImage, url, handler);
                    }
                }

                @Override protected void onFailureImpl(DataSource<Boolean> dataSource) {

                }
            };
            inDiskCache.subscribe(subscriber, executor);
        } else {
            getUrl(tMediaImage, url, handler);
        }
    }

    private void getUrl(TMediaImage tMediaImage, String url, final Handler handler) {
        if (!TextUtils.isEmpty(url) && url.startsWith("file://")) {
            Message msg = Message.obtain();
            msg.what = GET_URL_SUCCESS;
            msg.obj = url;
            handler.sendMessage(msg);
        }
        fileService.getDownloadUrl(tMediaImage.fileKey, "", new Callback() {
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

    public void setImageUri(String uri, ResizeOptions options) {

        final ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
                .setResizeOptions(options)
                .setAutoRotateEnabled(true)
                .build();
        final ImagePipeline imagePipeline = Fresco.getImagePipeline();
        final DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, this);
        final AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(mDraweeHolder.getController())
                .setAutoPlayAnimations(true)
                .setImageRequest(imageRequest)
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);

                        CloseableReference<CloseableImage> imageCloseableReference = null;
                        try {
                            imageCloseableReference = dataSource.getResult();
                            if (imageCloseableReference != null) {
                                final CloseableImage image = imageCloseableReference.get();
                                if (image != null && image instanceof CloseableStaticBitmap) {
                                    CloseableStaticBitmap closeableStaticBitmap = (CloseableStaticBitmap) image;
                                    final Bitmap bitmap = closeableStaticBitmap.getUnderlyingBitmap();
                                    if (bitmap != null) {
                                        setImageBitmap(bitmap);
                                    }
                                }
                            }
                        } finally {
                            dataSource.close();
                            CloseableReference.closeSafely(imageCloseableReference);
                        }
                    }
                })
                .build();
        mDraweeHolder.setController(controller);

        // TODO 此处注释防止缓存读取导致图片显示异常
//        setImageDrawable(mDraweeHolder.getTopLevelDrawable());
    }
}
