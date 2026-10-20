package com.example.attractions.controller;

import com.example.attractions.service.QueryService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cities")
public class AttractionController {
  private final QueryService query;

  public AttractionController(QueryService q) {
    query = q;
  }

  @GetMapping("/{city}/attractions/cheapest")
  public Map<String, Object> cheapest(
      @PathVariable String city, @RequestParam(defaultValue = "20") @Min(1) @Max(20) int limit) {
    return Map.of(
        "city",
        city,
        "validFrom",
        java.time.LocalDate.now(),
        "validTo",
        java.time.LocalDate.now().plusDays(90),
        "attractions",
        query.cheapest(city, limit));
  }
}
