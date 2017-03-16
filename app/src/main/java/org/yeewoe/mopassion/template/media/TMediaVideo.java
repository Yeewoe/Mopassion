package org.yeewoe.mopassion.template.media;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 视频
 * Created by wyw on 2016/4/7.
 */
public class TMediaVideo implements Parcelable {
    public String fileKey;
    public int length;
    private String buffer;

    public TMediaVideo() {

    }

    protected TMediaVideo(Parcel in) {
        fileKey = in.readString();
        length = in.readInt();
        buffer = in.readString();
    }

    public static final Creator<TMediaVideo> CREATOR = new Creator<TMediaVideo>() {
        @Override
        public TMediaVideo createFromParcel(Parcel in) {
            return new TMediaVideo(in);
        }

        @Override
        public TMediaVideo[] newArray(int size) {
            return new TMediaVideo[size];
        }
    };

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileKey);
        dest.writeInt(length);
        dest.writeString(buffer);
    }
}
