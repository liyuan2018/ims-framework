package com.spark.ims.common.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 内存缓存管理
 * Created by liyuan on 2018/4/26.
 */
public class MemoryCacheManager {

    /** 全局静态缓存 */
    private static Map<String,Object> cacheMap = new HashMap<String, Object>();

    private static class LazyHolder {
        private static final MemoryCacheManager INSTANCE = new MemoryCacheManager();
    }

    private MemoryCacheManager(){}

    public static MemoryCacheManager getInstance(){
        return LazyHolder.INSTANCE;
    }

    /**
     * 读取缓存数据
     * @param cacheName 缓存名称
     * @return
     */
    public Object getMemoryCache(String cacheName){
        return cacheMap.get(cacheName);
    }

    /**
     * 存储缓存数据
     * @param cacheName 缓存名称
     * @param cacheData 缓存数据
     */
    public void putMemoryCache(String cacheName,Object cacheData){
        if(cacheData != null && !cacheMap.containsKey(cacheName)){
            cacheMap.put(cacheName,cacheData);
        }
    }

    /**
     * 移除缓存数据
     * @param cacheName 缓存名称
     */
    public void removeMemoryCache(String cacheName){
        if(cacheMap.containsKey(cacheName)){
            cacheMap.remove(cacheName);
        }
    }

    /**
     * 移除所有缓存数据
     */
    public void removeAll(){
        cacheMap.clear();
    }

    /**
     * 是否存在缓存
     * @param cacheName
     * @return
     */
    public boolean isExist(String cacheName){
        return cacheMap.containsKey(cacheName);
    }
}
