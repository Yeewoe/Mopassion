// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.nearby.view.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class NearTrendLineAdapter$TrendLineViewHolder$$ViewBinder<T extends org.yeewoe.mopassion.app.nearby.view.adapter.NearTrendLineAdapter.TrendLineViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689797, "field 'mTxtUserName'");
    target.mTxtUserName = finder.castView(view, 2131689797, "field 'mTxtUserName'");
    view = finder.findRequiredView(source, 2131689802, "field 'mGenderViewMain'");
    target.mGenderViewMain = finder.castView(view, 2131689802, "field 'mGenderViewMain'");
    view = finder.findRequiredView(source, 2131689805, "field 'mTxtTrendContent'");
    target.mTxtTrendContent = finder.castView(view, 2131689805, "field 'mTxtTrendContent'");
    view = finder.findRequiredView(source, 2131689803, "field 'mTxtTrendDistance'");
    target.mTxtTrendDistance = finder.castView(view, 2131689803, "field 'mTxtTrendDistance'");
    view = finder.findRequiredView(source, 2131689804, "field 'mTxtTrendTime'");
    target.mTxtTrendTime = finder.castView(view, 2131689804, "field 'mTxtTrendTime'");
    view = finder.findRequiredView(source, 2131689801, "field 'mImgviHead'");
    target.mImgviHead = finder.castView(view, 2131689801, "field 'mImgviHead'");
    view = finder.findRequiredView(source, 2131689645, "field 'mThumbGLPhoto'");
    target.mThumbGLPhoto = finder.castView(view, 2131689645, "field 'mThumbGLPhoto'");
  }

  @Override public void unbind(T target) {
    target.mTxtUserName = null;
    target.mGenderViewMain = null;
    target.mTxtTrendContent = null;
    target.mTxtTrendDistance = null;
    target.mTxtTrendTime = null;
    target.mImgviHead = null;
    target.mThumbGLPhoto = null;
  }
}
