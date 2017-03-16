package org.yeewoe.mopassion.app.common.view.widget;

import android.content.Context;
import android.util.AttributeSet;

import org.yeewoe.mopassion.R;

import in.srain.cube.views.loadmore.LoadMoreListViewContainer;

/**
 * Created by wyw on 2016/4/26.
 */
public class MopaLoadMoreListViewContainer extends LoadMoreListViewContainer {
    public MopaLoadMoreListViewContainer(Context context) {
        super(context);
        init();
    }

    public MopaLoadMoreListViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setBackgroundColor(getResources().getColor(R.color.common_listview_loadmore_bg));
        setShowLoadingForFirstPage(true);
        useDefaultHeader();
    }
}
