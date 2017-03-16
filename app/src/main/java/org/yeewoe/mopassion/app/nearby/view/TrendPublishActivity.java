package org.yeewoe.mopassion.app.nearby.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.view.acitivty.MopaActivity;
import org.yeewoe.mopassion.app.common.view.builder.TitleBuilder;
import org.yeewoe.mopassion.app.common.view.dialog.MopaSelectDialog;
import org.yeewoe.mopassion.app.common.view.widget.ChatEditText;
import org.yeewoe.mopassion.app.common.view.widget.ThumbPhotoGridLayout;
import org.yeewoe.mopassion.app.location.manager.LocationPointInfo;
import org.yeewoe.mopassion.app.location.manager.LocationUtils;
import org.yeewoe.mopassion.app.nearby.model.TrendPublishParam;
import org.yeewoe.mopassion.app.nearby.presenter.TrendPublishPresenter;
import org.yeewoe.mopassion.app.nearby.view.iview.ITrendPublishView;
import org.yeewoe.mopassion.db.po.MapPosition;
import org.yeewoe.mopassion.utils.IntentManager;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 动态发布界面
 * Created by wyw on 2016/4/8.
 */
public class TrendPublishActivity extends MopaActivity<TrendPublishPresenter> implements ITrendPublishView {

    @Bind(R.id.edit_trend_title) ChatEditText mEditTitle;
    @Bind(R.id.edit_trend_content) ChatEditText mEditContent;
    @Bind(R.id.thumbGL_photo) ThumbPhotoGridLayout thumbGLPhoto;
    @Bind(R.id.imgvi_add_photo) ImageView mImgViAddPhoto;

    @Override protected int getViewRootResId() {
        return R.layout.activity_trend_publish;
    }

    @Override protected void bindIntent() {
    }

    @Override protected void initTitle() {
        new TitleBuilder(this).changeCenterTxt(R.string.trend_publish_title).changeToPrimaryColor().modeToBack(this, true);
    }

    @Override protected void bindLister() {

    }

    @Override protected TrendPublishPresenter getPresenter() {
        return new TrendPublishPresenter(this);
    }

    @Override protected void bindData() {
        // 执行定位更新
        mPresenter.requestLocation();
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

    @Override public void onLoadingDismiss() {
    }

    @Override public void setSite(LocationPointInfo site) {
    }

    @Override public void addPic(File pic) {
        thumbGLPhoto.addPhoto(pic);
        mImgViAddPhoto.setVisibility(View.GONE);
    }

    @Override public Activity getActivity() {
        return this;
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.handleActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.imgvi_add_photo) void onAddPicOnClick(View view) {
        MopaSelectDialog dialog = new MopaSelectDialog(this, R.array.common_picture_item_arrays, new MopaSelectDialog.OnClickListener() {
            @Override public void onClick(MopaSelectDialog dialog, int which) {
                switch (which) {
                    case 0:
                        mPresenter.openCamera();
                        break;
                    case 1:
                        mPresenter.openGallery();
                        break;
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.btn_next) void onNextOnClick(View view) {
        if (!Checks.check(mEditTitle.getText())) {
            mEditTitle.setError(getString(R.string.trend_publish_title_tip));
            return;
        }

        if (!Checks.check(mEditContent.getText())) {
            mEditContent.setError(getString(R.string.trend_publish_content_tip));
            return;
        }

        LocationPointInfo site = LocationUtils.loadLocation();
        MapPosition mapPosition;
        if (site == null) {
            showToast(getString(R.string.trend_publish_location_tip));
            return ;
        }

        mapPosition = new MapPosition(site);
        TrendPublishParam param = new TrendPublishParam(mEditTitle.getText().toString(), mEditContent.getText().toString(), mapPosition, thumbGLPhoto.getMediaImageList(), thumbGLPhoto.getFileList());
        IntentManager.Trend.intentToReviewTrend(this, param);
    }


}
