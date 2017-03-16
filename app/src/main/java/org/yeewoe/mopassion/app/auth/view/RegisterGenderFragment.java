package org.yeewoe.mopassion.app.auth.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import org.yeewoe.commonutils.common.assist.UIVerifier;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.auth.presenter.RegisterAuthInitPresenter;
import org.yeewoe.mopassion.app.auth.view.iview.IRegisterAuthInitView;
import org.yeewoe.mopassion.app.common.view.builder.TitleBuilder;
import org.yeewoe.mopassion.app.common.view.fragment.MopaFragment;
import org.yeewoe.mopassion.db.po.Gender;
import org.yeewoe.mopassion.utils.SoftWareUtil;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 注册-选择性别 TAB
 */
public class RegisterGenderFragment extends MopaFragment<RegisterAuthInitPresenter> implements IRegisterAuthInitView {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Bind(R.id.radio_btn_male) RadioButton mRadioBtnMale;
    @Bind(R.id.radio_btn_female) RadioButton mRadioBtnFemale;

    private UIVerifier mVerifier;

    private String mParam1;
    private String mParam2;

    public RegisterGenderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    public static RegisterGenderFragment newInstance(String param1, String param2) {
        RegisterGenderFragment fragment = new RegisterGenderFragment();
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
        return R.layout.fragment_register_gender;
    }

    @Override
    protected void onCreateViewInit(Bundle savedInstanceState) {
        mVerifier = new UIVerifier();
        new TitleBuilder(viewRoot).changeToPrimaryColor().modeToBack(getActivity()).changeCenterImg(R.drawable.ic_register_img);
    }

    @Override
    protected RegisterAuthInitPresenter getPresenter() {
        return new RegisterAuthInitPresenter(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick(R.id.action_next)
    public void onFinishButtonClick(View view) {
        int gender = 0;
        if (mRadioBtnMale.isChecked()) {
            gender = Gender.MALE;
        } else if (mRadioBtnFemale.isChecked()) {
            gender = Gender.FEMALE;
        }
        mPresenter.register(gender);
    }

    @OnClick(R.id.radio_btn_male) void onMaleClick() {
        mRadioBtnFemale.setChecked(false);
    }

    @OnClick(R.id.radio_btn_female) void onFemaleClick() {
        mRadioBtnMale.setChecked(false);
    }
}
