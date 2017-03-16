package org.yeewoe.mopassion.db.po;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.yeewoe.mopassion.db.core.DbConstants;

import java.util.List;

/**
 * 用户PO
 * Created by wyw on 2016/3/4.
 */
@DatabaseTable(tableName = DbConstants.TableInfo.User.TABLE_NAME)
public class User extends BasePo {
    @DatabaseField(columnName = DbConstants.TableInfo.User.ACCOUNT, canBeNull = false,  index = true)
    private String account;
    @DatabaseField(columnName = DbConstants.TableInfo.User.NICK, canBeNull = false)
    private String nick;

    /**
     * 生日，格林威治毫秒时间戳， 秒级
     */
    @DatabaseField(columnName = DbConstants.TableInfo.User.AGE, canBeNull = true)
    private Integer age;
    @DatabaseField(columnName = DbConstants.TableInfo.User.GENDER, canBeNull = true)
    private Integer gender;
    @DatabaseField(columnName = DbConstants.TableInfo.User.STAR, canBeNull = true)
    private Integer star;
    @DatabaseField(columnName = DbConstants.TableInfo.User.SIGNATURE, canBeNull = true)
    private String signature;
    @DatabaseField(columnName = DbConstants.TableInfo.User.JSON_HEADS)
    private String jsonHeads;
    private List<Media> heads;

    @DatabaseField(columnName = DbConstants.TableInfo.User.ADDRESS)
    private String address;

//    @DatabaseField(columnName = DbConstants.TableInfo.User.JOB)
//    private String job;
//    @DatabaseField(columnName = DbConstants.TableInfo.User.COLLEGE)
//    private String college;
//    @DatabaseField(columnName = DbConstants.TableInfo.User.HOMELAND)
//    private String homeland;
//    @DatabaseField(columnName = DbConstants.TableInfo.User.SPORT)
//    private String sport;
//    @DatabaseField(columnName = DbConstants.TableInfo.User.SPORT)
//    private String music;
//    @DatabaseField(columnName = DbConstants.TableInfo.User.SPORT)
//    private String sport;

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setAge2(Long age) {
        if (age == null) {
            age = 0L;
        }
        this.age = (int) (age / 1000);
    }

    public long getAge2() {
        return age != null ? age * 1000L : 0L;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getJsonHeads() {
        return jsonHeads;
    }

    public void setJsonHeads(String jsonHeads) {
        this.jsonHeads = jsonHeads;
    }

    public List<Media> getHeads() {
        return heads;
    }

    public void setHeads(List<Media> heads) {
        this.heads = heads;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", nick='" + nick + '\'' +
                "} " + super.toString();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
