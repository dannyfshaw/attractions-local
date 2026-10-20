package com.example.attractions.controller;

import com.example.attractions.model.*;
import com.example.attractions.repository.*;
import java.util.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {
  private final ProductRepository products;

  public CatalogController(ProductRepository p) {
    products = p;
  }

  @GetMapping("/products")
  public List<Product> products(@RequestParam(required = false) String city) {
    return city == null ? products.findAll() : products.findByCityIgnoreCase(city);
  }
}
