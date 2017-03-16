package org.yeewoe.mopassion.app.im.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.mopassion.app.common.model.MediaLineVo;
import org.yeewoe.mopassion.app.im.builder.MessageType;
import org.yeewoe.mopassion.db.po.Media;
import org.yeewoe.mopassion.db.po.Message;
import org.yeewoe.mopassion.db.po.SendStatus;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.mangers.UserDataMananger;
import org.yeewoe.mopassion.template.media.TMediaFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息显示使用的VO
 * Created by wyw on 2016/3/30.
 */
public class MessageVo extends MediaLineVo<Message> implements Parcelable {
    private MessageType type;
    private boolean isSelf;
    private SendStatus sendStatus;

    public MessageVo(Message source, Long uid, List<User> users, List<Media> medias) {
        super(source, uid, users, medias);
    }

    protected MessageVo(Parcel in) {
        super(in);
        isSelf = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (isSelf ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MessageVo> CREATOR = new Creator<MessageVo>() {
        @Override
        public MessageVo createFromParcel(Parcel in) {
            return new MessageVo(in);
        }

        @Override
        public MessageVo[] newArray(int size) {
            return new MessageVo[size];
        }
    };

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }

    public SendStatus getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(SendStatus sendStatus) {
        this.sendStatus = sendStatus;
    }

    public static class Convert {
        public static MessageVo convert(@NonNull Message message) {
            return convert(message, UserDataMananger.getInstance().getMeList());
        }

        public static MessageVo convert(@NonNull Message message, List<User> users) {
            Media media = TMediaFactory.getInstance().fromJson(message.getContent());
            List<Media> medias = new ArrayList<>();
            if (media != null) {
                medias.add(media);
            }
            MessageVo messageVo = new MessageVo(message, message.getCreateBy(), users, medias);

            // 设置类型
            if (Checks.check(messageVo.images)) {
                messageVo.setType(MessageType.PICTURE);
            } else if (messageVo.video != null) {
                messageVo.setType(MessageType.VIDEO);
            } else if (messageVo.voice != null) {
                messageVo.setType(MessageType.VOICE);
            } else {
                messageVo.setType(MessageType.TXT);
            }

            // 设置发送方
            if (message.getFromUid() == UserDataMananger.getInstance().getMeUid()) {
                messageVo.setSelf(true);
            } else {
                messageVo.setSelf(false);
            }

            // 设置发送状态
            messageVo.sendStatus = message.getSendStatus();
            return messageVo;
        }

        @NonNull public static List<MessageVo> convert(List<Message> messages, List<User> users) {
            if (messages == null) {
                return new ArrayList<>();
            }
            List<MessageVo> result = new ArrayList<>();
            for (Message message : messages) {
                result.add(convert(message, users));
            }

            return result;
        }

    }
}
