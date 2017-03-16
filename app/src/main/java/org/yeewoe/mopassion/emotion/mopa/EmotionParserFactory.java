package org.yeewoe.mopassion.emotion.mopa;

import android.support.annotation.Nullable;

/**
 * 表情解析器工厂
 * Created by wyw on 2016/6/14.
 */
public class EmotionParserFactory {
    @Nullable public static BaseEmotionParser build(@Nullable String name) {
        if (name == null) {
            return null;
        }

        if (name.equalsIgnoreCase("emoji")) {

        } else if (name.equalsIgnoreCase(ZemEmotionParser.NAME)) {
            return new ZemEmotionParser();
        } else if (name.equalsIgnoreCase(NodEmotionParser.NAME)) {
            return new NodEmotionParser();
        }

        return null;
    }
}
