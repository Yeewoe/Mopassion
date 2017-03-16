package org.yeewoe.mopassion.app.common.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.yeewoe.commonutils.common.utils.PatternUtil;
import org.yeewoe.mopassion.utils.IntentManager;
import org.yeewoe.mopassion.utils.LinkUtil;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 富文本父类
 * Created by wyw on 2015/9/7.
 */
public class ComRichTextView extends TextView {
    /**
     * 长按事件使用这个接口
     */
    public abstract class ImChatOnLongClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {
            isOnLongClicked = true;
            return onImChatLongClick(v);
        }

        public abstract boolean onImChatLongClick(View v);
    }

    // 标记是否进入长按事件。用于在onTouchEvent取消span触发的短按事件
    public boolean isOnLongClicked = false;
    // 存放已匹配过的位置， 避免重复匹配
    private ArrayList<Offset> mMatchOffsetList = new ArrayList<>();

    public ComRichTextView(Context context) {
        super(context);

        // 避免长按事件发送到onTouch上
        setOnLongClickListener(new ImChatOnLongClickListener() {
            @Override
            public boolean onImChatLongClick(View v) {
                return true;
            }
        });
    }

    public ComRichTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnLongClickListener(new ImChatOnLongClickListener() {
            @Override
            public boolean onImChatLongClick(View v) {
                return true;
            }
        });
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        SpannableStringBuilder spanText = new SpannableStringBuilder(text);

        // 按优先级匹配
        addEmotion();
        boolean mailFlag = addEmailLink(spanText);
        boolean urlFlag = addURLLink(spanText);
        boolean linkFlag = addPhoneLink(spanText);
        if (mailFlag || urlFlag || linkFlag) {
            this.setFocusable(true);
            super.setLinksClickable(true);
            this.setMovementMethod(LineSpanMovementMethod.getInstance());
        } else {
            this.setFocusable(false);
            super.setLinksClickable(false);
        }
        try {
            super.setText(spanText, type);
        } catch (ArrayIndexOutOfBoundsException e) {
            setText(text.toString());
            e.printStackTrace();
        }

        mMatchOffsetList = null;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } catch (ArrayIndexOutOfBoundsException e) {
            setText(getText().toString());
            e.printStackTrace();
        }
    }

    @Override
    public void setGravity(int gravity) {
        try {
            super.setGravity(gravity);
        } catch (ArrayIndexOutOfBoundsException e) {
            setText(getText().toString());
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 使外层容器也响应onTouch事件
        ((ViewGroup) getParent()).onTouchEvent(event);
        // 若进入长按状态，取消span的短按事件处理。
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isOnLongClicked = false;
        }

        return super.onTouchEvent(event);
    }

    private void addEmotion() {
        // 匹配表情，待开发
    }

    private boolean addEmailLink(SpannableStringBuilder spanText) {
        boolean isValidMatcher = false;
        Pattern pattern = Pattern.compile(PatternUtil.REGEX_EMAIL,
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(spanText);
        while (matcher.find()) {
            if (isValidMatch(matcher)) {
                spanText.setSpan(new ImChatEmailSpan(matcher.group(), getSpanColor()),
                        matcher.start(), matcher.end(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mMatchOffsetList
                        .add(new Offset(matcher.start(), matcher.end()));
                isValidMatcher = true;
            }

        }
        return isValidMatcher;
    }

    private boolean addPhoneLink(SpannableStringBuilder spanText) {
        boolean isValidMatcher = false;
        Pattern pattern = Pattern.compile(PatternUtil.REGEX_PHONE,
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(spanText);
        while (matcher.find()) {
            if (isValidMatch(matcher)) {
                spanText.setSpan(new ImChatPhoneSpan(matcher.group(), getSpanColor()),
                        matcher.start(), matcher.end(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mMatchOffsetList
                        .add(new Offset(matcher.start(), matcher.end()));
                isValidMatcher = true;
            }

        }
        return isValidMatcher;
    }

    private boolean addURLLink(SpannableStringBuilder spanText) {

        boolean isValidMatcher = false;

        Pattern pattern = Pattern.compile(PatternUtil.REGEX_IP, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(spanText);
        while (matcher.find()) {
            if (isValidMatch(matcher)) {
                spanText.setSpan(new ImChatURLSpan(matcher.group(), getSpanColor()),
                        matcher.start(), matcher.end(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mMatchOffsetList
                        .add(new Offset(matcher.start(), matcher.end()));
                isValidMatcher = true;
            }

        }

        pattern = Pattern.compile(PatternUtil.REGEX_URL,
                Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(spanText);
        while (matcher.find()) {
            if (isValidMatch(matcher)) {
                spanText.setSpan(new ImChatURLSpan(matcher.group(), getSpanColor()),
                        matcher.start(), matcher.end(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mMatchOffsetList
                        .add(new Offset(matcher.start(), matcher.end()));
                isValidMatcher = true;
            }

        }

        return isValidMatcher;

    }

    public int spanColor;

    public int getSpanColor() {
        return Color.parseColor("#007AFF");
    }

    private boolean isValidMatch(Matcher matcher) {

        int start = matcher.start();

        if (mMatchOffsetList == null) {
            mMatchOffsetList = new ArrayList<>();
        }

        for (Offset each : mMatchOffsetList) {
            if (isInOffset(start, each)) {
                return false;
            }
        }

        return true;

    }

    private boolean isInOffset(int start, Offset each) {
        return start >= each.a && start < each.b;
    }

    class Offset {
        int a;
        int b;

        public Offset(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    protected class ImChatURLSpan extends SelectorClickSpan {

        private String mURL;
        private int color;

        public ImChatURLSpan(String url, int color) {
            super(color, 0, Color.parseColor("#1A000000"));
            this.mURL = url;
            this.color = color;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            if (color != 0) {
                ds.setColor(color);
            }
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            if (isOnLongClicked) {
                isOnLongClicked = false;
                return;
            }

            IntentManager.Web.intentToWebBrowser(getContext(), this.mURL);
        }

    }

    protected class ImChatPhoneSpan extends ClickableSpan {

        private String mPhone;
        private int color;

        public ImChatPhoneSpan(String phone, int color) {
            this.mPhone = phone;
            this.color = color;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(color);
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            if (isOnLongClicked) {
                isOnLongClicked = false;
                return;
            }
            LinkUtil.openPhoneDialog(getContext(), mPhone);
        }

    }

    protected class ImChatEmailSpan extends ClickableSpan {

        private String mEmail;
        private int color;

        public ImChatEmailSpan(String email, int color) {
            this.mEmail = email;
            this.color = color;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(color);
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            if (isOnLongClicked) {
                isOnLongClicked = false;
                return;
            }
            LinkUtil.openEmailDialog(getContext(), mEmail);
        }

    }

    class CtBabyContentJsonData {
        String title;
        CtBabyGiftJsonData[] Gifts;
    }

    class CtBabyGiftJsonData {
        String giftid;
        CtBabyInfoJsonData[] info;
    }

    class CtBabyInfoJsonData {
        String name;
        String link;
    }

}
