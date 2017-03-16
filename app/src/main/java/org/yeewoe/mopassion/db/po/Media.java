package org.yeewoe.mopassion.db.po;

import com.google.gson.annotations.SerializedName;

import org.yeewoe.commonutils.common.assist.Base64;

/**
 * 媒体数据
 * Created by wyw on 2016/4/5.
 */
public class Media {
    /**
     * 媒体id
     */
    @SerializedName(value = "id")
    private long id;
    /**
     * 媒体名称
     */
    @SerializedName(value = "name")
    private String name;
    /**
     * 媒体类型(取值为PBMediaType)
     */
    @SerializedName(value = "type")
    private int type;
    /**
     * 媒体大小
     */
    @SerializedName(value = "size")
    private long size;
    /**
     * 媒体内容(可以是URL,文件哈希...)
     */
    @SerializedName(value = "content")
    private byte[] content;

    /**
     * (Base64编码)媒体内容(可以是URL,文件哈希...)
     */
    private String base64Content;
    /**
     * 用户数据，存放一些服务器不关心的数据
     */
    @SerializedName(value = "ext")
    private byte[] ext;

    /**
     * (Base64编码)用户数据，存放一些服务器不关心的数据
     */
    private String base64Ext;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public byte[] getContent() {
        if (content == null && base64Content != null) {
            content = Base64.decode(base64Content, Base64.DEFAULT);
        }
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
        this.base64Content = Base64.encodeToString(content, Base64.DEFAULT);
    }

    public byte[] getExt() {
        if (ext == null && base64Ext != null) {
            ext = Base64.decode(base64Ext, Base64.DEFAULT);
        }
        return ext;
    }

    public void setExt(byte[] ext) {
        this.ext = ext;
        this.base64Ext = Base64.encodeToString(ext, Base64.DEFAULT);
    }
}
