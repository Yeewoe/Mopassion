// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.web.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class WebBrowserActivity$$ViewBinder<T extends org.yeewoe.mopassion.app.web.view.WebBrowserActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689670, "field 'mWebViewMain'");
    target.mWebViewMain = finder.castView(view, 2131689670, "field 'mWebViewMain'");
  }

  @Override public void unbind(T target) {
    target.mWebViewMain = null;
  }
}
