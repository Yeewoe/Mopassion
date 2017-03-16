/*
 * Copyright (C) 2015-2016 Jacksgong(blog.dreamtobe.cn)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.yeewoe.mopassion.app.common.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.yeewoe.mopassion.utils.KeyboardUtil;


/**
 * 类描述：能自适应键盘高度的ViewGroup类需要配合KeyBroadListenerFrameLayout一起使用。
 * @see KeyBroadListenerFrameLayout
 * @author created by tuzhenyu
 */
public class PanelLayout extends FrameLayout {

    private boolean mIsHide = false;
    private boolean mIsShow = true;

    public PanelLayout(Context context) {
        super(context);
        init(context, null);
    }

    public PanelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public PanelLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context c, AttributeSet attrs) {

        refreshHeight();
    }

    public void refreshHeight() {

        if (isInEditMode()) {
            return;
        }

        if (getHeight() == KeyboardUtil.getValidPanelHeight(getContext())) {
            return;
        }

        post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, KeyboardUtil.getValidPanelHeight(getContext()));
                } else {
                    layoutParams.height = KeyboardUtil.getValidPanelHeight(getContext());
                }

                setLayoutParams(layoutParams);
            }
        });
    }


    /**
     * @param visibility {@link View#VISIBLE}: 这里有两种情况，1. 键盘没有弹起(需要适配)、2. 键盘没有弹起（不用适配）
     */
    @Override
    public void setVisibility(int visibility) {

        if (visibility == VISIBLE) {
            this.mIsHide = false;
        }

        if (visibility == getVisibility()) {
            return;
        }


        if (mIsKeyboardShowing && visibility == VISIBLE) {
            return;
        }

        super.setVisibility(visibility);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (mIsHide) {
            setVisibility(View.GONE);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY);
        }

        //由于表情键盘需要动态适应输入法高度变化
        //为了美观，当高度太高的时候，要把表情键盘“向下压”。以至于表情图标不会太分散
//        int padding = 0;
//        int height =MeasureSpec.getSize(heightMeasureSpec);
//
//        int normalHeight = getResources().getDimensionPixelOffset(R.dimen.im_chat_express_panel_normal_height);
//        int gresatHeight = getResources().getDimensionPixelOffset(R.dimen.im_chat_express_panel_great_height);

//        if(height >= normalHeight && height <= gresatHeight ){
//            padding = height / 7;
//        }else if(height > gresatHeight) {
//            padding = height / 6;
//        }

//        this.setPadding(0,padding,0,0);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 这里只是一个状态，是在{@link #onMeasure}之前{@link KeyBroadListenerFrameLayout#onLayout(boolean, int, int, int, int)}中获知
     */
    private boolean mIsKeyboardShowing = false;

    public void setIsKeyboardShowing(final boolean isKeyboardShowing) {
        this.mIsKeyboardShowing = isKeyboardShowing;
    }


    public void setIsShow(final boolean isShow) {
        this.mIsShow = isShow;
        if (mIsShow) {
            super.setVisibility(View.VISIBLE);
        }
    }

    public void setIsHide(final boolean isHide) {
        this.mIsHide = isHide;

    }

//    public boolean isAdjust() {
//        return isAdjust;
//    }
//
//    public void setIsAdjust(boolean isAdjust) {
//        this.isAdjust = isAdjust;
//    }
//
//    public boolean ismIsShow() {
//        return mIsShow;
//    }
//
//    public boolean ismIsKeyboardShowing() {
//        return mIsKeyboardShowing;
//    }
//
//    public boolean ismIsHide() {
//        return mIsHide;
//    }

}
