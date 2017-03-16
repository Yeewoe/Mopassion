package org.yeewoe.mopassion.app.common.view.interfaces;

import android.app.Activity;
import android.content.Intent;

/**
 * View接口，具有Activity特征
 * Created by wyw on 2016/4/8.
 */
public interface IActivityView extends IWaitView {
    void finish();
    void setResult(int resultCode);
    void setResult(int resultCode, Intent data);
    Intent getIntent();
    Activity getActivity();
    void runOnUiThread(Runnable action);
}
