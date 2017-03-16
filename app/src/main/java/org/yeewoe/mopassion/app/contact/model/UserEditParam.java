package org.yeewoe.mopassion.app.contact.model;

import org.yeewoe.mopassion.app.common.model.BaseParam;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.template.media.TMediaFactory;
import org.yeewoe.mopassion.template.media.TMediaImage;

import java.io.File;
import java.util.List;

/**
 * 用户编辑param
 * Created by wyw on 2016/5/26.
 */
public class UserEditParam extends BaseParam<User> {

    public List<TMediaImage> mediaImageList;
    public List<File> fileList;
    public long sid;
    public String account;
    public String nick;
    public String signature;
    public String address;
    public long birthday;

    public UserEditParam(List<TMediaImage> mediaImageList, List<File> fileList, long sid, String account, String nick, String signature, String address, long birthday) {
        this.mediaImageList = mediaImageList;
        this.fileList = fileList;
        this.sid = sid;
        this.account = account;
        this.nick = nick;
        this.signature = signature;
        this.address = address;
        this.birthday = birthday;
    }

    @Override public User toPo() {
        User user = new User();
        user.setSid(sid);
        user.setNick(nick);
        user.setAccount(account);
        user.setSignature(signature);
        user.setHeads(TMediaFactory.getInstance().fromTList("", 0, mediaImageList));
        user.setAddress(this.address);
        user.setAge2(this.birthday);
        return user;
    }
}
