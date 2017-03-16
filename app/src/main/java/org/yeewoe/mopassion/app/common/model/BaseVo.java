package org.yeewoe.mopassion.app.common.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.yeewoe.mopassion.db.po.BasePo;

/**
 * 基类VO
 * Created by wyw on 2016/4/4.
 */
public class BaseVo<T extends BasePo> implements Parcelable {
    protected int id;
    protected long sid;
    protected T source;

    protected BaseVo(Parcel in) {
        id = in.readInt();
        sid = in.readLong();
    }

    public static final Creator<BaseVo> CREATOR = new Creator<BaseVo>() {
        @Override
        public BaseVo createFromParcel(Parcel in) {
            return new BaseVo(in);
        }

        @Override
        public BaseVo[] newArray(int size) {
            return new BaseVo[size];
        }
    };

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

    public T getSource() {
        return source;
    }

    public void setSource(T source) {
        this.source = source;
    }

    public BaseVo(T source) {
        if (source != null) {
            this.source = source;
            this.id = source.getId();
            this.sid = source.getSid();
        }
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(sid);
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseVo<?> baseVo = (BaseVo<?>) o;

        return sid == baseVo.sid;

    }

    @Override public int hashCode() {
        return (int) (sid ^ (sid >>> 32));
    }
}
