// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.contact.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ContactFollowerAndFansFragment$$ViewBinder<T extends org.yeewoe.mopassion.app.contact.view.ContactFollowerAndFansFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689739, "method 'onFollowerItemClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onFollowerItemClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131689740, "method 'onFansItemClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onFansItemClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
  }
}
