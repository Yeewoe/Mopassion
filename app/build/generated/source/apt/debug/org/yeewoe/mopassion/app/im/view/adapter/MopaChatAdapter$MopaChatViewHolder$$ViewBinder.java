// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.im.view.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MopaChatAdapter$MopaChatViewHolder$$ViewBinder<T extends org.yeewoe.mopassion.app.im.view.adapter.MopaChatAdapter.MopaChatViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689758, "field 'mRLRoot'");
    target.mRLRoot = finder.castView(view, 2131689758, "field 'mRLRoot'");
    view = finder.findRequiredView(source, 2131689741, "field 'mLLContainer'");
    target.mLLContainer = finder.castView(view, 2131689741, "field 'mLLContainer'");
    view = finder.findRequiredView(source, 2131689743, "field 'mImgViewHead'");
    target.mImgViewHead = finder.castView(view, 2131689743, "field 'mImgViewHead'");
    view = finder.findRequiredView(source, 2131689766, "field 'mTxtContent'");
    target.mTxtContent = finder.castView(view, 2131689766, "field 'mTxtContent'");
    view = finder.findRequiredView(source, 2131689767, "field 'mImgViContent'");
    target.mImgViContent = finder.castView(view, 2131689767, "field 'mImgViContent'");
    view = finder.findOptionalView(source, 2131689763, null);
    target.mImgViewFail = finder.castView(view, 2131689763, "field 'mImgViewFail'");
    view = finder.findOptionalView(source, 2131689762, null);
    target.mProgressSending = finder.castView(view, 2131689762, "field 'mProgressSending'");
    view = finder.findOptionalView(source, 2131689761, null);
    target.mCbItemSelect = finder.castView(view, 2131689761, "field 'mCbItemSelect'");
  }

  @Override public void unbind(T target) {
    target.mRLRoot = null;
    target.mLLContainer = null;
    target.mImgViewHead = null;
    target.mTxtContent = null;
    target.mImgViContent = null;
    target.mImgViewFail = null;
    target.mProgressSending = null;
    target.mCbItemSelect = null;
  }
}
