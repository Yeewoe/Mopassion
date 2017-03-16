// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.nearby.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TrendPublishActivity$$ViewBinder<T extends org.yeewoe.mopassion.app.nearby.view.TrendPublishActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689649, "field 'mEditTitle'");
    target.mEditTitle = finder.castView(view, 2131689649, "field 'mEditTitle'");
    view = finder.findRequiredView(source, 2131689650, "field 'mEditContent'");
    target.mEditContent = finder.castView(view, 2131689650, "field 'mEditContent'");
    view = finder.findRequiredView(source, 2131689645, "field 'thumbGLPhoto'");
    target.thumbGLPhoto = finder.castView(view, 2131689645, "field 'thumbGLPhoto'");
    view = finder.findRequiredView(source, 2131689651, "field 'mImgViAddPhoto' and method 'onAddPicOnClick'");
    target.mImgViAddPhoto = finder.castView(view, 2131689651, "field 'mImgViAddPhoto'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onAddPicOnClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689652, "method 'onNextOnClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onNextOnClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.mEditTitle = null;
    target.mEditContent = null;
    target.thumbGLPhoto = null;
    target.mImgViAddPhoto = null;
  }
}
