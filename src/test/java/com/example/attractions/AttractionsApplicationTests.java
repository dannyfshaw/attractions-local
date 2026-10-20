package com.example.attractions;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.attractions.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AttractionsApplicationTests {
  @Autowired ProductRepository products;

  @Test
  void loadsDemoCatalogue() {
    assertThat(products.findByCityIgnoreCase("London")).hasSize(12);
    assertThat(products.findByCityIgnoreCase("New York")).hasSize(12);
  }
}
