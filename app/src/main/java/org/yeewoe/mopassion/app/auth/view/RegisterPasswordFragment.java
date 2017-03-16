package org.yeewoe.mopassion.app.auth.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.yeewoe.commonutils.common.assist.UIVerifier;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.auth.presenter.RegisterPasswordPresenter;
import org.yeewoe.mopassion.app.auth.view.iview.IRegisterPasswordView;
import org.yeewoe.mopassion.app.common.view.builder.TitleBuilder;
import org.yeewoe.mopassion.app.common.view.fragment.MopaFragment;
import org.yeewoe.mopassion.utils.SoftWareUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 注册-填写密码 TAB
 */
public class RegisterPasswordFragment extends MopaFragment<RegisterPasswordPresenter> implements IRegisterPasswordView {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Bind(value = R.id.edit_password) EditText mEditPassword;

    private UIVerifier mVerifier;

    private String mParam1;
    private String mParam2;

    public RegisterPasswordFragment() {
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
    public static RegisterPasswordFragment newInstance(String param1, String param2) {
        RegisterPasswordFragment fragment = new RegisterPasswordFragment();
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
        return R.layout.fragment_register_password;
    }

    @Override
    protected void onCreateViewInit(Bundle savedInstanceState) {
        mVerifier = new UIVerifier();
        new TitleBuilder(viewRoot).changeToPrimaryColor().modeToBack(getActivity()).changeCenterImg(R.drawable.ic_register_img);
        mEditPassword.requestFocus();
    }

    @Override
    protected RegisterPasswordPresenter getPresenter() {
        return new RegisterPasswordPresenter(this);
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
        if (!mVerifier.verifyLength(mEditPassword, 6, -1)) {
            mEditPassword.setError(getString(R.string.min_length_format, 6));
            return ;
        }
        if (!mVerifier.verifyPassword(mEditPassword)) {
            mEditPassword.setError(getString(R.string.please_input_a_password));
            return ;
        }

        /** 隐藏键盘 **/
        SoftWareUtil.hideSoftWare(getActivity());
        mPresenter.next(mEditPassword.getText().toString());
    }
}
