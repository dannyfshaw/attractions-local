package com.example.attractions.service;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class SupplierSimulator {
  public record Slot(LocalTime time, int available, BigDecimal price) {}

  public List<Slot> getAvailability(String supplierProductId, LocalDate date) {
    int seed = Math.abs(Objects.hash(supplierProductId, date));
    if (seed % 17 == 0) return List.of();
    BigDecimal base = BigDecimal.valueOf(15 + (seed % 70));
    int stock = 1 + (seed % 8);
    return List.of(
        new Slot(LocalTime.of(9, 0), stock, base),
        new Slot(
            LocalTime.of(14, 0),
            Math.max(1, stock - 2),
            base.add(BigDecimal.valueOf((seed % 12) + 3))));
  }
}
