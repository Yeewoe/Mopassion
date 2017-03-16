// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.auth.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RegisterPasswordFragment$$ViewBinder<T extends org.yeewoe.mopassion.app.auth.view.RegisterPasswordFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689627, "field 'mEditPassword'");
    target.mEditPassword = finder.castView(view, 2131689627, "field 'mEditPassword'");
    view = finder.findRequiredView(source, 2131689755, "method 'onFinishButtonClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onFinishButtonClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.mEditPassword = null;
  }
}
