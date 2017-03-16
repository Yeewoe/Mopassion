package org.yeewoe.mopassion.auth;

import android.content.Context;
import android.text.TextUtils;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

/**
 * Created by wyw on 2016/6/24.
 */
public class FacebookUtil {

    public static final String ACCOUNT_PREFIX = "fb";

    public static void init(Context context) {
        FacebookSdk.sdkInitialize(context);
    }

    public static String getAccount(LoginResult loginResult) {
        return ACCOUNT_PREFIX + loginResult.getAccessToken().getUserId();
    }

    public static String getPassword(LoginResult loginResult) {
        return ACCOUNT_PREFIX + loginResult.getAccessToken().getUserId();
    }

    public static boolean isAccount(String account) {
        if (!TextUtils.isEmpty(account) && account.startsWith(ACCOUNT_PREFIX)) {
            return true;
        }
        return false;
    }

    public static void logout() {
        LoginManager.getInstance().logOut();
    }
}
