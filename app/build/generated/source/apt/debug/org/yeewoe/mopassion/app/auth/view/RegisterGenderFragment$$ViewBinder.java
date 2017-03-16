// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.auth.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RegisterGenderFragment$$ViewBinder<T extends org.yeewoe.mopassion.app.auth.view.RegisterGenderFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689756, "field 'mRadioBtnMale' and method 'onMaleClick'");
    target.mRadioBtnMale = finder.castView(view, 2131689756, "field 'mRadioBtnMale'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onMaleClick();
        }
      });
    view = finder.findRequiredView(source, 2131689757, "field 'mRadioBtnFemale' and method 'onFemaleClick'");
    target.mRadioBtnFemale = finder.castView(view, 2131689757, "field 'mRadioBtnFemale'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onFemaleClick();
        }
      });
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
    target.mRadioBtnMale = null;
    target.mRadioBtnFemale = null;
  }
}
