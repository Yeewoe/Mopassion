package org.yeewoe.mopassion.app.common.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import org.yeewoe.mopassion.R;

public class CircleImageView extends ImageView {

    private Paint mPaint;
    private int padding;
    private int angle;

    public CircleImageView(Context context) {
        super(context);
        init();
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect r = new Rect();
        getLocalVisibleRect(r);
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.voice_volume_color));
        canvas.drawColor(Color.TRANSPARENT);
        mPaint.setStrokeWidth(getResources().getDimensionPixelOffset(R.dimen.im_chat_circle_stroke_width));
        mPaint.setStyle(Style.STROKE);
        RectF oval = new RectF();
        oval.left = r.left + padding;
        oval.right = r.right - padding;
        oval.top = r.top + padding;
        oval.bottom = r.bottom - padding;
        canvas.drawArc(oval, 270, angle, false, mPaint);
        canvas.drawArc(oval, 270, -angle, false, mPaint);

    }

    public void setArcPadding(int length) {
        padding = length;
    }

    public void setAngle(int angle) {
//		if(angle >180) {
//			throw new IllegalStateException("the angle must <= 180!");
//		}
        this.angle = angle;
        invalidate();
    }

}
