package com.example.attractions.service;

import com.github.benmanes.caffeine.cache.*;
import java.time.Duration;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
  private final Cache<String, List<?>> cache;

  public CacheService(@Value("${app.cache.ttl-minutes:10}") long ttl) {
    cache =
        Caffeine.newBuilder().expireAfterWrite(Duration.ofMinutes(ttl)).maximumSize(1000).build();
  }

  @SuppressWarnings("unchecked")
  public <T> Optional<List<T>> get(String key) {
    return Optional.ofNullable((List<T>) cache.getIfPresent(key));
  }

  public void put(String key, List<?> value) {
    cache.put(key, value);
  }

  public void evict(String key) {
    cache.invalidate(key);
  }

  public void clear() {
    cache.invalidateAll();
  }
}
