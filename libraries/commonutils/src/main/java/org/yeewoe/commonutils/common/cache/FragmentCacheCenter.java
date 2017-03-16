package org.yeewoe.commonutils.common.cache;

import android.support.v4.app.Fragment;

/**
 * Fragment缓存器
 * Created by wyw on 2016/3/7.
 */
public class FragmentCacheCenter {
    private CacheManager<Class, Fragment> cacheManager;

    public FragmentCacheCenter() {
        cacheManager = new CacheManager<>(CacheManager.LEVEL_SOFT);
    }

    public void put(Class cls, Fragment fragment) {
        if (cacheManager.get(cls) == null) {
            cacheManager.put(cls, fragment);
        }
    }

    public Fragment get(Class cls) {
        return cacheManager.get(cls);
    }
}
