// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.auth.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginActivity$$ViewBinder<T extends org.yeewoe.mopassion.app.auth.view.LoginActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689626, "field 'mAccountView'");
    target.mAccountView = finder.castView(view, 2131689626, "field 'mAccountView'");
    view = finder.findRequiredView(source, 2131689627, "field 'mPasswordView'");
    target.mPasswordView = finder.castView(view, 2131689627, "field 'mPasswordView'");
    view = finder.findRequiredView(source, 2131689630, "field 'mFbBtnLogin'");
    target.mFbBtnLogin = finder.castView(view, 2131689630, "field 'mFbBtnLogin'");
    view = finder.findRequiredView(source, 2131689631, "field 'mTtBtnLogin'");
    target.mTtBtnLogin = finder.castView(view, 2131689631, "field 'mTtBtnLogin'");
    view = finder.findRequiredView(source, 2131689628, "method 'onSignInButtonClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onSignInButtonClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689629, "method 'onRegisterButtonClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onRegisterButtonClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.mAccountView = null;
    target.mPasswordView = null;
    target.mFbBtnLogin = null;
    target.mTtBtnLogin = null;
  }
}
