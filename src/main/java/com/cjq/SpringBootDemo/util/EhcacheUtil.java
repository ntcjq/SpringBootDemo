package com.cjq.SpringBootDemo.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EhcacheUtil {

    @Autowired
    private CacheManager cacheManager;

    public void put(String cacheName, String key, Object value) {
        Cache cache = cacheManager.getCache(cacheName);
        Element element = new Element(key, value);
        cache.put(element);
    }

    public Object get(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        Element element = cache.get(key);
        return element == null ? null : element.getObjectValue();
    }

    public void remove(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        cache.remove(key);
    }

    public Cache get(String cacheName) {
        return cacheManager.getCache(cacheName);
    }

    public List<Map> getCacheList(String cacheName){
        Cache cache = cacheManager.getCache(cacheName);
        Map<Object,Element> elements = cache.getAll(cache.getKeys());
        List<Map> list = new ArrayList<>();
        if(elements!=null){
            for (Map.Entry<Object, Element> entry : elements.entrySet()) {
                Map map = new HashMap();
                map.put("key",entry.getValue().getObjectKey().toString());
                map.put("value",entry.getValue().getObjectValue());
                list.add(map);
            }
        }
        return list;
    }
}