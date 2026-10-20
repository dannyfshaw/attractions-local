package com.example.attractions.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "products")
public class Product {
  @Id private String id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String city;

  private String description;
  private String imageUrl;

  @Column(nullable = false)
  private String currency;

  protected Product() {}

  public Product(
      String id, String name, String city, String description, String imageUrl, String currency) {
    this.id = id;
    this.name = name;
    this.city = city;
    this.description = description;
    this.imageUrl = imageUrl;
    this.currency = currency;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getCity() {
    return city;
  }

  public String getDescription() {
    return description;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getCurrency() {
    return currency;
  }
}
