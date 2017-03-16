// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.nearby.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TrendDetailActivity$$ViewBinder<T extends org.yeewoe.mopassion.app.nearby.view.TrendDetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689640, "field 'mImgViBack'");
    target.mImgViBack = finder.castView(view, 2131689640, "field 'mImgViBack'");
    view = finder.findRequiredView(source, 2131689637, "field 'mImgHead'");
    target.mImgHead = finder.castView(view, 2131689637, "field 'mImgHead'");
    view = finder.findRequiredView(source, 2131689638, "field 'mTxtName'");
    target.mTxtName = finder.castView(view, 2131689638, "field 'mTxtName'");
    view = finder.findRequiredView(source, 2131689641, "field 'mTxtSite'");
    target.mTxtSite = finder.castView(view, 2131689641, "field 'mTxtSite'");
    view = finder.findRequiredView(source, 2131689642, "field 'mTxtTime'");
    target.mTxtTime = finder.castView(view, 2131689642, "field 'mTxtTime'");
    view = finder.findRequiredView(source, 2131689643, "field 'mTxtTitle'");
    target.mTxtTitle = finder.castView(view, 2131689643, "field 'mTxtTitle'");
    view = finder.findRequiredView(source, 2131689644, "field 'mTxtContent'");
    target.mTxtContent = finder.castView(view, 2131689644, "field 'mTxtContent'");
    view = finder.findRequiredView(source, 2131689645, "field 'mThumbGLPhoto'");
    target.mThumbGLPhoto = finder.castView(view, 2131689645, "field 'mThumbGLPhoto'");
    view = finder.findRequiredView(source, 2131689646, "field 'mSpanContact'");
    target.mSpanContact = view;
    view = finder.findRequiredView(source, 2131689647, "field 'mBtnContact' and method 'onContactBtnClick'");
    target.mBtnContact = finder.castView(view, 2131689647, "field 'mBtnContact'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onContactBtnClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689639, "field 'mBtnFollow' and method 'onFollowBtnClick'");
    target.mBtnFollow = finder.castView(view, 2131689639, "field 'mBtnFollow'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onFollowBtnClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.mImgViBack = null;
    target.mImgHead = null;
    target.mTxtName = null;
    target.mTxtSite = null;
    target.mTxtTime = null;
    target.mTxtTitle = null;
    target.mTxtContent = null;
    target.mThumbGLPhoto = null;
    target.mSpanContact = null;
    target.mBtnContact = null;
    target.mBtnFollow = null;
  }
}
