package org.yeewoe.mopassion.template.media;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.commonutils.common.utils.JsonUtil;
import org.yeewoe.commonutils.common.utils.MD5Util;
import org.yeewoe.commonutils.common.utils.StringUtil;
import org.yeewoe.mopassion.constants.TemplateConstants;
import org.yeewoe.mopassion.db.po.Media;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okio.ByteString;

/**
 * Media模板工厂
 * Created by wyw on 2016/4/7.
 */
public class TMediaFactory {

    private static TMediaFactory instance;

    private TMediaFactory() {
    }

    public static TMediaFactory getInstance() {
        if (instance == null) {
            synchronized (TMediaFactory.class) {
                if (instance == null) {
                    instance = new TMediaFactory();
                }
            }
        }
        return instance;
    }

    public Object toT(@NonNull Media media) {
        switch (media.getType()) {
            case TemplateConstants.Media.TYPE_TEXT:
                if (media.getContent() != null) {
                    return JsonUtil.fromJson(StringUtil.decode(media.getContent()), String.class);
                } else {
                    return "";
                }
            case TemplateConstants.Media.TYPE_VOICE:
                if (media.getContent() != null) {
                    return JsonUtil.fromJson(new String(media.getContent()), TMediaVoice.class);
                } else {
                    return new TMediaVoice();
                }
            case TemplateConstants.Media.TYPE_IMAGE:
                if (media.getContent() != null) {
                    return JsonUtil.fromJson(new String(media.getContent()), TMediaImage.class);
                } else {
                    return new TMediaImage();
                }
            case TemplateConstants.Media.TYPE_VIDEO:
                if (media.getContent() != null) {
                    return JsonUtil.fromJson(new String(media.getContent()), TMediaVideo.class);
                } else {
                    return new TMediaVideo();
                }

            default:
                break;
        }

        return null;
    }

    public Object toT(List<Media> contents, int type) {
        if (!Checks.check(contents)) {
            return null;
        }
        for (Media content : contents) {
            if (content.getType() == type) {
                return toT(content);
            }
        }
        return null;
    }

    public Object toT(String json) {
        Media media = fromJson(json);
        if (media != null) {
            return toT(media);
        }
        return null;
    }

    public TMediaImage toTMediaImage(@NonNull File file) {
        TMediaImage result = new TMediaImage();
        result.fileKey = MD5Util.md5Str(file);
        return result;
    }

    public List<TMediaImage> toTMediaImage(@NonNull List<Media> medias) {
        List<TMediaImage> result = new ArrayList<>();
        if (Checks.check(medias)) {
            for (Media media : medias) {
                if (media != null) {
                    Object o = toT(media);
                    if (o instanceof TMediaImage) {
                        result.add((TMediaImage) o);
                    }
                }
            }
        }
        return result;
    }

    public ByteString toByteString(@NonNull Media media) {
        try {
            return ByteString.of(JsonUtil.toJson(media).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ByteString toByteString(@NonNull List<Media> medias) {
        try {
            return ByteString.of(JsonUtil.toJson(medias).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Media fromJson(String json) {
        if (!Checks.check(json)) {
            return null;
        }

        try {
            return JsonUtil.fromJson(json, Media.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Media> fromJsonToList(String json) {
        if (!Checks.check(json)) {
            return new ArrayList<>();
        }

        try {
            return new Gson().fromJson(json, new TypeToken<List<Media>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Media> fromByteStringToList(ByteString byteString) {
        if (byteString == null) {
            return new ArrayList<>();
        }
        try {
            return TMediaFactory.getInstance().fromJsonToList(new String(byteString.toByteArray(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Media fromT(String name, long size, @NonNull Object t) {
        Media media = new Media();
        try {
            media.setContent(JsonUtil.toJson(t).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        media.setName(name);
        media.setSize(size);
        if (t instanceof String) {
            media.setType(TemplateConstants.Media.TYPE_TEXT);
        } else if (t instanceof TMediaVoice) {
            media.setType(TemplateConstants.Media.TYPE_VOICE);
        } else if (t instanceof TMediaImage) {
            media.setType(TemplateConstants.Media.TYPE_IMAGE);
        } else if (t instanceof TMediaVideo) {
            media.setType(TemplateConstants.Media.TYPE_VIDEO);
        } else {
            return null;
        }

        return media;
    }

    public String fromTToJson(String name, long size, @NonNull Object t) {
        Media media = fromT(name, size, t);
        if (media == null) {
            return "";
        }

        return JsonUtil.toJson(media);
    }

    public ByteString fromTToByteString(String name, long size, @NonNull Object t) {
        Media media = fromT(name, size, t);
        if (media == null) {
            return ByteString.EMPTY;
        }

        try {
            return ByteString.of(JsonUtil.toJson(media).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Media> fromTs(String name, long size, Object... t) {
        if (t == null) {
            return new ArrayList<>();
        }
        List<Media> result = new ArrayList<>();
        for (Object o : t) {
            result.add(fromT(name, size, o));
        }
        return result;
    }

    public List<Media> fromTList(String name, long size, List t) {
        if (t == null) {
            return new ArrayList<>();
        }
        List<Media> result = new ArrayList<>();
        for (Object o : t) {
            result.add(fromT(name, size, o));
        }
        return result;
    }

}
