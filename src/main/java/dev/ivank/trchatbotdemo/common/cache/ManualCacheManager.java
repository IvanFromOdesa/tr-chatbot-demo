package dev.ivank.trchatbotdemo.common.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ManualCacheManager {
    private final CacheManager cacheManager;

    public ManualCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public <KEY extends Serializable, VALUE> void cache(String cacheName, KEY key, VALUE value) {
        Cache cache = cacheManager.getCache(cacheName);
        cache.put(key, value);
    }

    public <KEY extends Serializable, VALUE> VALUE evict(String cacheName, KEY key, Class<VALUE> clazz) {
        Cache cache = cacheManager.getCache(cacheName);
        VALUE value = cache.get(key, clazz);
        cache.evict(key);
        return value;
    }

    public List<String> cacheNamesInUse() {
        return new ArrayList<>(cacheManager.getCacheNames());
    }
}
