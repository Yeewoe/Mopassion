package org.yeewoe.mopassion.mangers;

import android.support.annotation.NonNull;

import org.yeewoe.mopassion.MopaApplication;
import org.yeewoe.mopassion.constants.SpConstants;
import org.yeewoe.mopassion.db.dao.DaoManager;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.auth.FacebookUtil;
import org.yeewoe.mopassion.utils.LogCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 登录用户运行时
 * Created by wyw on 2016/3/28.
 */
public class UserDataMananger {
    private static UserDataMananger ourInstance = new UserDataMananger();

    private HashMap<Long, User> users = new HashMap<Long, User>();

    public static UserDataMananger getInstance() {
        return ourInstance;
    }

    public User me; //自己的数据.

    private UserDataMananger() {
    }

    @NonNull
    public User getUser(long uid) {
        User u = users.get(uid);
        if (u == null) {
            u = new User();
            u.setSid(uid);
            users.put(uid, u);
        }
        return u;
    }

    public User addUser(long uid, User ud) {
        return users.put(uid, ud);
    }

    public long getMeUid() {
        return getMe().getSid();
    }

    @NonNull
    public User getMe() {
        if (me == null) {
            // 防止被回收的情况
            long meUid = MopaApplication.getInstance().getSp().loadLongSharedPreference(SpConstants.Auth.LOGIN_ID);
            try {
                me = DaoManager.getUserDao().query(meUid);
                if (me == null) {
                    me = new User();
                    me.setSid(meUid);
                }
            } catch (Exception e) {
                LogCore.interfaceI("DaoManager.getUserDao().query", e);
                me = new User();
                me.setSid(meUid);
            }
        }
        return me;
    }

    @NonNull public List<User> getMeList() {
        List<User> users = new ArrayList<>();
        users.add(UserDataMananger.getInstance().getMe());
        return users;
    }

    public void setMe(@NonNull User me) {
        this.me = me;
        MopaApplication.getInstance().getSp().saveSharedPreferences(SpConstants.Auth.LOGIN_ID, me.getSid());
    }

    /**
     * 是否是facebook授权账号
     */
    public boolean isFacebook() {
        return FacebookUtil.isAccount(getMe().getAccount());
    }

    /**
     * 是否是推特授权账号
     */
    public boolean isTwitter() {
        return false;
    }
}
