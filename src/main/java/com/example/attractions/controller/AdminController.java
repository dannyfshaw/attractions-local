package com.example.attractions.controller;

import com.example.attractions.service.*;
import java.util.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
  private final AvailabilityService availability;
  private final CacheService cache;

  public AdminController(AvailabilityService a, CacheService c) {
    availability = a;
    cache = c;
  }

  @PostMapping("/refresh")
  public Map<String, String> refresh() {
    availability.refreshAll();
    return Map.of("status", "refresh started");
  }

  @PostMapping("/cache/clear")
  public Map<String, String> clearCache() {
    cache.clear();
    return Map.of("status", "cache cleared");
  }
}
