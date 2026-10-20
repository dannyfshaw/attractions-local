package com.example.attractions.repository;

import com.example.attractions.model.PriceSummary;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface PriceSummaryRepository extends JpaRepository<PriceSummary, String> {
  @Query(
      "select p from PriceSummary p where lower(p.city)=lower(:city) order by p.minimumPrice asc")
  List<PriceSummary> findCheapest(@Param("city") String city);
}
