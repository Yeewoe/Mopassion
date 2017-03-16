package org.yeewoe.mopassion.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.yeewoe.mopassion.utils.LogCore;

/**
 * Created by acc on 2016/3/9.
 */
public class DummyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        for (String s : extras.keySet()) {
            LogCore.i("key=" + s + ", value=" + extras.get(s));
        }
    }
}
