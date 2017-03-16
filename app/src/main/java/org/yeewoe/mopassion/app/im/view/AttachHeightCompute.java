package org.yeewoe.mopassion.app.im.view;

import android.content.res.Resources;

import org.yeewoe.mopassion.R;

/**
 * Created by acc on 2016/3/30.
 */
public class AttachHeightCompute {
    public static int getAttachHeight(Resources mResources) {
        return mResources.getDisplayMetrics().widthPixels / 3 * 2
                - mResources.getDimensionPixelOffset(R.dimen.anim_ten_dp) * 4 + mResources.getDimensionPixelOffset(R.dimen.rounded_hint_divider_height);
    }

    public static int getAllExpressHeight(Resources mResources) {
        return getAttachHeight(mResources) - mResources.getDimensionPixelOffset(R.dimen.im_chat_express_tab_height);
    }
}
