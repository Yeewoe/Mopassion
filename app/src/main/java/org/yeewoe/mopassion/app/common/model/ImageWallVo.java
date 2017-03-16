package org.yeewoe.mopassion.app.common.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.mopassion.db.po.BasePo;
import org.yeewoe.mopassion.db.po.Trend;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.template.media.TMediaImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acc on 2016/7/28.
 */
public class ImageWallVo extends BaseLineVo implements Parcelable {

    private TMediaImage image;

    protected ImageWallVo(Parcel in) {
        super(in);
        image = in.readParcelable(TMediaImage.class.getClassLoader());
    }

    public ImageWallVo(TMediaImage image) {
        super(new BasePo(), null, null);
        this.image = image;
    }

    public static class Convertor {
        /**
         * 将动态列表进行过滤转化
         */
        public static List<ImageWallVo> convert(List<Trend> trends, List<User> users) {
            List<ImageWallVo> result = new ArrayList<>();
            if (!Checks.check(trends)) {
                return result;
            }
            for (Trend trend : trends) {
                MediaLineVo<BasePo> imageWallVo = new MediaLineVo<BasePo>(trend, trend.getCreateBy(), users, trend.getContents());
                if (Checks.check(imageWallVo.images)) {
                    for (TMediaImage image : imageWallVo.images) {
                        result.add(new ImageWallVo(image));
                    }
                }
            }
            return result;
        }

        public static List<ImageWallVo> convert(ArrayList<TMediaImage> photoList) {
            List<ImageWallVo> result = new ArrayList<>();
            if (!Checks.check(photoList)) {
                return result;
            }
            for (int i = 0; i < photoList.size(); i++) {
                result.add(new ImageWallVo(photoList.get(photoList.size() - 1 - i)));
            }
            return result;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(image, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageWallVo> CREATOR = new Creator<ImageWallVo>() {
        @Override
        public ImageWallVo createFromParcel(Parcel in) {
            return new ImageWallVo(in);
        }

        @Override
        public ImageWallVo[] newArray(int size) {
            return new ImageWallVo[size];
        }
    };

    public TMediaImage getImage() {
        return image;
    }

    public void setImage(TMediaImage image) {
        this.image = image;
    }
}
