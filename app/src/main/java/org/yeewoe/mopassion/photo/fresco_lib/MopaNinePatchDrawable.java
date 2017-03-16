package org.yeewoe.mopassion.photo.fresco_lib;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.text.TextUtils;

import org.yeewoe.mopassion.R;

public class MopaNinePatchDrawable extends NinePatchDrawable {
    private String mStr;
    private Paint mPaint;

    public MopaNinePatchDrawable(Resources res, Bitmap bitmap, byte[] chunk, Rect padding, String srcName, String str) {
        super(res, bitmap, chunk, padding, srcName);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(res.getDimensionPixelSize(R.dimen.txt_size_16));
        mStr = str;
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (TextUtils.isEmpty(mStr)) {
            return;
        }
        float textHeight = mPaint.descent() - mPaint.ascent();
        float textWidth = mPaint.measureText(mStr);

        float offsetX = textWidth / 2;
        float offsetY = textHeight / 2;

        float disY = getIntrinsicHeight() / 2 - offsetY;
        float disX = getIntrinsicWidth() / 2 - offsetX;

        canvas.drawText(mStr, this.getBounds().right - getIntrinsicWidth() + disX, this.getBounds().bottom - getIntrinsicHeight() + textHeight, mPaint);
    }
}
