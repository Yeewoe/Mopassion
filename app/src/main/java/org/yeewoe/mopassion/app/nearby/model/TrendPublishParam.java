package org.yeewoe.mopassion.app.nearby.model;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.mopassion.app.common.model.BaseParam;
import org.yeewoe.mopassion.db.po.MapPosition;
import org.yeewoe.mopassion.db.po.Media;
import org.yeewoe.mopassion.db.po.Trend;
import org.yeewoe.mopassion.template.media.TMediaFactory;
import org.yeewoe.mopassion.template.media.TMediaImage;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 表单参数
 * Created by wyw on 2016/4/8.
 */
public class TrendPublishParam extends BaseParam<Trend> implements Serializable {

    public String title;
    public String content;
    public MapPosition mapPosition;
    public List<TMediaImage> mediaImageList;
    public List<File> fileList;

    public TrendPublishParam(String title, String content, MapPosition mapPosition, List<TMediaImage> mediaImageList, List<File> fileList) {
        this.title = title;
        this.content = content;
        this.mapPosition = mapPosition;
        this.mediaImageList = mediaImageList;
        this.fileList = fileList;
    }

    @Override public String toString() {
        return "TrendPublishParam{" +
                "title='" + title + '\'' +
                ", content=" + content +
                ", mapPosition=" + mapPosition +
                ", mediaImageList=" + mediaImageList +
                ", fileList=" + fileList +
                "} " + super.toString();
    }

    @Override public Trend toPo() {
        Trend result = new Trend();
        List<Media> medias = new ArrayList<>();
        medias.add(TMediaFactory.getInstance().fromT(null, 0, content)); // content放第一个
        medias.add(TMediaFactory.getInstance().fromT(null, 0, title)); // title 要放第二个，顺序不能改  !!! 这个和解析时候的顺序是一致的
        if (Checks.check(mediaImageList)) {
            for (TMediaImage mediaImage : mediaImageList) {
                medias.add(TMediaFactory.getInstance().fromT(null, 0, mediaImage));
            }
        }
        result.setContents(medias);
        result.setPosition(mapPosition);
        initPo(result);
        return result;
    }
}
