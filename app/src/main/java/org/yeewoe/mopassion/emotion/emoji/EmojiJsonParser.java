package org.yeewoe.mopassion.emotion.emoji;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.yeewoe.mopassion.MopaApplication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class EmojiJsonParser {

    private static final int EMOJI_MAX_COUNT = 189;
    private static final String EMOJI_JSON_FILE_NAME = "emojiJson";
    private static List<HashMap<String, String>> emojiMap;

    public static List<HashMap<String, String>> getEmojiMap() {
        if (emojiMap == null) {
            emojiMap = EmojiJsonParser.parseEmojiJson(MopaApplication.getInstance());
        }
        return emojiMap;
    }

    /**
     * 将所有识别为emoji的字符串 转换为 [e][/e] 格式
     * @param input
     * @return
     */
    public static String parseEmoji(String input) {
        if (TextUtils.isEmpty(input)) {
            return "";
        }
        HashMap<String, String> emojiMap = getEmojiMap().get(1);
        StringBuilder result = new StringBuilder();
        int[] codePoints = EmojiParser.toCodePointArray(input);
        for (int i = 0; i < codePoints.length; i++) {
            String key = codePoints[i] + "";
            if (Integer.parseInt(key) < EMOJI_MAX_COUNT) {
                result.append(Character.toChars(codePoints[i]));
                continue;
            }
            if (emojiMap.containsKey(key)) {
                String value = emojiMap.get(key);
                result.append(EmojiParser.EMOJI_E_L).append(EmojiParser.EMOJI_SOURCE_PREFIX).append(value).append(EmojiParser.EMOJI_E_R);
                continue;
            }
            result.append(Character.toChars(codePoints[i]));
        }

        return result.toString();
    }

    private static List<HashMap<String, String>> parseEmojiJson(Context mContext) {
        String jsonString = readEmojiJsonFromAssert(mContext);
        List<HashMap<String, String>> maps = new ArrayList<>();
        HashMap<String, String> resultMap = new HashMap<>();
        HashMap<String, String> emojiparserMap = new HashMap<>();
        maps.add(resultMap);
        maps.add(emojiparserMap);
        try {
            JSONObject mJsonObject = new JSONObject(jsonString);
            Iterator<String> iterator = mJsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = mJsonObject.getString(key);
                resultMap.put(key, value);
                int[] ints = EmojiParser.toCodePointArray(value);
                String keys = "";
                if (ints.length <= 1) {
                    keys += ints[0];
                    emojiparserMap.put(keys, key);
                } else {
                    for (int i : ints) {
                        keys = keys + "_" + i;
                    }
                    emojiparserMap.put(keys, key);
                }
            }
            return maps;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String readEmojiJsonFromAssert(Context mContext) {
        try {
            InputStream stream = mContext.getAssets().open(EMOJI_JSON_FILE_NAME);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[128];
            int length = 0;
            while ((length = stream.read(buffer, 0, 128)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            return new String(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
