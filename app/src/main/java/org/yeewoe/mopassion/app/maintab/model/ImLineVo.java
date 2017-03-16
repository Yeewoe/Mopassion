package org.yeewoe.mopassion.app.maintab.model;

import android.support.annotation.NonNull;

import org.yeewoe.mopassion.app.common.model.MediaLineVo;
import org.yeewoe.mopassion.db.po.Media;
import org.yeewoe.mopassion.db.po.Message;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.template.media.TMediaFactory;
import org.yeewoe.mopassion.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyw on 2016/4/3.
 */
public class ImLineVo extends MediaLineVo<Message> {

    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ImLineVo(Message message, Long uid, List<User> users, List<Media> medias) {
        super(message, uid, users, medias);
        convertListMsg();
    }

    public static class Converter {
        public static List<ImLineVo> convert(List<Message> messages, List<User> users) {
            if (messages == null) {
                return new ArrayList<>();
            }

            List<ImLineVo> result = new ArrayList<>();
            for (Message message : messages) {
                if (message != null) {
                    result.add(convert(message, users));
                }
            }

            return result;
        }

        private static ImLineVo convert(@NonNull Message message, List<User> users) {
            Media media = TMediaFactory.getInstance().fromJson(message.getContent());
            List<Media> medias = new ArrayList<>();
            if (media != null) {
                medias.add(media);
            }
            ImLineVo imLineVo = new ImLineVo(message, message.getOtherUid(), users, medias);
            imLineVo.setTime(TimeUtil.parseItemListTime(message.getCreateTime(), true));
            return imLineVo;
        }


    }
}
