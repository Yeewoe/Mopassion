package org.yeewoe.mopassion.photo.fresco_lib;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.file.service.FileService;
import org.yeewoe.mopassion.template.media.TMediaImage;

/**
 * 图片缩略图,用于显示普通图片
 * Created by wyw on 2016/4/21.
 */
public class PhotoThumbSimpleDraweeView extends HeadThumbSimpleDraweeView {

    public PhotoThumbSimpleDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        init(context);
    }

    public PhotoThumbSimpleDraweeView(Context context) {
        super(context);
        init(context);
    }

    public PhotoThumbSimpleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PhotoThumbSimpleDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @Override
    protected void init(Context context) {
//        setProgressBar(new LoadingProgressDrawable(context));
//        setPlaceholderDrawable(context.getResources().getDrawable(R.drawable.default_img));
    }


}
