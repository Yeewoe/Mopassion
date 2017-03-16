package org.yeewoe.mopassion.app.web.view;

import android.app.Activity;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.view.acitivty.MopaActivity;
import org.yeewoe.mopassion.app.common.view.builder.TitleBuilder;
import org.yeewoe.mopassion.app.common.view.widget.MopaWebView;
import org.yeewoe.mopassion.app.web.presenter.WebBrowserPresenter;
import org.yeewoe.mopassion.app.web.view.iview.IWebBrowserView;

import butterknife.Bind;

/**
 * Created by acc on 2016/6/6.
 */
public class WebBrowserActivity extends MopaActivity<WebBrowserPresenter> implements IWebBrowserView, MopaWebView.OnWebChromeClientListener {
    public static final String EXTRA_URL = "extra_url";

    @Bind(R.id.webview_main) MopaWebView mWebViewMain;
    private String mUrl;
    private TitleBuilder mTitle;

    @Override public Activity getActivity() {
        return this;
    }

    @Override protected int getViewRootResId() {
        return R.layout.activity_web_browser;
    }

    @Override protected void bindIntent() {
        mUrl = getIntent().getStringExtra(EXTRA_URL);
    }

    @Override protected void initTitle() {
        mTitle = new TitleBuilder(this).modeToBack(this).changeCenterTxt(R.string.loading);
    }

    @Override protected void bindLister() {
        mWebViewMain.setOnWebChromeClientListener(this);
    }

    @Override protected WebBrowserPresenter getPresenter() {
        return null;
    }

    @Override protected void bindData() {
        mWebViewMain.loadUrl(mUrl);
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

    @Override public void onReceivedTitle(MopaWebView webView, String title) {
        mTitle.changeCenterTxt(title);
    }
}
