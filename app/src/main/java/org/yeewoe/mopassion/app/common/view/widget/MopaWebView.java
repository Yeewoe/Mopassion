package org.yeewoe.mopassion.app.common.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.yeewoe.commonutils.common.utils.PatternUtil;
import org.yeewoe.mopassion.utils.LinkUtil;
import org.yeewoe.mopassion.utils.WebViewUtil;

/**
 * 内置浏览器
 * Created by wyw on 2016/6/6.
 */
public class MopaWebView extends WebView {
    private OnWebChromeClientListener onWebChromeClientListener;
    private String mUrl;

    @Override public void loadUrl(String url) {
        super.loadUrl(WebViewUtil.convertUrl(url));
    }

    public static interface OnWebChromeClientListener {
        void onReceivedTitle(MopaWebView webView, String title);
    }

    public MopaWebView(Context context) {
        super(context);
        init();
    }

    public MopaWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MopaWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) public MopaWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setOnWebChromeClientListener(OnWebChromeClientListener onWebChromeClientListener) {
        this.onWebChromeClientListener = onWebChromeClientListener;
    }

    private void init() {

        getSettings().setJavaScriptEnabled(true);

        setWebChromeClient(new WebChromeClient() {


            @Override public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (onWebChromeClientListener !=  null) {
                    onWebChromeClientListener.onReceivedTitle((MopaWebView) view, title);
                }
            }
        });

        setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (PatternUtil.isEmail(url)) {
                    LinkUtil.openEmailDialog(getContext(), url);
                    return true;
                } else if (PatternUtil.isPhone(url)) {
                    LinkUtil.openPhoneDialog(getContext(), url);
                    return true;
                } else {
                    mUrl = url;
//                    endShouldOverrideUrlLoading();
                    return false;
                }
            }
        });
    }
}
