package com.example.attractions.repository;

import com.example.attractions.model.Product;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
  List<Product> findByCityIgnoreCase(String city);
}
