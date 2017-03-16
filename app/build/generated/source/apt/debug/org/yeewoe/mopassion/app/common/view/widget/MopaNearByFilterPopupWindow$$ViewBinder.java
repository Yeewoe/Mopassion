// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.common.view.widget;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MopaNearByFilterPopupWindow$$ViewBinder<T extends org.yeewoe.mopassion.app.common.view.widget.MopaNearByFilterPopupWindow> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689833, "field 'mTxtMenSeekMen' and method 'menSeekMen'");
    target.mTxtMenSeekMen = finder.castView(view, 2131689833, "field 'mTxtMenSeekMen'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.menSeekMen(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689834, "field 'mTxtMenSeekWomen' and method 'menSeekWomen'");
    target.mTxtMenSeekWomen = finder.castView(view, 2131689834, "field 'mTxtMenSeekWomen'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.menSeekWomen(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689835, "field 'mTxtWomenSeekMen' and method 'womenSeekMen'");
    target.mTxtWomenSeekMen = finder.castView(view, 2131689835, "field 'mTxtWomenSeekMen'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.womenSeekMen(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689836, "field 'mTxtWomenSeekWomen' and method 'womenSeekWomen'");
    target.mTxtWomenSeekWomen = finder.castView(view, 2131689836, "field 'mTxtWomenSeekWomen'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.womenSeekWomen(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.mTxtMenSeekMen = null;
    target.mTxtMenSeekWomen = null;
    target.mTxtWomenSeekMen = null;
    target.mTxtWomenSeekWomen = null;
  }
}
