package com.restful.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;

import com.restful.product.entity.Product;

@DataJpaTest(excludeAutoConfiguration = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration.class,
        org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration.class
})
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    private Pageable pageable;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductName("Aspirin");
        product.setProductDescription("Pain relief tablet");
        product.setCasNumber("123-45-6");

        productRepository.save(product);

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void testFindByProductId() {
        Optional<Product> result = productRepository.findByProductId(product.getProductId());
        assertThat(result).isPresent();
        assertThat(result.get().getProductName()).isEqualTo("Aspirin");
    }

    @Test
    void testFindByProductName() {
        Optional<Product> result = productRepository.findByProductName("Aspirin");
        assertThat(result).isPresent();
        assertThat(result.get().getProductDescription()).contains("Pain");
    }

    @Test
    void testDeleteByProductId() {
        productRepository.deleteByProductId(product.getProductId());
        Optional<Product> result = productRepository.findByProductId(product.getProductId());
        assertThat(result).isNotPresent();
    }

    @Test
    void testFindByProductNameLike() {
        Page<Product> page = productRepository.findByProductNameLike("aspirin", pageable);
        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getContent().get(0).getProductName()).isEqualTo("Aspirin");
    }

    @Test
    void testFindByProductDescriptionLike() {
        Page<Product> page = productRepository.findByProductDescriptionLike("pain", pageable);
        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getContent().get(0).getProductDescription()).contains("Pain");
    }

    @Test
    void testFindByCasNumberLike() {
        Page<Product> page = productRepository.findByCasNumberLike("123", pageable);
        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getContent().get(0).getCasNumber()).isEqualTo("123-45-6");
    }

    /*
    @Test
    void testFindByProductNameDesCanNumber() {
        Page<Product> page = productRepository.findByProductNameDesCanNumber("aspirin", "pain", "123", pageable);
        assertThat(page.getContent()).hasSize(1);
        Product p = page.getContent().get(0);
        assertThat(p.getProductName()).isEqualTo("Aspirin");
        assertThat(p.getProductDescription()).contains("Pain");
        assertThat(p.getCasNumber()).isEqualTo("123-45-6");
    }*/
}
