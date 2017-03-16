package org.yeewoe.mopassion.photo.fresco_lib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import org.yeewoe.mopassion.utils.IntentManager;

/**
 * 用于图片墙等
 * Created by wyw on 2016/4/24.
 */
public class WallPhotoSimpleDraweeView extends PhotoThumbSimpleDraweeView  {

    public WallPhotoSimpleDraweeView(Context context) {
        super(context);
    }

    public WallPhotoSimpleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WallPhotoSimpleDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init(Context context) {
        setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                IntentManager.Contact.intentToDetail(getContext(), userHeadVo);
            }
        });
    }
}
