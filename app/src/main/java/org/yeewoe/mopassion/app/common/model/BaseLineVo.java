package org.yeewoe.mopassion.app.common.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.mopassion.db.po.BasePo;
import org.yeewoe.mopassion.db.po.Message;
import org.yeewoe.mopassion.db.po.User;

import java.util.List;

/**
 * Created by wyw on 2016/4/4.
 */
public class BaseLineVo<T extends BasePo> extends BaseVo<T> implements Parcelable {
    protected UserHeadVo user;

    public BaseLineVo(T source, Long uid, List<User> users) {
        super(source);
        if (uid != null) {
            if (Checks.check(users)) {
                for (User user : users) {
                    if (uid == user.getSid()) {
                        this.user = UserHeadVo.Convertor.convert(user);
                    }
                }
            }
        } else {
            this.user = new UserHeadVo();
            this.user.setSid(0L);
        }
    }

    protected BaseLineVo(Parcel in) {
        super(in);
        user = in.readParcelable(UserHeadVo.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(user, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BaseLineVo> CREATOR = new Creator<BaseLineVo>() {
        @Override
        public BaseLineVo createFromParcel(Parcel in) {
            return new BaseLineVo(in);
        }

        @Override
        public BaseLineVo[] newArray(int size) {
            return new BaseLineVo[size];
        }
    };

    public UserHeadVo getUser() {
        return user;
    }

    public void setUser(UserHeadVo user) {
        this.user = user;
    }

}
