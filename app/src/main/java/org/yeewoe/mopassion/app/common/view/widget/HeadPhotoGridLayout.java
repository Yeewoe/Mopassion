package org.yeewoe.mopassion.app.common.view.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.presenter.MopaPresenter;
import org.yeewoe.mopassion.app.common.view.dialog.MopaSelectDialog;
import org.yeewoe.mopassion.app.common.view.interfaces.IActivityView;
import org.yeewoe.mopassion.constants.AppConstants;
import org.yeewoe.mopassion.photo.fresco_lib.PhotoThumbSimpleDraweeView;
import org.yeewoe.mopassion.template.media.TMediaFactory;
import org.yeewoe.mopassion.template.media.TMediaImage;
import org.yeewoe.mopassion.utils.IntentManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 缩略图照片grid layout
 * Created by wyw on 2016/5/10.
 */
public class HeadPhotoGridLayout extends GridLayout {

    private List<TMediaImage> mMediaImageList = new ArrayList<>();
    private List<File> mFileList = new ArrayList<>();
    private HashMap<String, File> keyFileHashMap = new HashMap<>();
    private int maxCount = -1;
    private int displayWidth;
    private boolean canAdd;
    private MopaPresenter mopaPresenter;

    public HeadPhotoGridLayout(Context context) {
        super(context);
        init();
    }


