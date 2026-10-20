package com.example.attractions.repository;

import com.example.attractions.model.ProductSupplier;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, Long> {
  List<ProductSupplier> findByProductId(String productId);
}
