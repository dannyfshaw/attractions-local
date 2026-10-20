package com.example.attractions.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(
    name = "availability",
    uniqueConstraints =
        @UniqueConstraint(
            columnNames = {"product_id", "supplier_id", "available_date", "available_time"}))
public class Availability {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "product_id", nullable = false)
  private String productId;

  @Column(name = "supplier_id", nullable = false)
  private String supplierId;

  @Column(name = "available_date", nullable = false)
  private LocalDate date;

  @Column(name = "available_time", nullable = false)
  private LocalTime time;

  private int available;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  @Column(name = "last_updated", nullable = false)
  private LocalDateTime lastUpdated;

  protected Availability() {}

  public Availability(String p, String s, LocalDate d, LocalTime t, int a, BigDecimal price) {
    this.productId = p;
    this.supplierId = s;
    this.date = d;
    this.time = t;
    this.available = a;
    this.price = price;
    this.lastUpdated = LocalDateTime.now();
  }

  public String getProductId() {
    return productId;
  }

  public String getSupplierId() {
    return supplierId;
  }

  public LocalDate getDate() {
    return date;
  }

  public LocalTime getTime() {
    return time;
  }

  public int getAvailable() {
    return available;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public void update(int a, BigDecimal p) {
    this.available = a;
    this.price = p;
    this.lastUpdated = LocalDateTime.now();
  }
}
