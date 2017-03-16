package org.yeewoe.mopassion.emotion.emoji;

import org.yeewoe.mopassion.MopaApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmojiParser {

    public static final String EMOJI_SOURCE_PREFIX = "emoji_";
    public static final String EMOJI_E_L = "[e]";
    public static final String EMOJI_E_R = "[/e]";
    public static final String DYNAMIC_E_L = "[d]";
    public static final String DYNAMIC_E_R = "[/d]";

    private static EmojiParser mParser;
    private HashMap<List<Integer>, String> convertMap = new HashMap<>();

    private EmojiParser() {
        convertMap = new HashMap<>();
    }

    public static EmojiParser getInstance() {
        if (mParser == null) {
            mParser = new EmojiParser();
        }
        return mParser;
    }

    public static int[] toCodePointArray(String str) {
        char[] ach = str.toCharArray();
        int len = ach.length;
        int[] acp = new int[Character.codePointCount(ach, 0, len)];
        int j = 0;
        for (int i = 0, cp; i < len; i += Character.charCount(cp)) {
            cp = Character.codePointAt(ach, i);
            acp[j++] = cp;
        }
        return acp;
    }

    public String parseEmoji(String input) {
        if (input == null || input.length() <= 0) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        int[] codePoints = toCodePointArray(input);
        List<Integer> key;
        for (int codePoint : codePoints) {
            key = new ArrayList<>();
            key.add(codePoint);
            if (convertMap.containsKey(key)) {
                String value = convertMap.get(key);
                if (value != null) {
                    result.append(EMOJI_E_L).append(EMOJI_SOURCE_PREFIX).append(value).append(EMOJI_E_R);
                }
                continue;
            }
            result.append(Character.toChars(codePoint));
        }
        return result.toString();
    }

}
