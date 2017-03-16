// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.common.view.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MopaListViewFragment$$ViewBinder<T extends org.yeewoe.mopassion.app.common.view.fragment.MopaListViewFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findOptionalView(source, 2131689479, null);
    target.mListViewMain = finder.castView(view, 2131689479, "field 'mListViewMain'");
    view = finder.findRequiredView(source, 2131689481, "field 'mPtrMain'");
    target.mPtrMain = finder.castView(view, 2131689481, "field 'mPtrMain'");
    view = finder.findOptionalView(source, 2131689480, null);
    target.mLoadMoreMain = finder.castView(view, 2131689480, "field 'mLoadMoreMain'");
  }

  @Override public void unbind(T target) {
    target.mListViewMain = null;
    target.mPtrMain = null;
    target.mLoadMoreMain = null;
  }
}
