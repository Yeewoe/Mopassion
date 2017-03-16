package org.yeewoe.mopassion.app.contact.view;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.yeewoe.commonutils.common.cache.FragmentCacheCenter;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.model.UserHeadVo;
import org.yeewoe.mopassion.app.common.view.acitivty.MopaActivity;
import org.yeewoe.mopassion.app.common.view.widget.MopaTextView;
import org.yeewoe.mopassion.app.contact.model.UserDetailVo;
import org.yeewoe.mopassion.app.contact.presenter.UserDetailPresenter;
import org.yeewoe.mopassion.app.contact.view.iview.IUserDetailView;
import org.yeewoe.mopassion.app.maintab.view.UserTrendFragment;
import org.yeewoe.mopassion.app.nearby.view.UserSpaceFragment;
import org.yeewoe.mopassion.mangers.UserDataMananger;
import org.yeewoe.mopassion.photo.fresco_lib.HeadThumbSimpleDraweeView;
import org.yeewoe.mopassion.template.media.TMediaImage;
import org.yeewoe.mopassion.utils.DialogFuncUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wyw on 2016/4/26.
 */
public class UserDetailActivity extends MopaActivity<UserDetailPresenter> implements IUserDetailView {
    public static final String EXTRA_USER_HEAD = "extra_user_head";
    public static final String EXTRA_USER_SID = "extra_user_sid";

    public UserDetailVo userDetailVo;

    @Bind(R.id.img_head) HeadThumbSimpleDraweeView mImgHead;
    @Bind(R.id.txt_nick) MopaTextView mTxtNick;
    @Bind(R.id.txt_signature) MopaTextView mTxtSignature;
    //    @Bind(R.id.head_photo_gl_main) HeadPhotoGridLayout mHeadPhotoGridLayoutMain;
    @Bind(R.id.radio_group_tab) RadioGroup mRadioGroupTab;
    @Bind(R.id.radio_btn_post) RadioButton mRadioBtnPost;
    @Bind(R.id.radio_btn_space) RadioButton mRadioBtnSpace;
    @Bind(R.id.ll_operation_bar) LinearLayout mLLOperationBar;
    private FragmentCacheCenter mFragmentCacheCenter;

    @Override protected int getViewRootResId() {
        return R.layout.activity_user_detail;
    }

    @Override protected void bindIntent() {
        userDetailVo = new UserDetailVo(null);
        UserHeadVo userHeadVo = getIntent().getParcelableExtra(EXTRA_USER_HEAD);
        if (userHeadVo != null) {
            userDetailVo.setNickname(userHeadVo.getName());
            userDetailVo.setSid(userHeadVo.getSid());
        } else {
            long sid = getIntent().getLongExtra(EXTRA_USER_SID, -1);
            userDetailVo.setSid(sid);
        }
    }

    @Override protected void initTitle() {
    }

    @Override protected void bindLister() {

    }

    @Override protected UserDetailPresenter getPresenter() {
        return new UserDetailPresenter(this);
    }

    @Override protected void bindData() {
        mFragmentCacheCenter = new FragmentCacheCenter();

        mRadioGroupTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_btn_post:
                        onTabPostChecked();
                        break;
                    case R.id.radio_btn_space:
                    default:
                        onTabSpaceChecked();
                        break;
                }
            }
        });

        mRadioGroupTab.check(R.id.radio_btn_post);
    }

    private void onTabPostChecked() {
        mRadioBtnPost.setTextColor(getResources().getColor(R.color.white));
        mRadioBtnSpace.setTextColor(getResources().getColor(R.color.purple));
        Fragment fragment = mFragmentCacheCenter.get(UserTrendFragment.class);
        if (fragment == null) {
            fragment = UserTrendFragment.newInstantce(userDetailVo.getSid());
            mFragmentCacheCenter.put(UserTrendFragment.class, fragment);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_tab_content, fragment).commit();
    }

    private void onTabSpaceChecked() {
        mRadioBtnPost.setTextColor(getResources().getColor(R.color.purple));
        mRadioBtnSpace.setTextColor(getResources().getColor(R.color.white));
        Fragment fragment = mFragmentCacheCenter.get(UserSpaceFragment.class);
        if (fragment == null) {
            fragment = UserSpaceFragment.newInstantce((ArrayList<TMediaImage>) userDetailVo.getHeadPhotos());
            mFragmentCacheCenter.put(UserSpaceFragment.class, fragment);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_tab_content, fragment).commit();
    }

    @Override protected void afterOnStart() {

    }

    @Override protected void afterOnResume() {
        initDetail(userDetailVo);
        mPresenter.loadLocal(userDetailVo.getSid());
        mPresenter.loadNet(userDetailVo.getSid());
    }

    @Override protected void beforeOnPause() {

    }

    @Override protected void beforOnStop() {

    }

    @Override protected void beforeOnDestroy() {

    }
//
//    @OnClick(R.id.btn_follow) void onFollowClick() {
//        mPresenter.follow(userDetailVo.getSid());
//    }
//
//    @OnClick(R.id.btn_chat) void onChatClick() {
//        IntentManager.IM.intentToChat(this, userDetailVo.getSid());
//    }

    @Override public Activity getActivity() {
        return this;
    }

    @Override public void initDetail(UserDetailVo data) {
        userDetailVo = data;
        mImgHead.setImageHead(data.getUserHeadVo());
        mImgHead.modeToShow();
        if (isMe()) {

            mLLOperationBar.setVisibility(View.GONE);
        }
        mTxtNick.setText(data.getNickname());
        mTxtSignature.setSignature(data.getSignature());
//        mHeadPhotoGridLayoutMain.setPhotos(data.getHeadPhotos());
    }

    private boolean isMe() {
        return UserDataMananger.getInstance().getMeUid() == userDetailVo.getSid();
    }

    @OnClick(R.id.imgvi_back) void onBackClick(View view) {
        finish();
    }

    @OnClick(R.id.ll_contact) void onContactClick(View view) {
        DialogFuncUtil.openContact(this, userDetailVo.getAccount(), userDetailVo.getSid(), userDetailVo.getNickname());
    }

    @OnClick(R.id.ll_mark) void onMarkClick(View view) {
        showToast("click mark");
    }

    @OnClick(R.id.ll_blacklist) void onBlackListClick(View view) {
        showToast("click blacklist");
    }
}
