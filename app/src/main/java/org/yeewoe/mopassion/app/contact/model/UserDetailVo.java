package org.yeewoe.mopassion.app.contact.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.yeewoe.mopassion.app.common.model.BaseVo;
import org.yeewoe.mopassion.app.common.model.UserHeadVo;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.template.media.TMediaFactory;
import org.yeewoe.mopassion.template.media.TMediaImage;
import org.yeewoe.mopassion.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyw on 2016/4/26.
 */
public class UserDetailVo extends BaseVo<User> implements Parcelable {
    private UserHeadVo userHeadVo;
    private String nickname;
    private String account;
    private String signature;
    private String registerTime;
    private List<TMediaImage> headPhotos;
    private String address;
    private long birthday;

    protected UserDetailVo(Parcel in) {
        super(in);
        userHeadVo = in.readParcelable(UserHeadVo.class.getClassLoader());
        nickname = in.readString();
        account = in.readString();
        signature = in.readString();
        registerTime = in.readString();
        headPhotos = in.createTypedArrayList(TMediaImage.CREATOR);
        address = in.readString();
        birthday = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(userHeadVo, flags);
        dest.writeString(nickname);
        dest.writeString(account);
        dest.writeString(signature);
        dest.writeString(registerTime);
        dest.writeTypedList(headPhotos);
        dest.writeString(address);
        dest.writeLong(birthday);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserDetailVo> CREATOR = new Creator<UserDetailVo>() {
        @Override
        public UserDetailVo createFromParcel(Parcel in) {
            return new UserDetailVo(in);
        }

        @Override
        public UserDetailVo[] newArray(int size) {
            return new UserDetailVo[size];
        }
    };

    public UserHeadVo getUserHeadVo() {
        return userHeadVo;
    }

    public void setUserHeadVo(UserHeadVo userHeadVo) {
        this.userHeadVo = userHeadVo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public UserDetailVo(User source) {
        super(source);
    }

    public List<TMediaImage> getHeadPhotos() {
        return headPhotos;
    }

    public void setHeadPhotos(List<TMediaImage> headPhotos) {
        this.headPhotos = headPhotos;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public static class Convertor {
        public static UserDetailVo convert(User user) {
            UserDetailVo userDetailVo = new UserDetailVo(user);
            userDetailVo.userHeadVo = UserHeadVo.Convertor.convert(user);
            userDetailVo.setNickname(user.getNick());
            userDetailVo.setAccount(user.getAccount());
            userDetailVo.setSignature(user.getSignature());
            userDetailVo.setRegisterTime(TimeUtil.parseTime(user.getCreateTime(), TimeUtil.pattern5));
            userDetailVo.setHeadPhotos(TMediaFactory.getInstance().toTMediaImage(user.getHeads()));
            userDetailVo.setAddress(user.getAddress());
            userDetailVo.setBirthday(user.getAge2());
            return userDetailVo;
        }
    }
}
