// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.im.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MopaChatActivity$$ViewBinder<T extends org.yeewoe.mopassion.app.im.view.MopaChatActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689615, "field 'mListViewMain'");
    target.mListViewMain = finder.castView(view, 2131689615, "field 'mListViewMain'");
    view = finder.findRequiredView(source, 2131689613, "field 'mPanelLayout'");
    target.mPanelLayout = finder.castView(view, 2131689613, "field 'mPanelLayout'");
    view = finder.findRequiredView(source, 2131689946, "field 'mEditChat'");
    target.mEditChat = finder.castView(view, 2131689946, "field 'mEditChat'");
    view = finder.findRequiredView(source, 2131689944, "method 'onPluseBtnClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onPluseBtnClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689947, "method 'onExpressBtnClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onExpressBtnClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689942, "method 'onVoiceBtnClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onVoiceBtnClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689943, "method 'onSendBtnClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onSendBtnClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.mListViewMain = null;
    target.mPanelLayout = null;
    target.mEditChat = null;
  }
}
