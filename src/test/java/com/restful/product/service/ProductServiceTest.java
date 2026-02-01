package com.restful.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restful.product.entity.Product;
import com.restful.product.exception.ServiceException;
import com.restful.product.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductService productService;

	private Product product;

	@BeforeEach
	void setUp() {
		product = new Product();
		product.setProductId(1L);
		product.setProductName("Aspirin");
		product.setProductDescription("Pain reliever");
		product.setCasNumber("50-78-2");
	}

	@Test
	void testSaveOrUpdate() throws ServiceException {
		Mockito.when(productRepository.save(any(Product.class))).thenReturn(product);

		Product saved = productService.saveOrUpdate(product);
		assertThat(saved).isNotNull();
		assertThat(saved.getProductName()).isEqualTo("Aspirin");

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
		List<Product> products = Arrays.asList(product);
		when(productRepository.findAll()).thenReturn(products);

		List<Product> result = productService.findAllProducts();
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getProductName()).isEqualTo("Aspirin");
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
		assertThat(result.get().getProductName()).isEqualTo("Aspirin");
	}

	@Test
	void testFindByProductNameLike() throws ServiceException {
		when(productRepository.findByProductNameLike("Asp")).thenReturn(Arrays.asList(product));

		List<Product> result = productService.findByProductNameLike("Asp");
		assertThat(result).hasSize(1);
	}

	@Test
	void testFindByProductDescriptionLike() throws ServiceException {
		when(productRepository.findByProductDescriptionLike("Pain")).thenReturn(Arrays.asList(product));

		List<Product> result = productService.findByProductDescriptionLike("Pain");
		assertThat(result).hasSize(1);
	}

	@Test
	void testFindByCasNumberLike() throws ServiceException {
		when(productRepository.findByCasNumberLike("50-78")).thenReturn(Arrays.asList(product));

		List<Product> result = productService.findByCasNumberLike("50-78");
		assertThat(result).hasSize(1);
	}

	@Test
	void testFindByProductNameDesCanNumber() throws ServiceException {
		when(productRepository.findByProductNameDesCanNumber("Aspirin", "Pain reliever", "50-78-2"))
				.thenReturn(Arrays.asList(product));

		List<Product> result = productService.findByProductNameDesCanNumber("Aspirin", "Pain reliever", "50-78-2");
		assertThat(result).hasSize(1);
	}
}
