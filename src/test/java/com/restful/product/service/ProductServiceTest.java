package com.restful.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import com.restful.product.entity.Product;
import com.restful.product.exception.ServiceException;
import com.restful.product.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId(1L);
        product.setProductName("Aspirin");
        product.setProductDescription("Pain relief tablet");
        product.setCasNumber("123-45-6");
    }

    @Test
    void testSaveOrUpdate() throws ServiceException {
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.saveOrUpdate(product);

        assertThat(result).isNotNull();
        assertThat(result.getProductName()).isEqualTo("Aspirin");
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testDeleteByProductId() throws ServiceException {
        doNothing().when(productRepository).deleteByProductId(1L);

        productService.deleteByProductId(1L);

        verify(productRepository, times(1)).deleteByProductId(1L);
    }

    @Test
    void testFindAllProducts() throws ServiceException {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(Arrays.asList(product));
        when(productRepository.findAll(pageable)).thenReturn(page);

        Page<Product> result = productService.findAllProducts(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getProductName()).isEqualTo("Aspirin");
    }

    @Test
    void testFindByProductId() throws ServiceException {
        when(productRepository.findByProductId(1L)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.findByProductId(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getProductName()).isEqualTo("Aspirin");
    }

    @Test
    void testFindByProductName() throws ServiceException {
        when(productRepository.findByProductName("Aspirin")).thenReturn(Optional.of(product));

        Optional<Product> result = productService.findByProductName("Aspirin");

        assertThat(result).isPresent();
        assertThat(result.get().getProductDescription()).contains("Pain");
    }

    @Test
    void testFindByProductNameLike() throws ServiceException {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(Arrays.asList(product));
        when(productRepository.findByProductNameLike("%Aspirin%", pageable)).thenReturn(page);

        Page<Product> result = productService.findByProductNameLike("%Aspirin%", pageable);

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void testFindByProductDescriptionLike() throws ServiceException {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(Arrays.asList(product));
        when(productRepository.findByProductDescriptionLike("%Pain%", pageable)).thenReturn(page);

        Page<Product> result = productService.findByProductDescriptionLike("%Pain%", pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getProductDescription()).contains("Pain");
    }

    @Test
    void testFindByCasNumberLike() throws ServiceException {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(Arrays.asList(product));
        when(productRepository.findByCasNumberLike("%123%", pageable)).thenReturn(page);

        Page<Product> result = productService.findByCasNumberLike("%123%", pageable);

        assertThat(result.getContent()).hasSize(1);
    }

    /*@Test
    void testFindByProductNameDesCanNumber() throws ServiceException {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(Arrays.asList(product));
        when(productRepository.findByProductNameDesCanNumber("%Aspirin%", "%Pain%", "%123%", pageable))
                .thenReturn(page);

        Page<Product> result = productService.findByProductNameDesCanNumber("%Aspirin%", "%Pain%", "%123%", pageable);

        assertThat(result.getContent()).hasSize(1);
    }*/
}
