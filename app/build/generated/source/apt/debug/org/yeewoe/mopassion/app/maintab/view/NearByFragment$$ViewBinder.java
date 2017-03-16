// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.maintab.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class NearByFragment$$ViewBinder<T extends org.yeewoe.mopassion.app.maintab.view.NearByFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689749, "field 'mLLTitle'");
    target.mLLTitle = view;
    view = finder.findRequiredView(source, 2131689752, "method 'onPublishTrendOnClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onPublishTrendOnClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689750, "method 'onTitleCenterClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onTitleCenterClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689751, "method 'onMenuArrowClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onMenuArrowClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.mLLTitle = null;
  }
}
