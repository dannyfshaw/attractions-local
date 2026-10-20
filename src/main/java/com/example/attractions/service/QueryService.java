package com.example.attractions.service;

import com.example.attractions.model.*;
import com.example.attractions.repository.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;
import org.springframework.stereotype.Service;

@Service
public class QueryService {
  public record Result(
      String productId,
      String name,
      String city,
      java.math.BigDecimal minimumPrice,
      String currency,
      LocalDate availableDate,
      java.time.LocalTime availableTime,
      String supplierId,
      String lastUpdated) {}

  private final PriceSummaryRepository summaries;
  private final ProductRepository products;
  private final CacheService cache;

  public QueryService(PriceSummaryRepository s, ProductRepository p, CacheService c) {
    summaries = s;
    products = p;
    cache = c;
  }

  public List<Result> cheapest(String city, int limit) {
    String key = "cheapest:" + city.toLowerCase(Locale.ROOT);
    //	List<Result> cached = cache.get(key).orElse(null);
    List<Result> cached = cache.<Result>get(key).orElse(null);
    if (cached != null) return cached.stream().limit(limit).toList();
    List<Result> result =
        summaries.findCheapest(city).stream()
            .map(
                s ->
                    products
                        .findById(s.getProductId())
                        .map(
                            p ->
                                new Result(
                                    p.getId(),
                                    p.getName(),
                                    p.getCity(),
                                    s.getMinimumPrice(),
                                    p.getCurrency(),
                                    s.getMinimumPriceDate(),
                                    s.getMinimumPriceTime(),
                                    s.getSupplierId(),
                                    String.valueOf(s.getLastUpdated())))
                        .orElse(null))
            .filter(Objects::nonNull)
            .limit(20)
            .toList();
    cache.put(key, result);
    return result.stream().limit(limit).toList();
  }
}
