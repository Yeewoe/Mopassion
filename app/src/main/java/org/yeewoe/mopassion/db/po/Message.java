package org.yeewoe.mopassion.db.po;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.yeewoe.mopassion.db.core.DbConstants;

/**
 * IM消息PO
 * Created by wyw on 2016/3/30.
 */
@DatabaseTable(tableName = DbConstants.TableInfo.Message.TABLE_NAME)
public class Message extends BasePo {

    @DatabaseField(columnName = DbConstants.TableInfo.Message.CONTENT)
    private String content;

    @DatabaseField(columnName = DbConstants.TableInfo.Message.TO_UID)
    private Long toUid;

    @DatabaseField(columnName = DbConstants.TableInfo.Message.FROM_UID)
    private Long fromUid;

    @DatabaseField(columnName = DbConstants.TableInfo.Message.OTHER_UID)
    private Long otherUid;

    @DatabaseField(columnName = DbConstants.TableInfo.Message.SELF_UID)
    private Long selfUid;

    @DatabaseField(columnName = DbConstants.TableInfo.Message.TO_GID)
    private Long toGid;

    @DatabaseField(columnName = DbConstants.TableInfo.Message.FROM_GID)
    private Long fromGid;

    @DatabaseField(columnName = DbConstants.TableInfo.Message.OTHER_GID)
    private Long otherGid;

    @DatabaseField(columnName = DbConstants.TableInfo.Base.SEND_STATUS, dataType = DataType.ENUM_INTEGER)
    private SendStatus sendStatus;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getFromGid() {
        return fromGid;
    }

    public void setFromGid(Long fromGid) {
        this.fromGid = fromGid;
    }

    public Long getFromUid() {
        return fromUid;
    }

    public void setFromUid(Long fromUid) {
        this.fromUid = fromUid;
    }

    public Long getToGid() {
        return toGid;
    }

    public void setToGid(Long toGid) {
        this.toGid = toGid;
    }

    public Long getToUid() {
        return toUid;
    }

    public void setToUid(Long toUid) {
        this.toUid = toUid;
    }

    public Long getOtherUid() {
        return otherUid;
    }

    public void setOtherUid(Long otherUid) {
        this.otherUid = otherUid;
    }

    public Long getSelfUid() {
        return selfUid;
    }

    public void setSelfUid(Long selfUid) {
        this.selfUid = selfUid;
    }

    public Long getOtherGid() {
        return otherGid;
    }

    public void setOtherGid(Long otherGid) {
        this.otherGid = otherGid;
    }

    public SendStatus getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(SendStatus sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Message() {
        sendStatus = SendStatus.SUCCESS;
    }
}
