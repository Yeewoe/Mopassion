package org.yeewoe.mopassion.app.common.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.mopassion.db.dao.DaoManager;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.template.media.TMediaFactory;
import org.yeewoe.mopassion.template.media.TMediaImage;

import java.sql.SQLException;

/**
 * 用户头像VO
 * Created by wyw on 2016/4/3.
 */
public class UserHeadVo implements Parcelable {
    private long sid;
    private TMediaImage head;
    private String name;
    private Integer gender;
    private String account;

    public UserHeadVo() {

    }

    protected UserHeadVo(Parcel in) {
        sid = in.readLong();
        head = in.readParcelable(TMediaImage.class.getClassLoader());
        name = in.readString();
        int genderTemp = in.readInt();
        if (genderTemp != -1) {
            gender = genderTemp;
        }
        account = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(sid);
        dest.writeParcelable(head, flags);
        dest.writeString(name);
        if (gender != null) {
            dest.writeInt(gender);
        } else {
            dest.writeInt(-1);
        }
        dest.writeString(account);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserHeadVo> CREATOR = new Creator<UserHeadVo>() {
        @Override
        public UserHeadVo createFromParcel(Parcel in) {
            return new UserHeadVo(in);
        }

        @Override
        public UserHeadVo[] newArray(int size) {
            return new UserHeadVo[size];
        }
    };

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TMediaImage getHead() {
        return head;
    }

    public void setHead(TMediaImage head) {
        this.head = head;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public static class Convertor {
        public static UserHeadVo convert(User user) {
            UserHeadVo userHeadVo = new UserHeadVo();
            userHeadVo.setSid(user.getSid());
            userHeadVo.setName(user.getNick());
            if (Checks.check(user.getHeads())) {
                // 取最后一张
                if (user.getHeads().get(user.getHeads().size() - 1) != null) {
                    Object o = TMediaFactory.getInstance().toT(user.getHeads().get(user.getHeads().size() - 1));
                    if (o instanceof TMediaImage) {
                        userHeadVo.setHead((TMediaImage) o);
                    }
                }
            }
            userHeadVo.setGender(user.getGender());
            userHeadVo.setAccount(user.getAccount());
            return userHeadVo;
        }
    }
}
