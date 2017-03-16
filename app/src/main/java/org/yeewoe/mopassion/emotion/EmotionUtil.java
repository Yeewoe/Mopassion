package org.yeewoe.mopassion.emotion;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;

import org.yeewoe.mopassion.MopaApplication;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.emotion.emoji.EmojiJsonParser;
import org.yeewoe.mopassion.emotion.emoji.EmojiParser;
import org.yeewoe.mopassion.utils.LogCore;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by acc on 2016/6/14.
 */
public class EmotionUtil {

    /**
     * 转化方法，转化为Drawable
     */
    @Nullable public static Drawable parseToDrawable(Context context, String name) {
        int resId = getEmojiDrawableId(name);
        if (resId <= 0) return null;
        return context.getResources().getDrawable(resId);
    }

    /**
     * 转化方法，转化为Drawable
     */
    public static int parseToDrawableId(String name) {
        return getEmojiDrawableId(name);
    }

    /**
     * 转化方法，转化指定资源名字到图像Spannable
     */
    @NonNull public static Spannable parseToMsg(Context context, String expressRes) {
        String source = expressRes;
        int id = getEmojiDrawableId(source);

        expressRes = EmojiParser.EMOJI_E_L + expressRes + EmojiParser.EMOJI_E_R;

        Spannable mSpannable = Spannable.Factory.getInstance()
                .newSpannable(expressRes);
        if (id <= 0) {
            return mSpannable;
        }
        Drawable drawable = context.getResources().getDrawable(id);
        if (drawable != null) {
            drawable.setBounds(
                    0,
                    0,
                    context.getResources().getDimensionPixelOffset(
                            R.dimen.express_width_small), context.getResources()
                            .getDimensionPixelOffset(R.dimen.express_width_small));
        }
        ImageSpan span = new ImageSpan(drawable, source);
        mSpannable.setSpan(span, 0, expressRes.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return mSpannable;
    }

    /**
     * 转化方法，动态表情转化为[d][/d] 格式
     * @param expressRes
     * @return
     */
    public static String parseToDynamicMsg(String expressRes) {
        return EmojiParser.DYNAMIC_E_L + expressRes + EmojiParser.DYNAMIC_E_R;
    }

    /**
     * 转化方法，表情转换为 [e][/e] 格式后 在通过这个方法转为 表情符号
     */
    public static SpannableStringBuilder parseToMsg(String content, Context mContext, boolean isoutside) {
        String regex = "\\[e\\](.*?)\\[/e\\]";
        Pattern pattern = Pattern.compile(regex);
        String emo = "";
        Resources resources = mContext.getResources();
        String unicode = EmojiParser.getInstance().parseEmoji(content);
        Matcher matcher = pattern.matcher(unicode);
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(unicode);
        Drawable drawable = null;
        ImageSpan span = null;
        while (matcher.find()) {
            emo = matcher.group();
            try {
                String source = emo.substring(emo.indexOf("]") + 1,
                        emo.lastIndexOf("["));
                int id = getEmojiDrawableId(source);
                if (id != 0) {
                    try {
                        drawable = resources.getDrawable(id);
                    } catch (OutOfMemoryError e) {
                        LogCore.e(e, "resources.getDrawable oom");
                        continue;
                    }
                    if (isoutside) {
                        drawable.setBounds(
                                0,
                                0,
                                mContext.getResources()
                                        .getDimensionPixelOffset(
                                                R.dimen.express_width_outside),
                                mContext.getResources()
                                        .getDimensionPixelOffset(
                                                R.dimen.express_width_outside));
                    } else if (drawable.getIntrinsicHeight() < 90) {
                        drawable.setBounds(
                                0,
                                0,
                                mContext.getResources()
                                        .getDimensionPixelOffset(
                                                R.dimen.express_width_small),
                                mContext.getResources()
                                        .getDimensionPixelOffset(
                                                R.dimen.express_width_small));

                    } else {
                        drawable.setBounds(
                                0,
                                0,
                                mContext.getResources()
                                        .getDimensionPixelOffset(
                                                R.dimen.express_width_normal),
                                mContext.getResources()
                                        .getDimensionPixelOffset(
                                                R.dimen.express_width_normal));
                    }
                    span = new ImageSpan(drawable, source);
                    sBuilder.setSpan(span, matcher.start(), matcher.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        return sBuilder;
    }

    /**
     * 将[d]格式的文本转化为资源ID
     */
    public static int parseToDynamicResId(String content) {
        String source = content.substring(content.indexOf("]") + 1,
                content.lastIndexOf("["));
        return getEmojiDrawableId(source);
    }

    /**
     * 转化方法，转化为文本串
     */
    public static String parseToText(CharSequence cs, Context mContext) {
        try {
            HashMap<String, String> emojimap = EmojiJsonParser.getEmojiMap().get(0);
            SpannableStringBuilder ssb = EmotionUtil.parseToMsg(cs.toString(), mContext,
                    false);
            ImageSpan[] spans = ssb.getSpans(0, cs.length(), ImageSpan.class);
            for (int i = 0; i < spans.length; i++) {
                ImageSpan span = spans[i];
                String c = span.getSource();
                int a = ssb.getSpanStart(span);
                int b = ssb.getSpanEnd(span);
                if (c.contains("emoji")) {
                    String convertedCode = convertUnicode(c, emojimap);
                    if (!TextUtils.isEmpty(convertedCode)) {
                        ssb.replace(a, b, convertedCode);
                    } else {
                        ssb.replace(a, b, "");
                    }
                }
            }
            ssb.clearSpans();
            return ssb.toString();
        } catch (Exception e) {
            return " ";
        }
    }

    /**
     * 转化，转成[表情]样式
     */
    public static String parseInfo(String content) {
        if (content == null) {
            return null;
        }
        Resources mResources = MopaApplication.getInstance().getResources();
        String regex = "\\[e\\](.*?)\\[/e\\]";
        Pattern pattern = Pattern.compile(regex);
        Matcher mathcer = pattern.matcher(content);
        String result = mathcer.replaceAll(mResources.getString(R.string.emoji_express));

        String regexDynamic = "\\[d\\](.*?)\\[/d\\]";
        Pattern patternDynamic = Pattern.compile(regexDynamic);
        Matcher mathcerDynamic = patternDynamic.matcher(result);
        return mathcerDynamic.replaceAll(mResources.getString(R.string.emoji_dynamic_express));
    }

    /**
     * 是否是动态表情
     */
    public static boolean isDynamicEmotion(String msg) {
        String regex = "\\[d\\](.*?)\\[/d\\]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(msg);
        return matcher.find();
    }

    private static int getEmojiDrawableId(String source) {
        return MopaApplication.getInstance().getResources().getIdentifier(source, "drawable",
                MopaApplication.getInstance().getPackageName());
    }

    private static String convertUnicode(String emo,
                                         HashMap<String, String> maps) {
        emo = emo.substring(emo.indexOf("_") + 1);
        return maps.get(emo);
    }
}
