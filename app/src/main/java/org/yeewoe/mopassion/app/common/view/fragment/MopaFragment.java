package org.yeewoe.mopassion.app.common.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.presenter.MopaPresenter;
import org.yeewoe.mopassion.app.common.view.dialog.MopaLoadingDialog;
import org.yeewoe.mopassion.utils.LogCore;
import org.yeewoe.mopassion.app.common.view.widget.MopaToast;
import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.mopassion.utils.UIErrorUtil;

import butterknife.ButterKnife;

/**
 * 公共基类Fragment
 * Created by wyw on 2016/3/6.
 */
public abstract class MopaFragment<T extends MopaPresenter> extends Fragment {

    protected EventBus mEventBus;
    protected View viewRoot;
    protected T mPresenter;
    private MopaLoadingDialog mLoadingDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogCore.i("附加Fragment: " + getClass().getSimpleName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogCore.d("onCreate: " + getClass().getSimpleName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogCore.d("onCreateView: " + getClass().getSimpleName());
        if (viewRoot == null) {
            LogCore.d("viewRoot != null: " + getClass().getSimpleName());
            viewRoot = getViewRoot(inflater, container);
            ButterKnife.bind(this, viewRoot);
            mEventBus = EventBus.getDefault();
            onCreateViewInit(savedInstanceState);
            mPresenter = getPresenter();
        }

        onBind();

        return viewRoot;
    }

    protected void onBind() {
    }

    private View getViewRoot(LayoutInflater inflater, ViewGroup container) {
        int viewRootResId = getViewRootResId();
        if (viewRootResId > 0) {
            return inflater.inflate(viewRootResId, container, false);
        } else {
            return new View(getContext());
        }
    }

    protected View getRootView() {
        return viewRoot;
    }

    protected abstract int getViewRootResId();

    protected abstract void onCreateViewInit(Bundle savedInstanceState);

    protected abstract T getPresenter();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogCore.d("onActivityCreated: " + getClass().getSimpleName());
    }

    @Override
    public void onStart() {
        super.onStart();
//        LogCore.d("onStart: " + getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
//        LogCore.d("onResume: " + getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
//        LogCore.d("onPause: " + getClass().getSimpleName());
    }

    @Override
    public void onStop() {
        super.onStop();
//        LogCore.d("onStop: " + getClass().getSimpleName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        LogCore.d("onDestroyView: " + getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        LogCore.d("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogCore.i("分离Fragment: " + getClass().getSimpleName());
    }

    public void showToast(String text) {
        if (Checks.check(getActivity())) {
            MopaToast.makeText(getActivity(), text, MopaToast.LENGTH_SHORT).show();
        }
    }

    public void showToast(int resId) {
        if (Checks.check(getActivity())) {
            MopaToast.makeText(getActivity(), resId, MopaToast.LENGTH_SHORT).show();
        }
    }

    public void showError(int errorCode) {
        showToast(UIErrorUtil.getErrorInfo(getActivity(), errorCode));
    }

    public void showLoading() {
        if (Checks.check(getActivity())) {
            mLoadingDialog = new MopaLoadingDialog(getActivity());
            mLoadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mLoadingDialog.setMessage(getString(R.string.please_wait));
            mLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override public void onDismiss(DialogInterface dialog) {
                    onLoadingDismiss();
                }
            });
            mLoadingDialog.show();
        }
    }

    public void onLoadingDismiss() {

    }

    public void dismissLoading() {
        if (Checks.check(getActivity())) {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
                mLoadingDialog = null;
            }
        }
    }

}
