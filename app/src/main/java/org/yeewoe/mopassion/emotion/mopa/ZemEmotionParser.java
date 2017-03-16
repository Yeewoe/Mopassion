package org.yeewoe.mopassion.emotion.mopa;

/**
 * 陌陌水滴系列表情解析类
 */
public class ZemEmotionParser extends BaseEmotionParser {

    public static final int SIZE = 73;
    public static final String NAME = "zem";

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
}
