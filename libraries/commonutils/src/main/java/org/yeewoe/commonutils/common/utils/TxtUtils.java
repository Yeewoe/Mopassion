package org.yeewoe.commonutils.common.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextPaint;
import android.widget.TextView;

/**
 * Created by wyw on 2015/7/6.
 */
public class TxtUtils {
    public static int getTextLine(TextView tv, int width){
        int line = 0;
        TextPaint paint = tv.getPaint();
        int paddingLeft = tv.getPaddingLeft();
        int paddingRight = tv.getPaddingRight();
        width -= (paddingLeft + paddingRight);
        CharSequence text = tv.getText();
        String s = text.toString();
        int length = s.length();

        int endl = -1;

        int start = 0;
        int breakLen;

        boolean noMoreEndl = false;
        int end;
        while(start < length){
            if(!noMoreEndl) {
                endl = s.indexOf('\n', start);
                if (endl == -1) {
                    noMoreEndl = true;
                }
            }
            if(noMoreEndl){
                end = length;
            }else {
                end = endl;
            }
            if(start < end) {
                breakLen = paint.breakText(s, start, end, true, width, null);
                if (breakLen == 0){//防止死循环
                    ++start;
                    continue;
                }
                start += breakLen;
                if(!noMoreEndl){
                    ++start;
                }
            }else {
                ++start;
            }
            ++line;
        }
        return line;
    }

    /**
     *
     * @param tv 需要计算行数的textview
     * @param width 每行的宽度
     * @param maxLine 是否达到 这个行数 达到了 返回true 没达到返回false
     * @return
     */
    public static boolean getTextLine(TextView tv, int width , int maxLine){
        int line = 0;
        TextPaint paint = tv.getPaint();
        int paddingLeft = tv.getPaddingLeft();
        int paddingRight = tv.getPaddingRight();
        width -= (paddingLeft + paddingRight);
        CharSequence text = tv.getText();
        String s = text.toString();
        int length = s.length();

        int endl = -1;

        int start = 0;
        int breakLen;

        boolean noMoreEndl = false;
        int end;
        while(start < length){
            if(!noMoreEndl) {
                endl = s.indexOf('\n', start);
                if (endl == -1) {
                    noMoreEndl = true;
                }
            }
            if(noMoreEndl){
                end = length;
            }else {
                end = endl;
            }
            if(start < end) {
                breakLen = paint.breakText(s, start, end, true, width, null);
                if (breakLen == 0){//防止死循环
                    ++start;
                    continue;
                }
                start += breakLen;
                if(!noMoreEndl){
                    ++start;
                }
            }else {
                ++start;
            }
            ++line;
            if(line > maxLine) return true;
        }
        return line > maxLine;
    }

    /**
     * 复制文字到剪贴板
     * @param txt
     */
    public static void copy(Context context, CharSequence txt){
        if(txt == null){
            return;
        }
        try {
            if (Build.VERSION.SDK_INT >= 11) {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                if(clipboard != null) {
                    clipboard.setText(txt);
                }
            } else {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                if(clipboard != null) {
                    clipboard.setText(txt);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String standardizeNewline(String src){
        if(src == null){
            return null;
        }
        String s = src.replaceAll("(\\r\\n)|\\r", "\n");
        return s;
    }
}
