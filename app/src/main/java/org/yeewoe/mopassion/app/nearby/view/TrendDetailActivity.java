package org.yeewoe.mopassion.app.nearby.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.view.acitivty.MopaActivity;
import org.yeewoe.mopassion.app.common.view.widget.MopaTextView;
import org.yeewoe.mopassion.app.common.view.widget.ThumbPhotoGridLayout;
import org.yeewoe.mopassion.app.nearby.model.TrendLineVo;
import org.yeewoe.mopassion.app.nearby.presenter.TrendDetailPresenter;
import org.yeewoe.mopassion.app.nearby.view.iview.ITrendDetailView;
import org.yeewoe.mopassion.mangers.UserDataMananger;
import org.yeewoe.mopassion.photo.fresco_lib.HeadThumbSimpleDraweeView;
import org.yeewoe.mopassion.utils.DialogFuncUtil;
import org.yeewoe.mopassion.utils.IntentManager;
import org.yeewoe.mopassion.utils.TimeUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wyw on 2016/8/11.
 */
public class TrendDetailActivity extends MopaActivity<TrendDetailPresenter> implements ITrendDetailView {
    public static final String EXTRA_TREND = "extra_trend";
    private TrendLineVo mData;

    @Bind(R.id.imgvi_back) ImageView mImgViBack;
    @Bind(R.id.img_head) HeadThumbSimpleDraweeView mImgHead;
    @Bind(R.id.txt_name) MopaTextView mTxtName;
    @Bind(R.id.txt_site) MopaTextView mTxtSite;
    @Bind(R.id.txt_time) MopaTextView mTxtTime;
    @Bind(R.id.txt_title) MopaTextView mTxtTitle;
    @Bind(R.id.txt_content) MopaTextView mTxtContent;
    @Bind(R.id.thumbGL_photo) ThumbPhotoGridLayout mThumbGLPhoto;
    @Bind(R.id.span_contact) View mSpanContact;
    @Bind(R.id.btn_contact) Button mBtnContact;
    @Bind(R.id.btn_follow) Button mBtnFollow;

    @Override protected int getViewRootResId() {
        return R.layout.activity_trend_detail;
    }

    @Override protected void bindIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mData = intent.getParcelableExtra(EXTRA_TREND);
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

    @Override protected TrendDetailPresenter getPresenter() {
        return new TrendDetailPresenter(this);
    }

    @Override protected void bindData() {
        if (mData == null) {
            showToast(R.string.error_data_error);
            return ;
        }
        mImgHead.setImageHead(mData.getUser());
        if (mData.getUser() != null) {
            mTxtName.setText(mData.getUser().getName());
        }
        mTxtTitle.setText(mData.getMsg2());
        mTxtContent.setText(mData.getMsg());
        mTxtSite.setText(getString(R.string.trend_detail_site_format, mData.getCountry(), mData.getCity(), mData.getDistance()));
        mTxtTime.setText(getString(R.string.trend_publish_time_format, TimeUtil.parseTime(mData.getTimeMillis(), TimeUtil.pattern2)), null);
        mThumbGLPhoto.setPhotos(mData.getImages());

        if (isMe()) {
            mSpanContact.setVisibility(View.GONE);
            mBtnContact.setVisibility(View.GONE);
            mBtnFollow.setVisibility(View.GONE);
        }
    }

    private boolean isMe() {
        return mData.getUser() != null && mData.getUser().getSid() == UserDataMananger.getInstance().getMeUid();
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

    @OnClick(R.id.btn_contact) void onContactBtnClick(View view) {
        if (mData.getUser() != null) {
            DialogFuncUtil.openContact(this, mData.getUser().getAccount(), mData.getUser().getSid(), mData.getUser().getName());
        }
    }

    @OnClick(R.id.btn_follow) void onFollowBtnClick(View view) {
        if (mData.getUser() != null) {
            mPresenter.follow(mData.getUser().getSid());
        }
    }

    @Override public Activity getActivity() {
        return this;
    }
}
