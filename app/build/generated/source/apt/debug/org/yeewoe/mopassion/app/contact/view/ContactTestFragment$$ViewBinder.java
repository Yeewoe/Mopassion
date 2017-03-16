// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.contact.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ContactTestFragment$$ViewBinder<T extends org.yeewoe.mopassion.app.contact.view.ContactTestFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689615, "field 'mListViewMain'");
    target.mListViewMain = finder.castView(view, 2131689615, "field 'mListViewMain'");
  }

  @Override public void unbind(T target) {
    target.mListViewMain = null;
  }
}
