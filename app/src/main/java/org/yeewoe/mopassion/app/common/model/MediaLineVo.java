package org.yeewoe.mopassion.app.common.model;

/**
 * Created by wyw on 2016/4/24.
 */

import android.os.Parcel;
import android.os.Parcelable;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.mopassion.db.po.BasePo;
import org.yeewoe.mopassion.db.po.Media;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.emotion.EmotionUtil;
import org.yeewoe.mopassion.template.media.TMediaFactory;
import org.yeewoe.mopassion.template.media.TMediaImage;
import org.yeewoe.mopassion.template.media.TMediaVideo;
import org.yeewoe.mopassion.template.media.TMediaVoice;

import java.util.ArrayList;
import java.util.List;

public class MediaLineVo<T extends BasePo> extends BaseLineVo<T> implements Parcelable {

    protected String msg;
    protected String msg2;
    protected List<TMediaImage> images = new ArrayList<>();
    protected TMediaVoice voice;
    protected TMediaVideo video;
    private boolean hasMsg;
    private boolean hasMsg2;

    public MediaLineVo(T source, Long uid, List<User> users, List<Media> medias) {
        super(source, uid, users);
        if (Checks.check(medias)) {
            for (Media media : medias) {
                if (media != null) {
                    Object t = TMediaFactory.getInstance().toT(media);
                    if (t instanceof String) {
                        if (!hasMsg) {
                            msg = (String) t;
                            hasMsg = true;
                        } else if (!hasMsg2) {
                            msg2 = (String) t;
                            hasMsg2 = true;
                        }
                    } else if (t instanceof TMediaImage) {
                        images.add((TMediaImage) t);
                    } else if (t instanceof TMediaVideo) {
                        video = (TMediaVideo) t;
                    } else if (t instanceof TMediaVoice) {
                        voice = (TMediaVoice) t;
                    }
                }
            }
        }
    }

    protected MediaLineVo(Parcel in) {
        super(in);
        msg = in.readString();
        msg2 = in.readString();
        images = in.createTypedArrayList(TMediaImage.CREATOR);
    }

    protected void convertListMsg() {
        if (Checks.check(images)) {
            setMsg("[图片]");
        } else if (video != null) {
            setMsg("[视频]");
        } else if (voice != null) {
            setMsg("[语音]");
        }
        setMsg(EmotionUtil.parseInfo(getMsg()));
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(msg);
        dest.writeString(msg2);
        dest.writeTypedList(images);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MediaLineVo> CREATOR = new Creator<MediaLineVo>() {
        @Override
        public MediaLineVo createFromParcel(Parcel in) {
            return new MediaLineVo(in);
        }

        @Override
        public MediaLineVo[] newArray(int size) {
            return new MediaLineVo[size];
        }
    };

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg2() {
        return msg2;
    }

    public void setMsg2(String msg2) {
        this.msg2 = msg2;
    }

    public List<TMediaImage> getImages() {
        return images;
    }

    public void setImages(List<TMediaImage> images) {
        this.images = images;
    }

    public TMediaVideo getVideo() {
        return video;
    }

    public void setVideo(TMediaVideo video) {
        this.video = video;
    }

    public TMediaVoice getVoice() {
        return voice;
    }

    public void setVoice(TMediaVoice voice) {
        this.voice = voice;
    }
}
