// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.maintab.view.apdaters;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ImListAdapter$ImListViewHolder$$ViewBinder<T extends org.yeewoe.mopassion.app.maintab.view.apdaters.ImListAdapter.ImListViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689789, "field 'mChatNameTV'");
    target.mChatNameTV = finder.castView(view, 2131689789, "field 'mChatNameTV'");
    view = finder.findRequiredView(source, 2131689792, "field 'mChatLastContentTV'");
    target.mChatLastContentTV = finder.castView(view, 2131689792, "field 'mChatLastContentTV'");
    view = finder.findRequiredView(source, 2131689787, "field 'mChatLastTimeTV'");
    target.mChatLastTimeTV = finder.castView(view, 2131689787, "field 'mChatLastTimeTV'");
    view = finder.findRequiredView(source, 2131689637, "field 'mHeadSimpleDraweeView'");
    target.mHeadSimpleDraweeView = finder.castView(view, 2131689637, "field 'mHeadSimpleDraweeView'");
  }

  @Override public void unbind(T target) {
    target.mChatNameTV = null;
    target.mChatLastContentTV = null;
    target.mChatLastTimeTV = null;
    target.mHeadSimpleDraweeView = null;
  }
}
