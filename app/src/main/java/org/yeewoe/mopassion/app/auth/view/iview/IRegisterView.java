package org.yeewoe.mopassion.app.auth.view.iview;

import org.yeewoe.mopassion.app.common.view.interfaces.ITabView;
import org.yeewoe.mopassion.app.common.view.interfaces.IWaitView;

/**
 * Created by wyw on 2016/3/26.
 */
public interface IRegisterView extends ITabView, IWaitView {
    String DATA_KEY_NICK_NAME = "nick";
    String DATA_KEY_ACCOUNT = "account";
    String DATA_KEY_PASSWORD = "password";
    String DATA_KEY_GENDER = "gender";

    void intentToLogin();

}
