// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.nearby.view.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UserTrendLineAdapter$UserTrendAdapterViewHolder$$ViewBinder<T extends org.yeewoe.mopassion.app.nearby.view.adapter.UserTrendLineAdapter.UserTrendAdapterViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689805, "field 'mTxtTrendContent'");
    target.mTxtTrendContent = finder.castView(view, 2131689805, "field 'mTxtTrendContent'");
    view = finder.findRequiredView(source, 2131689801, "field 'mImgviHead'");
    target.mImgviHead = finder.castView(view, 2131689801, "field 'mImgviHead'");
    view = finder.findRequiredView(source, 2131689645, "field 'mThumbGLPhoto'");
    target.mThumbGLPhoto = finder.castView(view, 2131689645, "field 'mThumbGLPhoto'");
  }

  @Override public void unbind(T target) {
    target.mTxtTrendContent = null;
    target.mImgviHead = null;
    target.mThumbGLPhoto = null;
  }
}
