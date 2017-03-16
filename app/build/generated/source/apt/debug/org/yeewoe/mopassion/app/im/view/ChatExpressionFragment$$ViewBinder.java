// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.im.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ChatExpressionFragment$$ViewBinder<T extends org.yeewoe.mopassion.app.im.view.ChatExpressionFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 16908306, "field 'mFragmentTabHost'");
    target.mFragmentTabHost = finder.castView(view, 16908306, "field 'mFragmentTabHost'");
    view = finder.findRequiredView(source, 2131689917, "field 'mViewRadioContainer'");
    target.mViewRadioContainer = view;
    view = finder.findRequiredView(source, 2131689919, "field 'mExpressButton'");
    target.mExpressButton = finder.castView(view, 2131689919, "field 'mExpressButton'");
    view = finder.findRequiredView(source, 2131689918, "field 'mExpressLayout'");
    target.mExpressLayout = finder.castView(view, 2131689918, "field 'mExpressLayout'");
    view = finder.findRequiredView(source, 2131689922, "field 'mMomoExpressButton'");
    target.mMomoExpressButton = finder.castView(view, 2131689922, "field 'mMomoExpressButton'");
    view = finder.findRequiredView(source, 2131689921, "field 'mMomoExpressLayout'");
    target.mMomoExpressLayout = finder.castView(view, 2131689921, "field 'mMomoExpressLayout'");
    view = finder.findRequiredView(source, 2131689925, "field 'mNodExpressButton'");
    target.mNodExpressButton = finder.castView(view, 2131689925, "field 'mNodExpressButton'");
    view = finder.findRequiredView(source, 2131689924, "field 'mNodExpressLayout'");
    target.mNodExpressLayout = finder.castView(view, 2131689924, "field 'mNodExpressLayout'");
    view = finder.findRequiredView(source, 2131689928, "field 'mSendBtn'");
    target.mSendBtn = finder.castView(view, 2131689928, "field 'mSendBtn'");
  }

  @Override public void unbind(T target) {
    target.mFragmentTabHost = null;
    target.mViewRadioContainer = null;
    target.mExpressButton = null;
    target.mExpressLayout = null;
    target.mMomoExpressButton = null;
    target.mMomoExpressLayout = null;
    target.mNodExpressButton = null;
    target.mNodExpressLayout = null;
    target.mSendBtn = null;
  }
}
