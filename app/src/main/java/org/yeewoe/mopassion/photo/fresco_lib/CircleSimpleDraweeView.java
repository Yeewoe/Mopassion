package org.yeewoe.mopassion.photo.fresco_lib;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;

/**
 * 圆形
 * Created by wyw on 2016/5/4.
 */
public class CircleSimpleDraweeView extends MopaSimpleDraweeView {
    public CircleSimpleDraweeView(Context context) {
        super(context);
        init(context);
    }

    public CircleSimpleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CircleSimpleDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public CircleSimpleDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        init(context);
    }

    private void init(Context context) {
        setCircleHierarchy();
    }
}
