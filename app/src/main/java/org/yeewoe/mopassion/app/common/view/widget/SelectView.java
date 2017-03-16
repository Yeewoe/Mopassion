package org.yeewoe.mopassion.app.common.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by cjz on 2015/9/8.
 */
public class SelectView extends ImageView implements View.OnClickListener{
    private boolean initExecuted;

    private OnClickListener outerOnClick;
    private OnSelectChangeListener onSelectChangeListener;
    public SelectView(Context context) {
        super(context);
        if(!initExecuted){
            initExecuted = true;
            init();
        }
    }

    public SelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!initExecuted){
            initExecuted = true;
            init();
        }
    }

    public SelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(!initExecuted){
            initExecuted = true;
            init();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SelectView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if(!initExecuted){
            initExecuted = true;
            init();
        }
    }

    protected void init(){
        super.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        setSelected(!isSelected());
        if(outerOnClick != null){
            outerOnClick.onClick(v);
        }
        if(onSelectChangeListener != null){
            onSelectChangeListener.onSelectChanged(this, isSelected());
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        outerOnClick = l;
    }

    public void setOnSelectChangeListener(OnSelectChangeListener l){
        onSelectChangeListener = l;
    }

    public interface OnSelectChangeListener {
        void onSelectChanged(SelectView selectView, boolean selected);
    }
}
