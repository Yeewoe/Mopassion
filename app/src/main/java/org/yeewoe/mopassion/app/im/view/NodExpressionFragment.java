package org.yeewoe.mopassion.app.im.view;

import org.yeewoe.mopassion.emotion.mopa.NodEmotionParser;

/**
 * Created by acc on 2016/6/16.
 */
public class NodExpressionFragment extends BaseExpressionFragment {
    @Override protected String buildExpressionName() {
        return NodEmotionParser.NAME;
    }

    @Override protected int buildColumnCount() {
        return 4;
    }

    @Override protected boolean isDynamic() {
        return true;
    }
}
