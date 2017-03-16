package org.yeewoe.mopassion.utils;

import org.yeewoe.mopassion.auth.FacebookUtil;
import org.yeewoe.mopassion.auth.TwitterUtil;
import org.yeewoe.mopassion.mangers.ClientManger;

/**
 * Created by acc on 2016/5/19.
 */
public class AuthUtil {
    public static void logout() {
        ClientManger.getInstance().setTicket(""); // 清空ticket
        FacebookUtil.logout();
        TwitterUtil.logout();
    }
}
