// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.auth.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RegisterActivity$$ViewBinder<T extends org.yeewoe.mopassion.app.auth.view.RegisterActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689632, "field 'mFlMainContent'");
    target.mFlMainContent = finder.castView(view, 2131689632, "field 'mFlMainContent'");
  }

  @Override public void unbind(T target) {
    target.mFlMainContent = null;
  }
}
