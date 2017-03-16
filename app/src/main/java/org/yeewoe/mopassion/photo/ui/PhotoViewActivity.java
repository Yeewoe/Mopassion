package org.yeewoe.mopassion.photo.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.view.acitivty.MopaActivity;
import org.yeewoe.mopassion.app.common.view.builder.TitleBuilder;
import org.yeewoe.mopassion.photo.photoview_lib.PhotoViewAttacher;
import org.yeewoe.mopassion.template.media.TMediaImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 图片查看器
 */
public class PhotoViewActivity extends MopaActivity<PhotoViewPresenter> implements IPhotoView {

    private static final String ISLOCKED_ARG = "isLocked";
    public static final String EXTRA_IMAGES = "extra_image";

    @Bind(R.id.viewpager_main) ViewPager mViewPager;
    private List<TMediaImage> images;
    private TitleBuilder mTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override protected boolean isScaleIn() {
        return true;
    }

    @Override protected int getViewRootResId() {
        return R.layout.activity_photo_view;
    }

    @Override protected void bindIntent() {
        images = getIntent().getParcelableArrayListExtra(EXTRA_IMAGES);
        if (images == null) {
            images = new ArrayList<>();
        }

        // 移除null的参数
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i) == null) {
                images.remove(i--);
            }
        }
    }

    @Override protected void initTitle() {
        mTitle = new TitleBuilder(this);
        mTitle.setVisible(View.GONE).changeCenterTxt("").getLeftImgBtn().setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                finish();
            }
        });
    }

    @Override protected void bindLister() {

    }

    @Override protected PhotoViewPresenter getPresenter() {
        return new PhotoViewPresenter(this);
    }

    @Override protected void bindData() {
        mViewPager.setAdapter(new SamplePagerAdapter());
    }

    @Override protected void bindData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            boolean isLocked = savedInstanceState.getBoolean(ISLOCKED_ARG, false);
            ((HackyViewPager) mViewPager).setLocked(isLocked);
        }
    }

    @Override protected void afterOnStart() {

    }

    @Override protected void afterOnResume() {

    }

    @Override protected void beforeOnPause() {

    }

    @Override protected void beforOnStop() {

    }

    @Override protected void beforeOnDestroy() {

    }

    private class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            MopaPhotoView photoView = new MopaPhotoView(container.getContext());

            photoView.setImageUri(images.get(position), null);

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override public void onPhotoTap(View view, float x, float y) {
                    mTitle.toggleVisible();
                }

                @Override public void onOutsidePhotoTap() {

                }
            });

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    private void toggleViewPagerScrolling() {
        if (isViewPagerActive()) {
            ((HackyViewPager) mViewPager).toggleLock();
        }
    }

    private boolean isViewPagerActive() {
        return (mViewPager != null && mViewPager instanceof HackyViewPager);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (isViewPagerActive()) {
            outState.putBoolean(ISLOCKED_ARG, ((HackyViewPager) mViewPager).isLocked());
        }
        super.onSaveInstanceState(outState);
    }
}
