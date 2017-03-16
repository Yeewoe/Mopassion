// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.auth.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RegisterAccountFragment$$ViewBinder<T extends org.yeewoe.mopassion.app.auth.view.RegisterAccountFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689626, "field 'mEditAccount'");
    target.mEditAccount = finder.castView(view, 2131689626, "field 'mEditAccount'");
    view = finder.findRequiredView(source, 2131689755, "method 'onNextButtonClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onNextButtonClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.mEditAccount = null;
  }
}
