package org.yeewoe.mopassion.app.common.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import org.yeewoe.mopassion.emotion.EmotionUtil;

public class ImChatTextView extends ComRichTextView {

    private boolean isSelfData = false;

    public ImChatTextView(Context context) {
        super(context);
    }

    public ImChatTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setImText(CharSequence text) {
        if (text == null) {
            text = "";
        }
        super.setText(EmotionUtil.parseToMsg(text.toString(), getContext(), false),
                BufferType.SPANNABLE);
    }

    public void setSelfData(boolean isSelfData) {
        this.isSelfData = isSelfData;
    }

    public int getSpanColor() {
        if (isSelfData) {
            return Color.parseColor("#0000FF");
        } else {
            return Color.parseColor("#007AFF");
        }
    }
}
