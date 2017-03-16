package org.yeewoe.mopassion.app.auth.view.iview;

import org.yeewoe.mopassion.app.common.view.interfaces.IActivityView;

/**
 * Created by acc on 2016/5/19.
 */
public interface ILauncherView extends IActivityView {
    void intentToLogin();

    void endAuth();

    void intentToMain();
}
