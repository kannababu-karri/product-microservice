package com.restful.product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restful.product.entity.Product;
import com.restful.product.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductControllerDemo {
	
	@Autowired
	private ProductService productService;
	
	public ProductControllerDemo() {
		
	}
	
	@GetMapping
	public ResponseEntity<List<Product>> getAll() {
		
		List<Product> products = productService.findAllProducts();
		
		return ResponseEntity.ok(products);	
	}
	
	@PostMapping
	public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
		Product result = productService.saveOrUpdate(product);
		
		//return ResponseEntity.status(HttpStatus.CREATED).ok(result);
		
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<Product> getByProductName(@PathVariable String name) {
		Optional<Product> product = productService.findByProductName(name);
		
		return ResponseEntity.ok(product.get());
		
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
		productService.deleteByProductId(id);
		return ResponseEntity.ok("Product deleted successfully.");
	}

}
