package org.yeewoe.mopassion.app.im.view;

import org.yeewoe.mopassion.emotion.mopa.ZemEmotionParser;

/**
 * Zem聊天表情网格显示界面
 *
 * @author crjoe
 */
public class ZemExpressionFragment extends BaseExpressionFragment {

    @Override protected String buildExpressionName() {
        return ZemEmotionParser.NAME;
    }

}
