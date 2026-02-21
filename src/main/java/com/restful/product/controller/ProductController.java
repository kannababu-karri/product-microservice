package com.restful.product.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restful.product.entity.PageResponseDto;
import com.restful.product.entity.Product;
import com.restful.product.exception.InvalidProductException;
import com.restful.product.exception.ProductNotFoundException;
import com.restful.product.exception.ServiceException;
import com.restful.product.service.ProductService;
import com.restful.product.utils.ILConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = {ILConstants.ANGULAR_URL_DEV, ILConstants.ANGULAR_URL_PROD})
public class ProductController {

	private static final Logger _LOGGER = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
    private ProductService productService;
    
    public ProductController() {
    	_LOGGER.info(">>> ProductController LOADED. <<<");
    }
    
    @PostMapping({ "", "/" })
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {

        _LOGGER.info(">>> Inside createProduct. <<<");

        Product saved = productService.saveOrUpdate(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id,
                                               @RequestBody Product product) {
    	product.setProductId(id);
    	Product updated = productService.update(product);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
		_LOGGER.info(">>> Inside deleteProduct. <<<");		
		
		if (id == null || id <= 0) {
			throw new InvalidProductException("Product id must not be empty");
		}
		
		Map<String, String> response = new HashMap<>();
		
		try {
			productService.deleteByProductId(id);
			//return ResponseEntity.ok("Product deleted successfully.");
			
			response.put("message", "Product deleted successfully.");
			
		    return ResponseEntity.ok(response);
		    
	    } catch (InvalidProductException ex) {
	    	_LOGGER.error("Error deleting product InvalidProductException with id {}: {}", id, ex.getMessage());
	        response.put("status", HttpStatus.NOT_FOUND.toString());
	        response.put("message", "Product not found with id: " + id);
	        return ResponseEntity
	                .status(HttpStatus.NOT_FOUND)
	                .body(response);
	    } catch (Exception ex) {
	        _LOGGER.error("Error deleting product Exception with id {}: {}", id, ex.getMessage());
	        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.toString());
	        response.put("message", "Unexpected error occurred: " + ex.getMessage());
	        return ResponseEntity
	                .status(HttpStatus.NOT_FOUND)
	                .body(response);
	    }
	}
    
    @GetMapping({"", "/"})
    public ResponseEntity<PageResponseDto<Product>> getAll(
    		@PageableDefault(size = 5, sort = "productName")
    	    Pageable pageable) {
    	_LOGGER.info(">>> Inside getAll. <<<");
    	
    	Page<Product> page = productService.findAllProducts(pageable);
    	
    	PageResponseDto<Product> dto = new PageResponseDto<>();
    	
    	if(page != null) {
	    	dto.setContent(page.getContent());
	        dto.setTotalPages(page.getTotalPages());
	        dto.setTotalElements(page.getTotalElements());
	        dto.setPageNumber(page.getNumber());
	        dto.setPageSize(page.getSize());
    	}
    	
    	if (page != null && page.isEmpty()) {
            //throw new ProductNotFoundException("No products found");
        }

    	return ResponseEntity.ok(dto);
    	
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
		
		String decodedProductName = URLDecoder.decode(productName, StandardCharsets.UTF_8);
		
		Product product = productService.findByProductName(decodedProductName)
				 .orElseThrow(() -> new ProductNotFoundException("Product not found with productName: " + decodedProductName));
		return ResponseEntity.ok(product);
	}
    
