package com.restful.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.restful.product.entity.Product;

@DataJpaTest(excludeAutoConfiguration = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration.class,
        org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration.class
})
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll(); // clean DB before each test

        product1 = new Product();
        product1.setProductName("Aspirin");
        product1.setProductDescription("Pain reliever");
        product1.setCasNumber("50-78-2");

        product2 = new Product();
        product2.setProductName("Paracetamol");
        product2.setProductDescription("Fever reducer");
        product2.setCasNumber("103-90-2");

        productRepository.save(product1);
        productRepository.save(product2);
    }

    @Test
    @DisplayName("Find product by ID")
    void testFindByProductId() {
        Optional<Product> found = productRepository.findByProductId(product1.getProductId());
        assertThat(found).isPresent();
        assertThat(found.get().getProductName()).isEqualTo("Aspirin");
    }

    @Test
    @DisplayName("Find product by Name")
    void testFindByProductName() {
        Optional<Product> found = productRepository.findByProductName("Paracetamol");
        assertThat(found).isPresent();
        assertThat(found.get().getProductDescription()).isEqualTo("Fever reducer");
    }

    @Test
    @DisplayName("Delete product by ID")
    void testDeleteByProductId() {
        productRepository.deleteByProductId(product1.getProductId());
        Optional<Product> found = productRepository.findByProductId(product1.getProductId());
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Find by product name like")
    void testFindByProductNameLike() {
        List<Product> products = productRepository.findByProductNameLike("par");
        assertThat(products).hasSize(1)
                            .extracting(Product::getProductName)
                            .contains("Paracetamol");
    }

    @Test
    @DisplayName("Find by product description like")
    void testFindByProductDescriptionLike() {
        List<Product> products = productRepository.findByProductDescriptionLike("pain");
        assertThat(products).hasSize(1)
                            .extracting(Product::getProductDescription)
                            .contains("Pain reliever");
    }

    @Test
    @DisplayName("Find by CAS number like")
    void testFindByCasNumberLike() {
        List<Product> products = productRepository.findByCasNumberLike("103");
        assertThat(products).hasSize(1)
                            .extracting(Product::getCasNumber)
                            .contains("103-90-2");
    }

    @Test
    @DisplayName("Find by product name, description, and CAS number")
    void testFindByProductNameDesCanNumber() {
        List<Product> products = productRepository.findByProductNameDesCanNumber(
            "Aspirin", "Pain", "50-78"
        );

        assertThat(products).hasSize(1)
                            .extracting(Product::getProductName)
                            .contains("Aspirin");
    }
}
