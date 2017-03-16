package org.yeewoe.mopassion.app.auth.model;

import org.yeewoe.mopassion.app.common.model.BaseParam;
import org.yeewoe.mopassion.db.po.BasePo;

/**
 * Created by wyw on 2016/4/8.
 */
public class LoginParam extends BaseParam {
    public String account;
    public String password;

    public LoginParam(String account, String password) {
        this.account = account;
        this.password = password;
    }

    @Override public BasePo toPo() {
        return null;
    }
}
