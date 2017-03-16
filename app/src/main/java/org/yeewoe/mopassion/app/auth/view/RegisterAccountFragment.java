package org.yeewoe.mopassion.app.auth.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import org.yeewoe.commonutils.common.assist.UIVerifier;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.auth.presenter.RegisterNickPresenter;
import org.yeewoe.mopassion.app.auth.view.iview.IRegisterNickView;
import org.yeewoe.mopassion.app.common.view.builder.TitleBuilder;
import org.yeewoe.mopassion.app.common.view.fragment.MopaFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 注册-填写邮箱账号 TAB
 */
public class RegisterAccountFragment extends MopaFragment<RegisterNickPresenter> implements IRegisterNickView {
    public static final String DATA_KEY_NICK_NAME = "nick";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Bind(value = R.id.edit_account) EditText mEditAccount;

    private UIVerifier mVerifier;

    public RegisterAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MeFragment.
     */
    public static RegisterAccountFragment newInstance() {
        return new RegisterAccountFragment();
    }

    @Override
    protected int getViewRootResId() {
        return R.layout.fragment_register_account;
    }

    @Override
    protected void onCreateViewInit(Bundle savedInstanceState) {
        mVerifier = new UIVerifier();
        new TitleBuilder(viewRoot).changeToPrimaryColor().modeToBack(getActivity()).changeCenterImg(R.drawable.ic_register_img);
        mEditAccount.requestFocus();
    }

    @Override
    protected RegisterNickPresenter getPresenter() {
        return new RegisterNickPresenter(this);
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
    public void onNextButtonClick(View view) {
        if (!mVerifier.verifyEmpty(mEditAccount)) {
            mEditAccount.setError(getString(R.string.can_not_be_empty));
            return ;
        }

        if (!mVerifier.verifyEmail(mEditAccount)) {
            mEditAccount.setError(getString(R.string.please_input_a_email_address));
            return ;
        }

        mPresenter.next(mEditAccount.getText().toString());
    }
}
