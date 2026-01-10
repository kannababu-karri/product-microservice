package com.restful.product.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.restful.product.entity.Product;
import com.restful.product.exception.InvalidProductException;
import com.restful.product.exception.ProductNotFoundException;
import com.restful.product.exception.ServiceException;
import com.restful.product.service.ProductService;

@Controller
@RequestMapping("/api/product")
public class ProductController {

	private static final Logger _LOGGER = LoggerFactory.getLogger(ProductController.class);
	
    @Autowired
    private ProductService productService;
    
    public ProductController() {
    	_LOGGER.info(">>> ProductController LOADED. <<<");
    }
    
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
    	_LOGGER.info(">>> Inside createProduct. <<<");
        if (product.getProductName() == null || product.getProductName().isBlank()) {
            throw new InvalidProductException("Product name must not be empty");
        }

        Product saved = productService.saveOrUpdate(product);
        
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		_LOGGER.info(">>> Inside deleteProduct. <<<");		
		
		if (id == null || id <= 0) {
			throw new InvalidProductException("Product id must not be empty");
		}
		
		try {
			productService.deleteByProductId(id);
			return ResponseEntity.ok("Product deleted successfully.");		
	    } catch (InvalidProductException ex) {
	        // ID not found in DB
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Product not found with id: " + id);
	    } catch (Exception ex) {
	        _LOGGER.error("Error deleting product with id {}: {}", id, ex.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Unexpected error occurred: " + ex.getMessage());
	    }
		
	}
    
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
    	_LOGGER.info(">>> Inside getAll. <<<");
    	List<Product> products = productService.findAllProducts();
    	
    	if (products.isEmpty()) {
            //throw new ProductNotFoundException("No products found");
        }

        return ResponseEntity.ok(products);
    	
    }
    
    @GetMapping("/id/{productId}")
	public ResponseEntity<Product> getById(@PathVariable Long productId) {
    	_LOGGER.info(">>> Inside getById. <<<");
    	
    	if (productId == null || (productId != null && productId.intValue() <= 0)) {
			throw new InvalidProductException("Product id must not be empty");
		}
    	
    	Product product = productService.findByProductId(productId)
				 .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
		return ResponseEntity.ok(product);
    }
    
    @GetMapping("/name/{productName}")
	public ResponseEntity<Product> getByName(@PathVariable String productName) {
		_LOGGER.info(">>> Inside getByName. <<<");	
		
		if (productName == null || productName.isBlank()) {
            throw new InvalidProductException("Product name must not be empty");
        }
		
		Product product = productService.findByProductName(productName)
				 .orElseThrow(() -> new ProductNotFoundException("Product not found with productName: " + productName));
		return ResponseEntity.ok(product);
	}
    
    @GetMapping("/search/productName/{name}")
    public ResponseEntity<List<Product>> getByProductName(@PathVariable String name) {
    	_LOGGER.info(">>> Inside getByName. <<<");
    	
    	if (name == null || name.isBlank()) {
            throw new InvalidProductException("Product name like must not be empty");
        }
    	
		List<Product> products = productService.findByProductNameLike(name);
    	
    	if (products.isEmpty()) {
            //throw new ProductNotFoundException("No products found for name: "+name);
        }

        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/search/description/{description}")
    public ResponseEntity<List<Product>> getByDescription(@PathVariable String description) {
    	_LOGGER.info(">>> Inside getByDescription. <<<");
    	
    	if (description == null || description.isBlank()) {
            throw new InvalidProductException("Product description like must not be empty");
        }
    	
		List<Product> products = productService.findByProductDescriptionLike(description);
    	
    	if (products.isEmpty()) {
            //throw new ProductNotFoundException("No products found for description: "+description);
        }

        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/search/cas/{casNumber}")
    public ResponseEntity<List<Product>> getByCasNumber(@PathVariable String casNumber) {
    	_LOGGER.info(">>> Inside getByCasNumber. <<<");
    	
    	if (casNumber == null || casNumber.isBlank()) {
            throw new InvalidProductException("Product cas number like must not be empty");
        }
    	
		List<Product> products = productService.findByCasNumberLike(casNumber);
    	
    	if (products.isEmpty()) {
            //throw new ProductNotFoundException("No products found for cas number: "+casNumber);
        }

        return ResponseEntity.ok(products);
    }
    
    // GET products by multiple fields
    @GetMapping("/search")
    public ResponseEntity<List<Product>> getByMultipleFields(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String casNumber) {
        try {
            List<Product> products = productService.findByProductNameDesCanNumber(name, description, casNumber);
            return ResponseEntity.ok(products);
        } catch (ServiceException ex) {
            _LOGGER.error("Error fetching products by multiple fields: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    

	
}
