package org.yeewoe.mopassion.app.common.view.widget;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by wyw on 2016/4/26.
 */
public class MopaPtrFrameLayout extends PtrClassicFrameLayout {
    public MopaPtrFrameLayout(Context context) {
        super(context);
        init();
    }

    private void init() {
        setPullToRefresh(false);
        setKeepHeaderWhenRefresh(true);
        setDurationToClose(200);
        setDurationToCloseHeader(500);
        setRatioOfHeaderHeightToRefresh(1.2f);
        setResistance(1.7f);
        setLoadingMinTime(500);
    }

    public MopaPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MopaPtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
}
