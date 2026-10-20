package com.example.attractions.service;

import com.example.attractions.model.*;
import com.example.attractions.repository.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AvailabilityService {
  private final ProductRepository products;
  private final ProductSupplierRepository mappings;
  private final AvailabilityRepository availability;
  private final PriceSummaryRepository summaries;
  private final SupplierSimulator suppliers;
  private final CacheService cache;
  private final Executor availabilityExecutor;

  public AvailabilityService(
      ProductRepository p,
      ProductSupplierRepository m,
      AvailabilityRepository a,
      PriceSummaryRepository s,
      SupplierSimulator sup,
      CacheService c,
      @Qualifier("availabilityExecutor") Executor availExecutor) {
    products = p;
    mappings = m;
    availability = a;
    summaries = s;
    suppliers = sup;
    cache = c;
    availabilityExecutor = availExecutor;
  }

  @Scheduled(
      initialDelayString = "${app.refresh.initial-delay-ms:3000}",
      fixedDelayString = "${app.refresh.fixed-delay-ms:300000}")
  public void scheduledRefresh() {
    refreshAll();
  }

  @Async("availabilityExecutor")
  public void refreshAll() {
    LocalDate from = LocalDate.now(), to = from.plusDays(90);
    mappings
        .findAll()
        .forEach(
            m ->
                CompletableFuture.runAsync(
                    () -> refreshMapping(m, from, to), availabilityExecutor));
  }

  @Transactional
  public void refreshMapping(ProductSupplier m, LocalDate from, LocalDate to) {
    for (LocalDate d = from; !d.isAfter(to); d = d.plusDays(1)) {
      for (SupplierSimulator.Slot slot : suppliers.getAvailability(m.getSupplierProductId(), d)) {
        var existing =
            availability
                .findByProductIdAndSupplierIdAndDateAndTime(
                    m.getProductId(), m.getSupplierId(), d, slot.time())
                .orElse(null);
        if (existing == null)
          availability.save(
              new Availability(
                  m.getProductId(),
                  m.getSupplierId(),
                  d,
                  slot.time(),
                  slot.available(),
                  slot.price()));
        else existing.update(slot.available(), slot.price());
      }
    }
    recalculate(m.getProductId());
  }

  @Transactional
  public void recalculate(String productId) {
    LocalDate from = LocalDate.now(), to = from.plusDays(90);
    List<Availability> rows = availability.findAvailable(productId, from, to);
    if (rows.isEmpty()) {
      summaries.deleteById(productId);
      return;
    }
    Availability best = rows.get(0);
    Product p = products.findById(productId).orElseThrow();
    PriceSummary s = summaries.findById(productId).orElse(null);
    if (s == null)
      summaries.save(
          new PriceSummary(
              productId,
              p.getCity(),
              best.getPrice(),
              best.getDate(),
              best.getTime(),
              best.getSupplierId()));
    else s.update(best.getPrice(), best.getDate(), best.getTime(), best.getSupplierId());
    cache.evict("cheapest:" + p.getCity().toLowerCase(Locale.ROOT));
  }
}
