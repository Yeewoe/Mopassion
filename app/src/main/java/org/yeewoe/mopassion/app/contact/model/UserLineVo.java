package org.yeewoe.mopassion.app.contact.model;

import android.os.Parcel;
import android.support.annotation.NonNull;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.mopassion.MopaApplication;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.model.BaseLineVo;
import org.yeewoe.mopassion.db.po.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Created by wyw on 2016/4/9.
 */
public class UserLineVo extends BaseLineVo<User> {
    protected String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public UserLineVo(User source, Long uid, List<User> users) {
        super(source, uid, users);
    }
    protected UserLineVo(Parcel in) {
        super(in);
    }

    public static final class Convert {
        @NonNull public static List<UserLineVo> convert(List<User> users) {
            if (users == null) {
                return new ArrayList<>();
            }
            List<UserLineVo> result = new ArrayList<>();
            for (User user : users) {
                if (user != null) {
                    result.add(convert(user));
                }
            }
            return result;
        }

        @NonNull public static UserLineVo convert(@NonNull User user) {
            List<User> users = new ArrayList<>();
            users.add(user); // 这里组装users是为了使用公共构造方法

            UserLineVo result = new UserLineVo(user, user.getSid(), users);
            result.signature = user.getSignature();
            return result;
        }
    }
}
