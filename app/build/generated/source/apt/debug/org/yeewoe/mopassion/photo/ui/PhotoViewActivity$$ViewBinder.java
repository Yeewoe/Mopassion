// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.photo.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PhotoViewActivity$$ViewBinder<T extends org.yeewoe.mopassion.photo.ui.PhotoViewActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689634, "field 'mViewPager'");
    target.mViewPager = finder.castView(view, 2131689634, "field 'mViewPager'");
  }

  @Override public void unbind(T target) {
    target.mViewPager = null;
  }
}
