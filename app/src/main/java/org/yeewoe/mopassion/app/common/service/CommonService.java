package org.yeewoe.mopassion.app.common.service;

import android.support.annotation.NonNull;

import org.yeewoe.mopanet.protos.PBMapPostion;
import org.yeewoe.mopanet.protos.PBMedia;
import org.yeewoe.mopassion.db.po.MapPosition;
import org.yeewoe.mopassion.db.po.Media;

import java.util.ArrayList;
import java.util.List;

import okio.ByteString;

/**
 * 公共接口
 * Created by wyw on 2016/4/8.
 */
public class CommonService {
    @NonNull public List<Media> pbToMedia(List<PBMedia> pbMedias) {
        if (pbMedias == null) {
            return new ArrayList<>();
        }
        List<Media> result = new ArrayList<>();
        for (PBMedia pbMedia : pbMedias) {
            result.add(pbToMedia(pbMedia));
        }

        return result;
    }

    @NonNull public Media pbToMedia(@NonNull PBMedia pbMedia) {
        Media result = new Media();
        if (pbMedia.id != null) {
            result.setId(pbMedia.id);
        }
        result.setName(pbMedia.name);
        if (pbMedia.type != null) {
            result.setType(pbMedia.type);
        }
        if (pbMedia.size != null) {
            result.setSize(pbMedia.size);
        }
        if (pbMedia.content != null) {
            result.setContent(pbMedia.content.toByteArray());
        }
        if (pbMedia.ext != null) {
            result.setExt(pbMedia.ext.toByteArray());
        }
        return result;
    }

    public List<PBMedia> mediaToPb(List<Media> medias) {
        if (medias == null) {
            return new ArrayList<>();
        }
        List<PBMedia> result = new ArrayList<>();
        for (Media media : medias) {
            if (media != null) {
                result.add(mediaToPb(media));
            }
        }
        return result;
    }

    public PBMedia mediaToPb(Media media) {
        if (media == null) {
            return null;
        }
        PBMedia.Builder builder = new PBMedia.Builder();
        if (media.getId() > 0) {
            builder.id(media.getId());
        }
        builder.name(media.getName()).size(media.getSize()).type(media.getType()).content(ByteString.of(media.getContent()));
        if (media.getExt() != null) {
            builder.ext(ByteString.of(media.getExt()));
        }
        return builder.build();
    }

    @NonNull public List<MapPosition> pbToMapPosition(List<PBMapPostion> pbMapPostions) {
        if (pbMapPostions == null) {
            return new ArrayList<>();
        }

        List<MapPosition> result = new ArrayList<>();
        for (PBMapPostion pbMapPostion : pbMapPostions) {
            if (pbMapPostion != null) {
                result.add(pbToMapPosition(pbMapPostion));
            }
        }
        return result;
    }

    @NonNull public MapPosition pbToMapPosition(@NonNull PBMapPostion pbMapPostion) {
        return new MapPosition(pbMapPostion.addr, pbMapPostion.lon, pbMapPostion.lat);
    }


    public PBMapPostion mapPositionToPb(MapPosition position) {
        if (position == null) {
            return null;
        }
        /** 注意，这里address的格式不能随便调动 **/
        return new PBMapPostion.Builder().addr(position.toFormatedAddress()).lon(position.getLon()).lat(position.getLat()).build();
    }
}
