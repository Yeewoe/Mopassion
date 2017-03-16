package org.yeewoe.mopassion.app.common.view.widget;

import android.text.TextPaint;
import android.text.style.ClickableSpan;

/**
 * Created by acc on 2015/4/16.
 */
public abstract class SelectorClickSpan extends ClickableSpan {

    public static final String TAG = "SelectorClickSpan";

    private boolean mIsPressed;
    private int mPressedBackgroundColor;
    private int mNormalTextColor;
    private int mPressedTextColor;
    private String content;

    public SelectorClickSpan(int normalTextColor, int pressedTextColor, int pressedBackgroundColor) {
        mNormalTextColor = normalTextColor;
        mPressedTextColor = pressedTextColor;
        mPressedBackgroundColor = pressedBackgroundColor;
    }

    public SelectorClickSpan(int normalTextColor, int pressedTextColor, int pressedBackgroundColor, String content) {
        mNormalTextColor = normalTextColor;
        mPressedTextColor = pressedTextColor;
        mPressedBackgroundColor = pressedBackgroundColor;
    }

    public void setPressed(boolean isSelected) {
        mIsPressed = isSelected;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(mIsPressed ? (mPressedTextColor==0?mNormalTextColor:mPressedTextColor) : mNormalTextColor);
        ds.bgColor = mIsPressed ? mPressedBackgroundColor : 0x00ffffff;
        ds.setUnderlineText(false);
    }

}
