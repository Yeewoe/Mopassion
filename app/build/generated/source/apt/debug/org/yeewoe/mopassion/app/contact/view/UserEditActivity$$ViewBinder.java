// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.contact.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UserEditActivity$$ViewBinder<T extends org.yeewoe.mopassion.app.contact.view.UserEditActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689665, "field 'mTILNick'");
    target.mTILNick = finder.castView(view, 2131689665, "field 'mTILNick'");
    view = finder.findRequiredView(source, 2131689666, "field 'mTILSignature'");
    target.mTILSignature = finder.castView(view, 2131689666, "field 'mTILSignature'");
    view = finder.findRequiredView(source, 2131689667, "field 'mTILAddress'");
    target.mTILAddress = finder.castView(view, 2131689667, "field 'mTILAddress'");
    view = finder.findRequiredView(source, 2131689669, "field 'mTxtBirthday'");
    target.mTxtBirthday = finder.castView(view, 2131689669, "field 'mTxtBirthday'");
    view = finder.findRequiredView(source, 2131689664, "field 'mHeadPhotoGridLayoutMain'");
    target.mHeadPhotoGridLayoutMain = finder.castView(view, 2131689664, "field 'mHeadPhotoGridLayoutMain'");
    view = finder.findRequiredView(source, 2131689668, "method 'onBirthdayClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onBirthdayClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.mTILNick = null;
    target.mTILSignature = null;
    target.mTILAddress = null;
    target.mTxtBirthday = null;
    target.mHeadPhotoGridLayoutMain = null;
  }
}
