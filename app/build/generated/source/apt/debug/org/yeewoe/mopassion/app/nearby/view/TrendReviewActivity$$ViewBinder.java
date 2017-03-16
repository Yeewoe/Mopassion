// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.nearby.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TrendReviewActivity$$ViewBinder<T extends org.yeewoe.mopassion.app.nearby.view.TrendReviewActivity> implements ViewBinder<T> {
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
    view = finder.findRequiredView(source, 2131689653, "method 'onSubmitClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onSubmitClick(p0);
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
  }
}
