package org.yeewoe.mopassion.db.core;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.yeewoe.mopassion.MopaApplication;

/**
 * DBHelper中心
 * Created by wyw on 2016/4/1.
 */
public class DbHelperManager {
    private static MopaDbHelper mInstance;
    public static MopaDbHelper getInstance() {
        if (mInstance == null) {
            synchronized (DbHelperManager.class) {
                if (mInstance == null) {
                    mInstance = new MopaDbHelper(MopaApplication.getInstance());
                }
            }
        }

        return mInstance;

    }

    public static void release() {
        if (mInstance != null) {
            mInstance.close();
            mInstance = null;
        }
        OpenHelperManager.releaseHelper();
    }
}
