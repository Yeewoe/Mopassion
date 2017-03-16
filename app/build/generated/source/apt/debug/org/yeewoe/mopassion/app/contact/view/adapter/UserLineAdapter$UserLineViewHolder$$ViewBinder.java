// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.contact.view.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UserLineAdapter$UserLineViewHolder$$ViewBinder<T extends org.yeewoe.mopassion.app.contact.view.adapter.UserLineAdapter.UserLineViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689797, "field 'mTxtUserName'");
    target.mTxtUserName = finder.castView(view, 2131689797, "field 'mTxtUserName'");
    view = finder.findRequiredView(source, 2131689800, "field 'mTxtUserContent'");
    target.mTxtUserContent = finder.castView(view, 2131689800, "field 'mTxtUserContent'");
    view = finder.findRequiredView(source, 2131689801, "field 'mImgViHead'");
    target.mImgViHead = finder.castView(view, 2131689801, "field 'mImgViHead'");
    view = finder.findRequiredView(source, 2131689802, "field 'mGenderView'");
    target.mGenderView = finder.castView(view, 2131689802, "field 'mGenderView'");
  }

  @Override public void unbind(T target) {
    target.mTxtUserName = null;
    target.mTxtUserContent = null;
    target.mImgViHead = null;
    target.mGenderView = null;
  }
}
