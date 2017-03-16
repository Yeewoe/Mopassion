// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.nearby.view.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class NearByUserGridAdapter$UserLineViewHolder$$ViewBinder<T extends org.yeewoe.mopassion.app.nearby.view.adapter.NearByUserGridAdapter.UserLineViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689781, "field 'mImgViHead'");
    target.mImgViHead = finder.castView(view, 2131689781, "field 'mImgViHead'");
    view = finder.findRequiredView(source, 2131689782, "field 'mTxtDistance'");
    target.mTxtDistance = finder.castView(view, 2131689782, "field 'mTxtDistance'");
    view = finder.findRequiredView(source, 2131689783, "field 'mGenderViewMain'");
    target.mGenderViewMain = finder.castView(view, 2131689783, "field 'mGenderViewMain'");
  }

  @Override public void unbind(T target) {
    target.mImgViHead = null;
    target.mTxtDistance = null;
    target.mGenderViewMain = null;
  }
}
