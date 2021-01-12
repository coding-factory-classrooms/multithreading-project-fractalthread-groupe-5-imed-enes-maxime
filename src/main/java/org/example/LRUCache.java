package org.example;

import java.util.LinkedHashMap;

public class LRUCache<K, V> {

    private final LinkedHashMap<K, V> cache;
    private int capacity = 0;

    public LRUCache(){
        this.cache = new LinkedHashMap<K, V>(capacity, 0.75f, true);
    }

    private void resize(int maxSize) {
        this.capacity = maxSize;
    }
    public final V get(K key) {

        V mapValue;
        synchronized (cache) {
            mapValue = cache.get(key);
            if (mapValue != null) {
                return mapValue;
            }
        }
        return mapValue;
    }

    public synchronized void put(K key, V value){
        if(cache.get(key) == null){
            if (cache.size() == capacity) {
                resize(cache.size()+1);
                cache.put(key, value);
            }

        }
    }

    @Override
    public String toString() {
        return "LRUCache{" +
                "cache=" + cache +
                '}';
    }
}
