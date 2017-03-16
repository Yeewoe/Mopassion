package org.yeewoe.mopassion.app.im.view;

import android.support.annotation.NonNull;

import org.yeewoe.mopassion.emotion.emoji.EmojiJsonParser;
import org.yeewoe.mopassion.emotion.emoji.EmojiParser;

import java.util.HashMap;

/**
 * 聊天表情网格显示界面
 *
 * @author crjoe
 */
public class EmojiExpressionFragment extends BaseExpressionFragment {


    @Override
    public void initEmojiNum() {
        HashMap<String, String> emoMap = EmojiJsonParser.getEmojiMap().get(0);
        int size = emoMap.keySet().size();
        for (int i = 0; i < size; i++) {
            mExpressNoList.add(getExpressionSourcePrefix() + i);
        }
    }

    @Override protected String buildExpressionName() {
        return null;
    }

    @NonNull @Override protected String getExpressionSourcePrefix() {
        return EmojiParser.EMOJI_SOURCE_PREFIX;
    }

}
