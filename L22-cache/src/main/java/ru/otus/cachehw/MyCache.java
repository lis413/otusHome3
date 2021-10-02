package ru.otus.cachehw;


import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {
//Надо реализовать эти методы
    Map<K, V> mapCash = new WeakHashMap<>();
    List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        mapCash.put(key, value);
        this.listeners.forEach(listener -> listener.notify(key, value, "put"));
    }

    @Override
    public void remove(K key) {
        mapCash.remove(key);
        this.listeners.forEach(listener -> listener.notify(key, mapCash.get(key), "remove"));
    }

    @Override
    public V get(K key) {
        this.listeners.forEach(listener -> listener.notify(key, mapCash.get(key), "get"));
        return mapCash.get(key);

    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);

    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}
