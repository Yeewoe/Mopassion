// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.maintab.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MeFragment$$ViewBinder<T extends org.yeewoe.mopassion.app.maintab.view.MeFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689743, "field 'mImgViHead'");
    target.mImgViHead = finder.castView(view, 2131689743, "field 'mImgViHead'");
    view = finder.findRequiredView(source, 2131689654, "field 'mTxtNick'");
    target.mTxtNick = finder.castView(view, 2131689654, "field 'mTxtNick'");
    view = finder.findRequiredView(source, 2131689655, "field 'mTxtSignature'");
    target.mTxtSignature = finder.castView(view, 2131689655, "field 'mTxtSignature'");
    view = finder.findRequiredView(source, 2131689656, "field 'mRadioGroupTab'");
    target.mRadioGroupTab = finder.castView(view, 2131689656, "field 'mRadioGroupTab'");
    view = finder.findRequiredView(source, 2131689657, "field 'mRadioBtnPost'");
    target.mRadioBtnPost = finder.castView(view, 2131689657, "field 'mRadioBtnPost'");
    view = finder.findRequiredView(source, 2131689658, "field 'mRadioBtnSpace'");
    target.mRadioBtnSpace = finder.castView(view, 2131689658, "field 'mRadioBtnSpace'");
    view = finder.findRequiredView(source, 2131689748, "field 'mRadioBtnFollowed'");
    target.mRadioBtnFollowed = finder.castView(view, 2131689748, "field 'mRadioBtnFollowed'");
    view = finder.findRequiredView(source, 2131689744, "method 'onTitleClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onTitleClick();
        }
      });
    view = finder.findRequiredView(source, 2131689745, "method 'onSettingClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onSettingClick();
        }
      });
  }

  @Override public void unbind(T target) {
    target.mImgViHead = null;
    target.mTxtNick = null;
    target.mTxtSignature = null;
    target.mRadioGroupTab = null;
    target.mRadioBtnPost = null;
    target.mRadioBtnSpace = null;
    target.mRadioBtnFollowed = null;
  }
}
