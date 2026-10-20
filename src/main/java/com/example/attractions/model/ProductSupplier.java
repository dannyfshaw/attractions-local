package com.example.attractions.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "product_suppliers",
    uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "supplier_id"}))
public class ProductSupplier {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "product_id", nullable = false)
  private String productId;

  @Column(name = "supplier_id", nullable = false)
  private String supplierId;

  @Column(name = "supplier_product_id", nullable = false)
  private String supplierProductId;

  protected ProductSupplier() {}

  public ProductSupplier(String productId, String supplierId, String supplierProductId) {
    this.productId = productId;
    this.supplierId = supplierId;
    this.supplierProductId = supplierProductId;
  }

  public Long getId() {
    return id;
  }

  public String getProductId() {
    return productId;
  }

  public String getSupplierId() {
    return supplierId;
  }

  public String getSupplierProductId() {
    return supplierProductId;
  }
}
