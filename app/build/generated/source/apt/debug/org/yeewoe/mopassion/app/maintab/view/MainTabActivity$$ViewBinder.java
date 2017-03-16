// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.maintab.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainTabActivity$$ViewBinder<T extends org.yeewoe.mopassion.app.maintab.view.MainTabActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689633, "field 'tabLayoutMain'");
    target.tabLayoutMain = finder.castView(view, 2131689633, "field 'tabLayoutMain'");
  }

  @Override public void unbind(T target) {
    target.tabLayoutMain = null;
  }
}
