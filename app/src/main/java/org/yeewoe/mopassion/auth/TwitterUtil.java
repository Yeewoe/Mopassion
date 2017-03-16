package org.yeewoe.mopassion.auth;

import android.content.Context;
import android.text.TextUtils;

import com.facebook.login.LoginResult;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import org.yeewoe.mopassion.R;

import io.fabric.sdk.android.Fabric;

/**
 * Created by wyw on 2016/6/24.
 */
public class TwitterUtil {

    public static final String ACCOUNT_PREFIX = "tt";

    public static String getAccount(TwitterSession loginResult) {
        return ACCOUNT_PREFIX + loginResult.getUserId();
    }

    public static String getPassword(TwitterSession loginResult) {
        return ACCOUNT_PREFIX + loginResult.getUserId();
    }

    public static boolean isAccount(String account) {
        if (!TextUtils.isEmpty(account) && account.startsWith(ACCOUNT_PREFIX)) {
            return true;
        }
        return false;
    }

    public static void logout() {

    }

    public static void init(Context context) {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(context.getString(R.string.twitter_app_id), context.getString(R.string.twitter_app_secret));
        Fabric.with(context, new TwitterCore(authConfig));

    }
}
