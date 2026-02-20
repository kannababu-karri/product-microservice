package com.restful.product.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.restful.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	Optional<Product> findByProductId(Long id);
	
	Optional<Product> findByProductName(String productName);
	
	void deleteByProductId(Long id);
	
    @Query("""
    	    SELECT p FROM Product p
    	    WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :productName, '%'))
    	""")
    	Page<Product> findByProductNameLike(@Param("productName") String productName, Pageable pageable);
    
    @Query("""
    	    SELECT p FROM Product p
    	    WHERE LOWER(p.productDescription) LIKE LOWER(CONCAT('%', :productDescription, '%'))
    	""")
    	Page<Product> findByProductDescriptionLike(@Param("productDescription") String productDescription, Pageable pageable);
    
    @Query("""
    	    SELECT p FROM Product p
    	    WHERE LOWER(p.casNumber) LIKE LOWER(CONCAT('%', :casNumber, '%'))
    	""")
    	Page<Product> findByCasNumberLike(@Param("casNumber") String casNumber, Pageable pageable);
	
    
    @Query("""
    	    SELECT p FROM Product p
    	    WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :productName, '%'))
    	    AND LOWER(p.productDescription) LIKE LOWER(CONCAT('%', :productDescription, '%'))
    	    AND LOWER(p.casNumber) LIKE LOWER(CONCAT('%', :casNumber, '%'))
    	""")
    	Page<Product> findByProductNameDesCasNumber(@Param("productName") String productName,
    	                       @Param("productDescription") String productDescription,
    	                       @Param("casNumber") String casNumber,
    	                       Pageable pageable);
    
}
