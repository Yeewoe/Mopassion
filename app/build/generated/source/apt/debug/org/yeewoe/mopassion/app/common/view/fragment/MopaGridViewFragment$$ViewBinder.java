// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.common.view.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MopaGridViewFragment$$ViewBinder<T extends org.yeewoe.mopassion.app.common.view.fragment.MopaGridViewFragment> extends org.yeewoe.mopassion.app.common.view.fragment.MopaListViewFragment$$ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    super.bind(finder, target, source);

    View view;
    view = finder.findRequiredView(source, 2131689478, "field 'mGridViewMain'");
    target.mGridViewMain = finder.castView(view, 2131689478, "field 'mGridViewMain'");
  }

  @Override public void unbind(T target) {
    super.unbind(target);

    target.mGridViewMain = null;
  }
}
