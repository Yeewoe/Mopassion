package org.yeewoe.mopassion.emotion.mopa;

/**
 * 点头摇头系列表情解析类
 */
public class NodEmotionParser extends BaseEmotionParser {

    public static final int SIZE = 15;
    public static final String NAME = "nod";

    @Override protected int[] buildNo() {
        int[] result = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            result[i] = i;
        }

        return result;
    }

    @Override protected String buildPrevStr() {
        return NAME;
    }

    @Override protected String buildSuffixStr() {
        return ".gif";
    }
}
