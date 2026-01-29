package com.restful.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.restful.product.entity.Product;

@Repository
public interface ProductRepositoryDemo extends JpaRepository<Product, Long> {
	
	//Optional<Product> findByProductName(String name);
	Optional<Product> findByProductId(Long id);
	
	@Query (
			"""
			select name from product
			where lower(name) like lower(concat('%', productName, '%'))
			""")
			List<Product> findProductNameLike(@Param("productName") String productName);
	
	//JPQL query
	@Query (
			"""
			SELECT p FROM PRODUCT p WHERE p.productname = :productName
			"""
			)
			Product findByProductName(@Param("productName") String productName);
	
	//Native query
	@Query(value="Select p from product p where productName = ?", nativeQuery = true)
	Product findByNativeProductName(@Param("productName") String productName);
}
