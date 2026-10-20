package com.example.attractions.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "price_summary")
public class PriceSummary {
  @Id private String productId;

  @Column(nullable = false)
  private String city;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal minimumPrice;

  @Column(nullable = false)
  private LocalDate minimumPriceDate;

  private LocalTime minimumPriceTime;
  private String supplierId;
  private LocalDateTime lastUpdated;

  protected PriceSummary() {}

  public PriceSummary(
      String id, String city, BigDecimal price, LocalDate date, LocalTime time, String supplier) {
    this.productId = id;
    this.city = city;
    this.minimumPrice = price;
    this.minimumPriceDate = date;
    this.minimumPriceTime = time;
    this.supplierId = supplier;
    this.lastUpdated = LocalDateTime.now();
  }

  public String getProductId() {
    return productId;
  }

  public String getCity() {
    return city;
  }

  public BigDecimal getMinimumPrice() {
    return minimumPrice;
  }

  public LocalDate getMinimumPriceDate() {
    return minimumPriceDate;
  }

  public LocalTime getMinimumPriceTime() {
    return minimumPriceTime;
  }

  public String getSupplierId() {
    return supplierId;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public void update(BigDecimal p, LocalDate d, LocalTime t, String s) {
    minimumPrice = p;
    minimumPriceDate = d;
    minimumPriceTime = t;
    supplierId = s;
    lastUpdated = LocalDateTime.now();
  }
}
