package com.example.attractions.repository;

import com.example.attractions.model.Availability;
import java.time.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
  Optional<Availability> findByProductIdAndSupplierIdAndDateAndTime(
      String p, String s, LocalDate d, LocalTime t);

  @Query(
      "select a from Availability a where a.productId=:p and a.date between :from and :to and a.available > 0 order by a.price asc")
  List<Availability> findAvailable(
      @Param("p") String p, @Param("from") LocalDate from, @Param("to") LocalDate to);
}
