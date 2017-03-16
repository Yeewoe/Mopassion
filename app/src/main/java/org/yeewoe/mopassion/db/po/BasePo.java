package org.yeewoe.mopassion.db.po;

import com.j256.ormlite.field.DatabaseField;

import org.yeewoe.mopassion.db.core.DbConstants;
import org.yeewoe.mopassion.utils.TimeUtil;

/**
 * 基类PO
 * Created by wyw on 2016/3/30.
 */
public class BasePo {
    @DatabaseField(columnName = DbConstants.TableInfo.Base.ID, generatedId = true, index = true)
    private int id;

    @DatabaseField(columnName = DbConstants.TableInfo.Base.SID, index = true)
    private long sid;

    @DatabaseField(columnName = DbConstants.TableInfo.Base.CREATE_TIME)
    private long createTime;

    @DatabaseField(columnName = DbConstants.TableInfo.Base.CREATE_BY)
    private long createBy;

    @DatabaseField(columnName = DbConstants.TableInfo.Base.UPDATE_TIME)
    private long updateTime;

    @DatabaseField(columnName = DbConstants.TableInfo.Base.UPDATE_BY)
    private long updateBy;

    @DatabaseField(columnName = DbConstants.TableInfo.Base.VERSION)
    private int version;

    @DatabaseField(columnName = DbConstants.TableInfo.Base.IS_DELETED)
    private boolean isDeleted;

    @DatabaseField(columnName = DbConstants.TableInfo.Base.OWNER_ID)
    private long ownerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = TimeUtil.parseServerTime(createTime);
    }

    public long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(long createBy) {
        this.createBy = createBy;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(long updateBy) {
        this.updateBy = updateBy;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "BasePo{" +
                "id=" + id +
                ", sid=" + sid +
                ", createTime=" + TimeUtil.logTime(createTime) +
                ", createBy=" + createBy +
                '}';
    }
}
