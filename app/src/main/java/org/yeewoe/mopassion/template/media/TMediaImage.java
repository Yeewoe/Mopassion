package org.yeewoe.mopassion.template.media;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 图片
 * Created by acc on 2016/4/7.
 */
public class TMediaImage implements Parcelable, Serializable {
    public String fileKey;
    public int height;
    public int width;

    public TMediaImage() {

    }

    protected TMediaImage(Parcel in) {
        fileKey = in.readString();
        height = in.readInt();
        width = in.readInt();
    }

    public static final Creator<TMediaImage> CREATOR = new Creator<TMediaImage>() {
        @Override
        public TMediaImage createFromParcel(Parcel in) {
            return new TMediaImage(in);
        }

        @Override
        public TMediaImage[] newArray(int size) {
            return new TMediaImage[size];
        }
    };

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileKey);
        dest.writeInt(height);
        dest.writeInt(width);
    }
}
