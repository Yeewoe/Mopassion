package org.yeewoe.mopassion.app.auth.view.iview;

import org.yeewoe.mopassion.app.common.view.interfaces.IActivityView;

/**
 * Created by wyw on 2016/3/25.
 */
public interface ILoginView extends IActivityView {
    void intentToRegister();
    void intentToMain();
    void setPasswordError(int errorId);

    void intentToLogin();
}
