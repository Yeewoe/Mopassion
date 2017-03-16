package org.yeewoe.mopassion.app.common.view.widget;

import android.content.Context;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by acc on 2015/4/16.
 */
public class LineSpanMovementMethod extends LinkMovementMethod {

    private static LineSpanMovementMethod sInstance;
    private Context context;
    private GestureDetector gestureDetector;

    public static final String TAG = "LineSpanMovementMethod";

    public static MovementMethod getInstance() {
        if (sInstance == null)
            sInstance = new LineSpanMovementMethod();
        return sInstance;
    }

    public LineSpanMovementMethod() {
    }

    private SelectorClickSpan mPressedSpan;

    @Override
    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent event) {
        try {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                SelectorClickSpan mPressedSpan = getPressedSpan(textView, spannable, event);
                if (mPressedSpan != null) {
                    mPressedSpan.setPressed(true);
                    Selection.setSelection(spannable, spannable.getSpanStart(mPressedSpan),
                            spannable.getSpanEnd(mPressedSpan));
                }
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                SelectorClickSpan touchedSpan = getPressedSpan(textView, spannable, event);
                if (mPressedSpan != null && touchedSpan != mPressedSpan) {
                    mPressedSpan.setPressed(false);
                    mPressedSpan = null;
                    Selection.removeSelection(spannable);
                }
            } else {
                SelectorClickSpan touchedSpan = getPressedSpan(textView, spannable, event);
                if(touchedSpan!=null) {
                    touchedSpan.setPressed(false);
                }
                if (mPressedSpan != null) {
                    mPressedSpan.setPressed(false);
                    super.onTouchEvent(textView, spannable, event);
                    return true;
                }
                mPressedSpan = null;
                Selection.removeSelection(spannable);
            }
            if(mPressedSpan!=null) {
                return true;
            }
        } catch(Exception e) {

        } catch (Error e) {

        }
        return super.onTouchEvent(textView,spannable,event);
    }

    private SelectorClickSpan getPressedSpan(TextView textView, Spannable spannable, MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        x -= textView.getTotalPaddingLeft();
        y -= textView.getTotalPaddingTop();

        x += textView.getScrollX();
        y += textView.getScrollY();

        Layout layout = textView.getLayout();
        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);

        SelectorClickSpan[] link = spannable.getSpans(off, off, SelectorClickSpan.class);
        SelectorClickSpan touchedSpan = null;
        if (link.length > 0) {
            touchedSpan = link[0];
        }
        return touchedSpan;
    }




}