    public HeadPhotoGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttr(context, attrs);
    }


    public HeadPhotoGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttr(context, attrs);
    }

    public void setActivityView(IActivityView activityView) {
        mopaPresenter.setActivitiyView(activityView);
    }

    private void init() {
        mopaPresenter = new MopaPresenter() {

            @Override public void onDestroy() {

            }

            @Override protected void handleCompress(File pic) {
                super.handleCompress(pic);
                addPhoto(pic);
            }
        };

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        displayWidth = wm.getDefaultDisplay().getWidth() - getPaddingLeft() - getPaddingRight();
        setColumnCount(AppConstants.HEAD_GRID_MAX_COLUMN_COUNT);
        if (AppConstants.HEAD_GRID_MAX_RAW_COUNT >= 0) {
            setRowCount(AppConstants.HEAD_GRID_MAX_RAW_COUNT);
        }

        if (AppConstants.HEAD_GRID_MAX_RAW_COUNT >= 0) {
            maxCount = AppConstants.HEAD_GRID_MAX_COLUMN_COUNT * AppConstants.HEAD_GRID_MAX_RAW_COUNT;
        }

        setOrientation(HORIZONTAL);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.HeadPhotoGridLayout);
        int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.HeadPhotoGridLayout_canAdd:
                    canAdd = typedArray.getBoolean(i, false);
                    break;
            }
        }
        typedArray.recycle();
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
     */
    public void addPhoto(File pic) {
        if (pic == null || (maxCount >= 0 && mMediaImageList.size() >= maxCount)) {
            return;
        }
        setVisibility(View.VISIBLE);
        TMediaImage media = TMediaFactory.getInstance().toTMediaImage(pic);
        mFileList.add(pic);
        keyFileHashMap.put(media.fileKey, pic);
        mMediaImageList.add(media);
        initSize(mMediaImageList.size());
        PhotoThumbSimpleDraweeView childAt = (PhotoThumbSimpleDraweeView) getChildAt(mMediaImageList.size() - 1);
        resetThumbSimpleDraweeView(childAt);
        childAt.setRoundDraweeViewMedia(media);
        initAdd(mMediaImageList.size());
    }

    /**
     * 设置图片
     */
    public void setPhotos(List<TMediaImage> mediaImageList) {
        if (mediaImageList == null) {
            mediaImageList = new ArrayList<>();
        }

        mMediaImageList = mediaImageList;
        setVisibility(View.VISIBLE);
        initSize(mediaImageList.size());
        for (int i = 0; (i < mediaImageList.size() && maxCount < 0) ||
                (i < mediaImageList.size() && maxCount >= 0 && i < maxCount); i++) {
            PhotoThumbSimpleDraweeView childAt = (PhotoThumbSimpleDraweeView) getChildAt(i);
            resetThumbSimpleDraweeView(childAt);
            childAt.setRoundDraweeViewMedia(mediaImageList.get(i));
        }
        initAdd(mediaImageList.size());
    }

    /**
     * 获取图片Media列表
     */
    public List<TMediaImage> getMediaImageList() {
        return mMediaImageList;
    }

    /**
     * 获取图片文件列表
     */
    public List<File> getFileList() {
        return mFileList;
    }

    /**
     * 处理获取到的照片
     */
    public boolean handleActivityResult(int requestCode, int resultCode, Intent data) {
        return mopaPresenter.handleActivityResult(requestCode, resultCode, data);
    }

    // 初始化子view，复用旧的，创建新的
    private void initSize(int size) {
        if (canAdd) {
            size += 1; // 加上添加按钮
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (i < size) {
                // 显示
                getChildAt(i).setVisibility(View.VISIBLE);
            } else {
                // 不显示
                getChildAt(i).setVisibility(View.GONE);
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
    }

    // 初始化添加按钮
    private void initAdd(int index) {
        if (canAdd) {
            PhotoThumbSimpleDraweeView addView = (PhotoThumbSimpleDraweeView) getChildAt(index);
            if (maxCount >= 0 && index >= maxCount) {
                addView.setVisibility(View.GONE);
            } else {
                addView.setVisibility(View.VISIBLE);
                addView.setOnClickListener(new AddClicListener());
                addView.setRoundDraweeViewResId(R.drawable.ic_photo_add);
            }
        }
    }

    // 构建ThumbSimpleDraweeView
    @NonNull private PhotoThumbSimpleDraweeView initThumbSimpleDraweeView(int i) {
        PhotoThumbSimpleDraweeView result = new PhotoThumbSimpleDraweeView(getContext());
        int length = (displayWidth - getResources().getDimensionPixelOffset(R.dimen.head_grid_span) * 6) / 4;
        LayoutParams layoutParams = new LayoutParams(new MarginLayoutParams(length, length));
        layoutParams.setGravity(Gravity.CENTER);
        if (i % AppConstants.HEAD_GRID_MAX_COLUMN_COUNT != 0) {
            layoutParams.leftMargin = getResources().getDimensionPixelOffset(R.dimen.head_grid_span);
        }
        if (i % AppConstants.HEAD_GRID_MAX_COLUMN_COUNT != (AppConstants.HEAD_GRID_MAX_COLUMN_COUNT - 1)) {
            layoutParams.rightMargin = getResources().getDimensionPixelOffset(R.dimen.head_grid_span);
        }
        layoutParams.topMargin = getResources().getDimensionPixelOffset(R.dimen.head_grid_span);
        layoutParams.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.head_grid_span);
        result.setLayoutParams(layoutParams);
        result.setTag(i);

        return result;
    }

    // 初始化ThumbSimpleDraweeView
    private void resetThumbSimpleDraweeView(PhotoThumbSimpleDraweeView result) {
        // TODO test
        result.setOnClickListener(new PhotoClickListener());
//        result.setRoundDraweeViewUrl("http://img1.imgtn.bdimg.com/it/u=1819655436,1406018065&fm=21&gp=0.jpg");
    }

    private void deletePhoto(int i) {
        TMediaImage remove = mMediaImageList.remove(i);
        mFileList.remove(keyFileHashMap.get(remove.fileKey));
        setPhotos(mMediaImageList);
    }

    class PhotoClickListener implements OnClickListener {

        @Override public void onClick(View v) {
            final int i = (int) v.getTag();
            final TMediaImage selectImage = mMediaImageList.get(i);
            if (!canAdd) {
                IntentManager.Photo.intentToPhotoView(getContext(), selectImage);
            } else {
                MopaSelectDialog dialog = new MopaSelectDialog(getContext(), R.array.modify_head_item_arrays, new MopaSelectDialog.OnClickListener() {
                    @Override public void onClick(MopaSelectDialog dialog, int which) {
                        switch (which) {
                            case 0:
                                IntentManager.Photo.intentToPhotoView(getContext(), selectImage);
                                break;
                            case 1:
                                // 删除
                                deletePhoto(i);
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }

    }

    class AddClicListener implements OnClickListener {

        @Override public void onClick(View v) {
            MopaSelectDialog dialog = new MopaSelectDialog(getContext(), R.array.common_picture_item_arrays, new MopaSelectDialog.OnClickListener() {
                @Override public void onClick(MopaSelectDialog dialog, int which) {
                    switch (which) {
                        case 0:
                            mopaPresenter.openCamera();
                            break;
                        case 1:
                            mopaPresenter.openGallery();
                            break;
                    }
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}
