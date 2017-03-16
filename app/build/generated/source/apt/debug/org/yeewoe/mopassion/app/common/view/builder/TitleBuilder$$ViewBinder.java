// Generated code from Butter Knife. Do not modify!
package org.yeewoe.mopassion.app.common.view.builder;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TitleBuilder$$ViewBinder<T extends org.yeewoe.mopassion.app.common.view.builder.TitleBuilder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findOptionalView(source, 2131689813, null);
    target.layoutContiner = finder.castView(view, 2131689813, "field 'layoutContiner'");
    view = finder.findOptionalView(source, 2131689750, null);
    target.txtCenter = finder.castView(view, 2131689750, "field 'txtCenter'");
    view = finder.findOptionalView(source, 2131689817, null);
    target.txtLeft = finder.castView(view, 2131689817, "field 'txtLeft'");
    view = finder.findOptionalView(source, 2131689818, null);
    target.txtRight = finder.castView(view, 2131689818, "field 'txtRight'");
    view = finder.findOptionalView(source, 2131689814, null);
    target.imgBtnLeft = finder.castView(view, 2131689814, "field 'imgBtnLeft'");
    view = finder.findOptionalView(source, 2131689816, null);
    target.imgBtnRight = finder.castView(view, 2131689816, "field 'imgBtnRight'");
    view = finder.findOptionalView(source, 2131689815, null);
    target.imgViCenter = finder.castView(view, 2131689815, "field 'imgViCenter'");
  }

  @Override public void unbind(T target) {
    target.layoutContiner = null;
    target.txtCenter = null;
    target.txtLeft = null;
    target.txtRight = null;
    target.imgBtnLeft = null;
    target.imgBtnRight = null;
    target.imgViCenter = null;
  }
}
