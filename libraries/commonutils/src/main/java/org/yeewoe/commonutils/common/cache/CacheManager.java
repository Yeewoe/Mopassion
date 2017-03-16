package org.yeewoe.commonutils.common.cache;

import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存管理器
 * 支持多种缓存策略
 * Created by wyw on 2016/3/7.
 */
public class CacheManager<T, P> {
    public static final int LEVEL_WEEK      = 0;
    public static final int LEVEL_SOFT      = 1;
    public static final int LEVEL_STRONG    = 2;
    public static final int LEVEL_LRU       = 3;

    private MapCache<T, P> mapCache;
    private LruCache<T, P> lruCache;
    private int level;

    public CacheManager() {
        this(LEVEL_WEEK);
    }

    /**
     * @param level 缓存等级，{@link #LEVEL_WEEK}、{@link #LEVEL_SOFT}、{@link #LEVEL_STRONG} 或者 {@link #LEVEL_LRU}
     */
    public CacheManager(int level){
        this.level = level;

        if (isMapLevel()) {
            mapCache = new MapCache<>();
        } else if (isLruLevel()) {
            int lruSize = 4096;
            lruCache = new LruCache<>(lruSize);
        }
    }

    private boolean isLruLevel() {
        return level == LEVEL_LRU;
    }

    private boolean isMapLevel() {
        switch (level) {
            case LEVEL_WEEK:
            case LEVEL_SOFT:
            case LEVEL_STRONG:
                return true;
            default:
                return false;
        }
    }

    public P get(@NonNull T key){
        if (isMapLevel()) {
            ConcurrentHashMap<T, P> map = mapCache.getCache();
            if (map != null) {
                return map.get(key);
            }
        } else if (isLruLevel()) {
            return lruCache.get(key);
        }
        return null;
    }

    public void put(@NonNull T key , P value){
        if (isMapLevel()) {
            ConcurrentHashMap<T, P> map = mapCache.getCache();
            if (map != null) {
                map.put(key, value);
            }
        } else if (isLruLevel()) {
            lruCache.put(key, value);
        }
    }

    public void clear(@NonNull T key){
        if (isMapLevel()) {
            ConcurrentHashMap<T, P> cache = this.mapCache.getCache();
            if (cache != null) {
                cache.remove(key);
            }
        } else if (isLruLevel()) {
            lruCache.remove(key);
        }
    }

    public void clearAll(){
        if(mapCache != null) {
            if(mapCache.getCache() != null) {
                mapCache.getCache().clear();
            }
            if(mapCache.weakReferenceCache != null) {
                mapCache.weakReferenceCache.clear();
            }
        }
    }

    private class MapCache<T, P>  {
        ConcurrentHashMap <T, P> cache;
        WeakReference<ConcurrentHashMap <T, P>> weakReferenceCache;
        SoftReference<ConcurrentHashMap <T, P>> softReferenceCache;

        private MapCache(){
            initCache();
        }

        public ConcurrentHashMap<T, P> getCache(){
            if (getInnerCache() == null) {
                initCache();
            }
            return getInnerCache();
        }

        public void initCache() {
            cache = new ConcurrentHashMap<>();
            switch (level) {
                case LEVEL_WEEK:
                    weakReferenceCache = new WeakReference<>(cache);
                    break;
                case LEVEL_SOFT:
                    softReferenceCache = new SoftReference<>(cache);
                    break;
                case LEVEL_STRONG:
                    break;
                default:
                    break;
            }
        }

        private ConcurrentHashMap<T, P> getInnerCache() {
            switch (level) {
                case LEVEL_WEEK:
                    return weakReferenceCache.get();
                case LEVEL_SOFT:
                    return softReferenceCache.get();
                case LEVEL_STRONG:
                    return cache;
                default:
                    return null;
            }
        }
    }

}
