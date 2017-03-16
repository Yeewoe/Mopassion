package org.yeewoe.commonutils.common.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.widget.Toast;

import org.yeewoe.commonutils.R;

import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Intent辅助工具
 * Created by wyw on 2016/3/5.
 */
public class IntentUtil {
    /**
     * Attempt to launch the supplied {@link Intent}. Queries on-device packages before launching and
     * will display a simple message if none are available to handle it.
     */
    public static boolean maybeStartActivity(@NonNull Context context, @NonNull Intent intent) {
        return maybeStartActivity(context, intent, false);
    }

    /**
     * Attempt to launch Android's chooser for the supplied {@link Intent}. Queries on-device
     * packages before launching and will display a simple message if none are available to handle
     * it.
     */
    public static boolean maybeStartChooser(@NonNull Context context, @NonNull Intent intent) {
        return maybeStartActivity(context, intent, true);
    }

    private static boolean maybeStartActivity(@NonNull Context context, @NonNull Intent intent, boolean chooser) {
        if (hasHandler(context, intent)) {
            if (chooser) {
                intent = Intent.createChooser(intent, null);
            }
            context.startActivity(intent);
            return true;
        } else {
            Toast.makeText(context, R.string.tip_no_intent_handler, LENGTH_LONG).show();
            return false;
        }
    }

    /**
     * Queries on-device packages for a handler for the supplied {@link Intent}.
     */
    private static boolean hasHandler(@NonNull Context context, @NonNull Intent intent) {
        List<ResolveInfo> handlers = context.getPackageManager().queryIntentActivities(intent, 0);
        return !handlers.isEmpty();
    }

    private IntentUtil() {
        throw new AssertionError("No instances.");
    }
}
