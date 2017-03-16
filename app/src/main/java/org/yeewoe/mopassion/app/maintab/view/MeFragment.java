package org.yeewoe.mopassion.app.maintab.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.yeewoe.commonutils.common.cache.FragmentCacheCenter;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.presenter.MopaPresenter;
import org.yeewoe.mopassion.app.common.view.fragment.MopaFragment;
import org.yeewoe.mopassion.app.common.view.widget.MopaTextView;
import org.yeewoe.mopassion.app.contact.model.UserDetailVo;
import org.yeewoe.mopassion.app.nearby.view.UserSpaceFragment;
import org.yeewoe.mopassion.mangers.UserDataMananger;
import org.yeewoe.mopassion.photo.fresco_lib.HeadThumbSimpleDraweeView;
import org.yeewoe.mopassion.template.media.TMediaImage;
import org.yeewoe.mopassion.utils.IntentManager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 个人 TAB
 */
public class MeFragment extends MopaFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @Bind(R.id.imgvi_head) HeadThumbSimpleDraweeView mImgViHead;
    @Bind(R.id.txt_nick) MopaTextView mTxtNick;
    @Bind(R.id.txt_signature) MopaTextView mTxtSignature;
    @Bind(R.id.radio_group_tab) RadioGroup mRadioGroupTab;
    @Bind(R.id.radio_btn_post) RadioButton mRadioBtnPost;
    @Bind(R.id.radio_btn_space) RadioButton mRadioBtnSpace;
    @Bind(R.id.radio_btn_followed) RadioButton mRadioBtnFollowed;

//    @Bind(R.id.txt_account) MopaTextView mTxtAccount;


    private UserDetailVo mUserDetailVo;
    private FragmentCacheCenter mFragmentCacheCenter;

    public MeFragment() {
        // Required empty public constructor
        mFragmentCacheCenter = new FragmentCacheCenter();

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected int getViewRootResId() {
        return R.layout.fragment_main_me;
    }

    @Override
    protected void onCreateViewInit(Bundle savedInstanceState) {
        mRadioGroupTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_btn_post:
                        onTabPostChecked();
                        break;
                    case R.id.radio_btn_followed:
                        onTabFollowedChecked();
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
        mRadioBtnFollowed.setTextColor(getResources().getColor(R.color.purple));
        Fragment fragment = mFragmentCacheCenter.get(UserTrendFragment.class);
        if (fragment == null) {
            fragment = new UserTrendFragment();
            mFragmentCacheCenter.put(UserTrendFragment.class, fragment);
        }
        getFragmentManager().beginTransaction().replace(R.id.fl_tab_content, fragment).commit();
    }

    private void onTabSpaceChecked() {
        mRadioBtnPost.setTextColor(getResources().getColor(R.color.purple));
        mRadioBtnSpace.setTextColor(getResources().getColor(R.color.white));
        mRadioBtnFollowed.setTextColor(getResources().getColor(R.color.purple));
        Fragment fragment = mFragmentCacheCenter.get(UserSpaceFragment.class);
        if (fragment == null) {
            fragment = UserSpaceFragment.newInstantce((ArrayList<TMediaImage>) mUserDetailVo.getHeadPhotos());
            mFragmentCacheCenter.put(UserSpaceFragment.class, fragment);
        }
        getFragmentManager().beginTransaction().replace(R.id.fl_tab_content, fragment).commit();
    }

    private void onTabFollowedChecked() {
        mRadioBtnPost.setTextColor(getResources().getColor(R.color.purple));
        mRadioBtnSpace.setTextColor(getResources().getColor(R.color.purple));
        mRadioBtnFollowed.setTextColor(getResources().getColor(R.color.white));
        Fragment fragment = mFragmentCacheCenter.get(MeFollowedFragment.class);
        if (fragment == null) {
            fragment = new MeFollowedFragment();
            mFragmentCacheCenter.put(MeFollowedFragment.class, fragment);
        }
        getFragmentManager().beginTransaction().replace(R.id.fl_tab_content, fragment).commit();
    }

    @Override public void onResume() {
        super.onResume();
        mUserDetailVo = UserDetailVo.Convertor.convert(UserDataMananger.getInstance().getMe());
        mImgViHead.setImageHead(mUserDetailVo.getUserHeadVo());
        mImgViHead.modeToShow();
        mTxtNick.setUser(mUserDetailVo.getUserHeadVo());
        mTxtSignature.setText(mUserDetailVo.getSignature());
//        mTxtAccount.setText(getString(R.string.me_main_account, mUserDetailVo.getAccount()));
    }

    @Override
    protected MopaPresenter getPresenter() {
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick(R.id.imgvi_edit) void onTitleClick() {
        IntentManager.Contact.intentToEdit(getContext(), mUserDetailVo);
    }

   @OnClick(R.id.imgvi_setting) void onSettingClick() {
        IntentManager.Setting.intentToMain(getContext());
    }

//    @OnClick(R.id.rl_trend) void onTrendClick() {
//        IntentManager.Trend.intentToUserTrendList(getContext(), mUserDetailVo);
//    }

//    @OnClick(R.id.rl_setting) void onSettingClick() {
//        IntentManager.Setting.intentToMain(getContext());
//    }
//
//    @OnClick(R.id.rl_help) void onHelpClick() {
//        IntentManager.Help.intentToMain(getContext());
//    }

}
