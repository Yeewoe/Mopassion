// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.setting.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SettingMainActivity$$ViewBinder<T extends org.yeewoe.mopassion.app.setting.view.SettingMainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689635, "method 'onLogoutClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onLogoutClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
  }
}