    //@GetMapping("/search/productName/{name}")
    @GetMapping({"/search/productName", "/search/productName/{name}"})
    public ResponseEntity<PageResponseDto<Product>> getByProductName(@PathVariable String name,
    		@PageableDefault(size = 5, sort = "productName")
			Pageable pageable) {
    	_LOGGER.info(">>> Inside getByName. <<<");
    	
    	if (name == null || name.isBlank()) {
            throw new InvalidProductException("Product name like must not be empty");
        }
    	
    	String decodedName = URLDecoder.decode(name, StandardCharsets.UTF_8);
    	
    	Page<Product> page = productService.findByProductNameLike(decodedName, pageable);
    	
    	PageResponseDto<Product> dto = new PageResponseDto<>();
    	
    	if(page != null) {
	    	dto.setContent(page.getContent());
	        dto.setTotalPages(page.getTotalPages());
	        dto.setTotalElements(page.getTotalElements());
	        dto.setPageNumber(page.getNumber());
	        dto.setPageSize(page.getSize());
    	}
    	
    	if (page != null && page.isEmpty()) {
            //throw new ProductNotFoundException("No products found for name: "+name);
        }

        //return ResponseEntity.ok(products);
    	return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    //@GetMapping("/search/description/{description}")
    @GetMapping({"/search/description", "/search/description/{description}"})
    public ResponseEntity<PageResponseDto<Product>> getByDescription(@PathVariable String description,
    		@PageableDefault(size = 5, sort = "productName")
			Pageable pageable) {
    	_LOGGER.info(">>> Inside getByDescription. description<<<"+description);
    	
    	if (description == null || description.isBlank()) {
            throw new InvalidProductException("Product description like must not be empty");
        }
    	
    	String decodedDescription = URLDecoder.decode(description, StandardCharsets.UTF_8);
    	
    	Page<Product> page = productService.findByProductDescriptionLike(decodedDescription, pageable);
    	
    	PageResponseDto<Product> dto = new PageResponseDto<>();
    	
    	if(page != null) {
	    	dto.setContent(page.getContent());
	        dto.setTotalPages(page.getTotalPages());
	        dto.setTotalElements(page.getTotalElements());
	        dto.setPageNumber(page.getNumber());
	        dto.setPageSize(page.getSize());
    	}
    	
    	if (page != null && page.isEmpty()) {
            //throw new ProductNotFoundException("No products found for description: "+description);
        }

        //return ResponseEntity.ok(products);
    	return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    //@GetMapping("/search/cas/{casNumber}")
    @GetMapping({"/search/cas", "/search/cas/{casNumber}"})
    public ResponseEntity<PageResponseDto<Product>> getByCasNumber(@PathVariable String casNumber,
    		@PageableDefault(size = 5, sort = "productName")
			Pageable pageable) {
    	_LOGGER.info(">>> Inside getByCasNumber. casNumber <<<"+casNumber);
    	
    	if (casNumber == null || casNumber.isBlank()) {
            throw new InvalidProductException("Product cas number like must not be empty");
        }
    	
    	String decodedCasNumber = URLDecoder.decode(casNumber, StandardCharsets.UTF_8);
    	
    	_LOGGER.info(">>> Inside getByCasNumber. decodedCasNumber <<<"+decodedCasNumber);
    	
    	Page<Product> page = productService.findByCasNumberLike(decodedCasNumber, pageable);
    	
    	PageResponseDto<Product> dto = new PageResponseDto<>();
    	
    	if(page != null) {
	    	dto.setContent(page.getContent());
	        dto.setTotalPages(page.getTotalPages());
	        dto.setTotalElements(page.getTotalElements());
	        dto.setPageNumber(page.getNumber());
	        dto.setPageSize(page.getSize());
    	}
    	
    	if (page != null && page.isEmpty()) {
            //throw new ProductNotFoundException("No products found for cas number: "+casNumber);
        }

        //return ResponseEntity.ok(products);
    	return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    // GET products by multiple fields
    @GetMapping("/search")
    public ResponseEntity<PageResponseDto<Product>> getByMultipleFields(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String casNumber,
            @PageableDefault(size = 5, sort = "productName")
			Pageable pageable) {
    	
    	_LOGGER.info(">>> Inside getByMultipleFields. name <<<"+name);
    	_LOGGER.info(">>> Inside getByMultipleFields. description <<<"+description);
    	_LOGGER.info(">>> Inside getByMultipleFields. casNumber <<<"+casNumber);
    	
        try {
        	
        	String decodedName = URLDecoder.decode(name, StandardCharsets.UTF_8);
        	String decodedDescription = URLDecoder.decode(description, StandardCharsets.UTF_8);
        	String decodedCasNumber = URLDecoder.decode(casNumber, StandardCharsets.UTF_8);
        	
        	_LOGGER.info(">>> Inside getByMultipleFields. decodedName <<<"+decodedName);
        	_LOGGER.info(">>> Inside getByMultipleFields. decodedDescription <<<"+decodedDescription);
        	_LOGGER.info(">>> Inside getByMultipleFields. decodedCasNumber <<<"+decodedCasNumber);
        	
        	Page<Product> page = productService.findByProductNameDesCasNumber(decodedName, decodedDescription, decodedCasNumber, pageable);
        	
        	PageResponseDto<Product> dto = new PageResponseDto<>();
        	
        	if(page != null) {
    	    	dto.setContent(page.getContent());
    	        dto.setTotalPages(page.getTotalPages());
    	        dto.setTotalElements(page.getTotalElements());
    	        dto.setPageNumber(page.getNumber());
    	        dto.setPageSize(page.getSize());
        	}
            //return ResponseEntity.ok(products);
        	return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (ServiceException ex) {
            _LOGGER.error("Error fetching products by multiple fields: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    

	
}
