package com.example.hazelcast.caching.service;

import com.example.hazelcast.model.Employee;
import com.hazelcast.collection.ISet;
import com.hazelcast.core.HazelcastInstance;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public abstract class HazelcastSetService<K, V> {

    private final HazelcastInstance hazelcastInstance;

    public boolean createData(String cacheName, V value) {
        ISet<V> cache = hazelcastInstance.getSet(cacheName);
        cache.add(value);
        return true;
    }

    public Optional<V> getDataByKey(K key ) {
        ISet<V> cache = getSet();
        return cache.stream().filter(value -> getKey(value).equals(key)).findFirst();
    }

    public void deleteData(String cacheName, K key) {
        ISet<V> cache = hazelcastInstance.getSet(cacheName);
        getDataByKey(key).ifPresent(cache::remove);
    }

    public ISet<V> getSet() {
        return hazelcastInstance.getSet(getCacheName());
    }

    protected abstract K getKey(V value);

    protected abstract String getCacheName();

}
