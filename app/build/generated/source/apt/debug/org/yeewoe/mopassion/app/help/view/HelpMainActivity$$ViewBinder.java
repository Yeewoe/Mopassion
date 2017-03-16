// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.help.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HelpMainActivity$$ViewBinder<T extends org.yeewoe.mopassion.app.help.view.HelpMainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689603, "field 'mTxtCurrentVersion'");
    target.mTxtCurrentVersion = finder.castView(view, 2131689603, "field 'mTxtCurrentVersion'");
    view = finder.findRequiredView(source, 2131689601, "method 'onVersionCheckClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onVersionCheckClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689604, "method 'onVersionIntroClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onVersionIntroClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689605, "method 'onProtocolClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onProtocolClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.mTxtCurrentVersion = null;
  }
}
