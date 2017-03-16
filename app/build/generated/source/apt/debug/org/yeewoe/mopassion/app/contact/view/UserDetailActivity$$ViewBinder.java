// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.contact.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UserDetailActivity$$ViewBinder<T extends org.yeewoe.mopassion.app.contact.view.UserDetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689637, "field 'mImgHead'");
    target.mImgHead = finder.castView(view, 2131689637, "field 'mImgHead'");
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
    view = finder.findRequiredView(source, 2131689660, "field 'mLLOperationBar'");
    target.mLLOperationBar = finder.castView(view, 2131689660, "field 'mLLOperationBar'");
    view = finder.findRequiredView(source, 2131689640, "method 'onBackClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onBackClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689661, "method 'onContactClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onContactClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689662, "method 'onMarkClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onMarkClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689663, "method 'onBlackListClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onBlackListClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.mImgHead = null;
    target.mTxtNick = null;
    target.mTxtSignature = null;
    target.mRadioGroupTab = null;
    target.mRadioBtnPost = null;
    target.mRadioBtnSpace = null;
    target.mLLOperationBar = null;
  }
}
