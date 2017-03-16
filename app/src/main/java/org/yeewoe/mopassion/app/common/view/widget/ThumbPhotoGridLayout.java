package org.yeewoe.mopassion.app.common.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;

import com.facebook.drawee.drawable.ScalingUtils;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.photo.fresco_lib.PhotoThumbSimpleDraweeView;
import org.yeewoe.mopassion.template.media.TMediaFactory;
import org.yeewoe.mopassion.template.media.TMediaImage;
import org.yeewoe.mopassion.utils.IntentManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 缩略图照片grid layout
 * Created by wyw on 2016/5/10.
 */
public class ThumbPhotoGridLayout extends GridLayout implements View.OnClickListener {

    private static final int MAX_COLUMN_COUNT = 3;
    private static final int MAX_RAW_COUNT = 3;

    /** 用于控制永远只显示一张 **/
    private static final boolean JUST_ONLY = true;

    boolean firstOnlyZoomIn = false;
    private List<TMediaImage> mMediaImageList = new ArrayList<>();
    private List<File> mFileList = new ArrayList<>();
    private int maxCount;

    public ThumbPhotoGridLayout(Context context) {
        super(context);
        init();
    }


    public ThumbPhotoGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttr(context, attrs);
    }


    public ThumbPhotoGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttr(context, attrs);
    }

    private void init() {
        setColumnCount(MAX_COLUMN_COUNT);
        setRowCount(MAX_RAW_COUNT);
        maxCount = MAX_COLUMN_COUNT * MAX_RAW_COUNT;

        setOrientation(HORIZONTAL);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.ThumbPhotoGridLayout);
        int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.ThumbPhotoGridLayout_firstOnlyZoomIn:
                    firstOnlyZoomIn = typedArray.getBoolean(
                            R.styleable.ThumbPhotoGridLayout_firstOnlyZoomIn, false);
                    break;
            }
        }
        typedArray.recycle();

        if (JUST_ONLY) {
            firstOnlyZoomIn = true;
        }
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    /**
     * 添加图片
     * @param pic
     */
    public void addPhoto(File pic) {
        if (pic == null || mMediaImageList.size() >= maxCount) {
            return;
        }
        setVisibility(View.VISIBLE);
        TMediaImage media = TMediaFactory.getInstance().toTMediaImage(pic);
        mFileList.add(pic);
        mMediaImageList.add(media);
        initSize(mMediaImageList.size());
        PhotoThumbSimpleDraweeView childAt = (PhotoThumbSimpleDraweeView) getChildAt(mMediaImageList.size() - 1);
        resetThumbSimpleDraweeView(childAt);
        childAt.setImageMedia(media);
    }

    /**
     * 设置图片
     */
    public void setPhotos(List<TMediaImage> mediaImageList) {
        mMediaImageList = mediaImageList;
        if (!Checks.check(mediaImageList)) {
            setVisibility(View.GONE);
        } else {
            if (JUST_ONLY) {
                mediaImageList = mediaImageList.subList(0, 1);
            }

            setVisibility(View.VISIBLE);
        }
        initSize(mediaImageList.size());
        for (int i = 0; i < mediaImageList.size() && i < maxCount; i++) {
            PhotoThumbSimpleDraweeView childAt = (PhotoThumbSimpleDraweeView) getChildAt(i);
            resetThumbSimpleDraweeView(childAt);
            childAt.setImageMedia(mediaImageList.get(i));
        }
    }

    // 初始化子view，复用旧的，创建新的
    private void initSize(int size) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            setLayoutParam(childAt);

            if (i < size) {
                // 显示
                childAt.setVisibility(View.VISIBLE);
            } else {
                // 不显示
                childAt.setVisibility(View.GONE);
            }
        }

        // 需要新增
        if (childCount < size) {
            int countNew = size - childCount;
            for (int i = 0; i < countNew; i++) {
                PhotoThumbSimpleDraweeView photoThumbSimpleDraweeView = initThumbSimpleDraweeView(childCount + i);
                addView(photoThumbSimpleDraweeView);
            }
        }

        // 开启了单张缩放模式，而且图片数量为1时，做缩放处理
        if (firstOnlyZoomIn && size == 1) {
            setZoomInSetting(getChildAt(0));
        }
    }

    // 构建ThumbSimpleDraweeView
    @NonNull private PhotoThumbSimpleDraweeView initThumbSimpleDraweeView(int i) {
        PhotoThumbSimpleDraweeView result = new PhotoThumbSimpleDraweeView(getContext());
        setLayoutParam(result);
        result.setTag(i);
        result.setOnClickListener(this);

        return result;
    }

    private void setLayoutParam(View view) {
        setLayoutParam((PhotoThumbSimpleDraweeView) view);
    }

    private void setLayoutParam(PhotoThumbSimpleDraweeView result) {
        LayoutParams layoutParams = new LayoutParams(new MarginLayoutParams(getResources().getDimensionPixelOffset(R.dimen.thumb_photo_width), getResources().getDimensionPixelOffset(R.dimen.thumb_photo_height)));
        layoutParams.topMargin = getResources().getDimensionPixelOffset(R.dimen.common_padding_left_right_3dp);
        layoutParams.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.common_padding_left_right_3dp);
        layoutParams.leftMargin = getResources().getDimensionPixelOffset(R.dimen.common_padding_left_right_3dp);
        layoutParams.rightMargin = getResources().getDimensionPixelOffset(R.dimen.common_padding_left_right_3dp);
        result.setLayoutParams(layoutParams);
        result.setDraweeViewScaleType(ScalingUtils.ScaleType.CENTER_CROP);

    }

    private void setZoomInSetting(View result) {
        LayoutParams layoutParams = new LayoutParams(new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        layoutParams.topMargin = getResources().getDimensionPixelOffset(R.dimen.common_padding_left_right_3dp);
        layoutParams.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.common_padding_left_right_3dp);
        layoutParams.leftMargin = getResources().getDimensionPixelOffset(R.dimen.common_padding_left_right_3dp);
        layoutParams.rightMargin = getResources().getDimensionPixelOffset(R.dimen.common_padding_left_right_3dp);
        result.setMinimumHeight(getResources().getDimensionPixelOffset(R.dimen.thumb_zoom_photo_min_height));
        result.setMinimumWidth(getResources().getDimensionPixelOffset(R.dimen.thumb_zoom_photo_width));
        result.setLayoutParams(layoutParams);

        PhotoThumbSimpleDraweeView photoThumbSimpleDraweeView = (PhotoThumbSimpleDraweeView) result;
        photoThumbSimpleDraweeView.setDraweeViewScaleType(ScalingUtils.ScaleType.FIT_START);
    }

    // 初始化ThumbSimpleDraweeView
    private void resetThumbSimpleDraweeView(PhotoThumbSimpleDraweeView result) {
        result.setDraweeViewUrl("");
    }

    @Override public void onClick(View v) {
        int i = (int) v.getTag();
        TMediaImage selectImage = mMediaImageList.get(i);
        IntentManager.Photo.intentToPhotoView(getContext(), selectImage);
    }

    public List<TMediaImage> getMediaImageList() {
        return mMediaImageList;
    }

    public List<File> getFileList() {
        return mFileList;
    }
}
