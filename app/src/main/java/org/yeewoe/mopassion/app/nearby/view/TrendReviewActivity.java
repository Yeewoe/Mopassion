package org.yeewoe.mopassion.app.nearby.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.model.UserHeadVo;
import org.yeewoe.mopassion.app.common.view.acitivty.MopaActivity;
import org.yeewoe.mopassion.app.common.view.builder.TitleBuilder;
import org.yeewoe.mopassion.app.common.view.widget.MopaTextView;
import org.yeewoe.mopassion.app.common.view.widget.ThumbPhotoGridLayout;
import org.yeewoe.mopassion.app.nearby.model.TrendPublishParam;
import org.yeewoe.mopassion.app.nearby.presenter.TrendReviewPresenter;
import org.yeewoe.mopassion.app.nearby.view.iview.ITrendReviewView;
import org.yeewoe.mopassion.mangers.ClientManger;
import org.yeewoe.mopassion.mangers.UserDataMananger;
import org.yeewoe.mopassion.photo.fresco_lib.HeadThumbSimpleDraweeView;
import org.yeewoe.mopassion.utils.TimeUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 动态发布界面
 * Created by wyw on 2016/4/8.
 */
public class TrendReviewActivity extends MopaActivity<TrendReviewPresenter> implements ITrendReviewView {

    public static final String EXTRA_PARAM = "extra_param";
    private TrendPublishParam mParam;

    @Bind(R.id.imgvi_back) ImageView mImgViBack;
    @Bind(R.id.img_head) HeadThumbSimpleDraweeView mImgHead;
    @Bind(R.id.txt_name) MopaTextView mTxtName;
    @Bind(R.id.txt_site) MopaTextView mTxtSite;
    @Bind(R.id.txt_time) MopaTextView mTxtTime;
    @Bind(R.id.txt_title) MopaTextView mTxtTitle;
    @Bind(R.id.txt_content) MopaTextView mTxtContent;
    @Bind(R.id.thumbGL_photo) ThumbPhotoGridLayout mThumbGLPhoto;

    @Override protected int getViewRootResId() {
        return R.layout.activity_trend_review;
    }

    @Override protected void bindIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mParam = (TrendPublishParam) intent.getSerializableExtra(EXTRA_PARAM);
        }
    }

    @Override protected void initTitle() {
        mImgViBack.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                finish();
            }
        });
    }

    @Override protected void bindLister() {

    }

    @Override protected TrendReviewPresenter getPresenter() {
        return new TrendReviewPresenter(this);
    }

    @Override protected void bindData() {
        mImgHead.setMeHead();
        mTxtName.setMe();
        mTxtTime.setText(getString(R.string.trend_publish_time_format, TimeUtil.parseTime(ClientManger.getInstance().getServerSwapTime(), TimeUtil.pattern2)), null);
        if (mParam != null) {
            if (mParam.mapPosition != null) {
                mTxtSite.setText(getString(R.string.trend_detail_site_format, mParam.mapPosition.getCountry(), mParam.mapPosition.getCity(), 0.0));
            }
            mTxtTitle.setText(mParam.title, null);
            mTxtContent.setText(mParam.content, null);
            mThumbGLPhoto.setPhotos(mParam.mediaImageList);
        } else {
            showToast(R.string.error_data_error);
            finish();
        }
    }

    @Override protected void afterOnStart() {

    }

    @Override protected void afterOnResume() {

    }

    @Override protected void beforeOnPause() {
        mPresenter.cancelSubmit();
    }

    @Override protected void beforOnStop() {

    }

    @Override protected void beforeOnDestroy() {

    }

    @Override public void onLoadingDismiss() {
        mPresenter.cancelSubmit();
    }

    @Override public Activity getActivity() {
        return this;
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.handleActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.btn_submit) void onSubmitClick(View view) {
        mPresenter.submit(this.mParam);
    }

}
