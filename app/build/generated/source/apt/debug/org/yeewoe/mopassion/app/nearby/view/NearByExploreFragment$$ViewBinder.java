// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.nearby.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class NearByExploreFragment$$ViewBinder<T extends org.yeewoe.mopassion.app.nearby.view.NearByExploreFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689754, "field 'swipeView'");
    target.swipeView = finder.castView(view, 2131689754, "field 'swipeView'");
  }

  @Override public void unbind(T target) {
    target.swipeView = null;
  }
}
