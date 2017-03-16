package org.yeewoe.mopassion.photo.fresco_lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import org.yeewoe.mopassion.R;


public class LoadingProgressDrawable extends Drawable {

    private static final String TAG = "LoadingProgressDrawable";
    private static int[] Loadings = {
            R.mipmap.load_progress_1,
            R.mipmap.load_progress_3,
            R.mipmap.load_progress_4,
            R.mipmap.load_progress_6,
            R.mipmap.load_progress_7,
            R.mipmap.load_progress_8,
            R.mipmap.load_progress_9,
            R.mipmap.load_progress_10,
            R.mipmap.load_progress_11,
            R.mipmap.load_progress_12
    };
    private Paint mPaint;
    private int mLevel;
    private Context context;

    public LoadingProgressDrawable(Context context) {
        this.context = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    }

    BitmapFactory.Options options = new BitmapFactory.Options();

    @Override
    public void draw(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), Loadings[getIndex()], options);

        int left = getBounds().right / 2 - options.outWidth / 2;
        int top = getBounds().bottom / 2 - options.outHeight / 2;

        canvas.drawBitmap(bitmap, left, top, mPaint);
    }

    private int getIndex() {
        int index = mLevel / 1000;
        if (index < 0) {
            index = 0;
        } else if (index >= Loadings.length) {
            index = Loadings.length - 1;
        }
        return index;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return Color.TRANSPARENT;
    }

    @Override
    protected boolean onLevelChange(int level) {
        this.mLevel = level;
        this.invalidateSelf();
        return true;
    }
}
