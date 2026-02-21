package com.restful.product.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restful.product.entity.Product;
import com.restful.product.exception.InvalidProductException;
import com.restful.product.exception.ServiceException;
import com.restful.product.repository.ProductRepository;

@Service
public class ProductService {
	private static final Logger _LOGGER = LoggerFactory.getLogger(ProductService.class);
	
	@Autowired
	private ProductRepository productRepository;

	@Transactional
	public Product saveOrUpdate(Product product) {

	    if (productRepository.findByProductName(product.getProductName()).isPresent()) {
	        throw new InvalidProductException("Product name already exists!!!");
	    }

	    try {
	        return productRepository.save(product);
	    } catch (DataIntegrityViolationException e) {
	        throw new InvalidProductException("Product name already exists!!!");
	    } catch (Exception e) {
	        _LOGGER.error("saveOrUpdate failed", e);
	        throw new ServiceException("Server error while saving product");
	    }
	}
	
	@Transactional
    public Product update(Product product) throws ServiceException {
    	try {
    		Product existing =
    				productRepository.findById(product.getProductId())
    		        .orElseThrow(() -> new RuntimeException("Product not found"));
    		
    		//Check existing and db product name.
    		if(!product.getProductName().equalsIgnoreCase(existing.getProductName())) {
    			//Check product name already exists.
    			if (productRepository.findByProductName(product.getProductName()).isPresent()) {
    		        throw new InvalidProductException("Product name already exists!!!");
    		    }
    		}

		    existing.setProductName(product.getProductName());
		    existing.setProductDescription(product.getProductDescription());
		    existing.setCasNumber(product.getCasNumber());

		    return productRepository.save(product);
	    } catch (DataIntegrityViolationException e) {
	        throw new InvalidProductException("Product name already exists!!!");
	    } catch (Exception e) {
	        _LOGGER.error("saveOrUpdate failed", e);
	        throw new ServiceException("Server error while saving product");
	    }
    }
	
	@Transactional
	public void deleteByProductId(Long productId) throws ServiceException {
		try {
			productRepository.deleteByProductId(productId);
		} catch (Exception exp) {
			_LOGGER.error("ERROR: Service Exception occured in deleteByProductId."+exp.toString());	
			throw new ServiceException("ERROR: Service Exception occured in deleteByProductId."+exp.toString());
		}
	}

	public Page<Product> findAllProducts(Pageable pageable) throws ServiceException {
		try {
			return productRepository.findAll(pageable);
		} catch (Exception exp) {
			_LOGGER.error("ERROR: Service Exception occured in findAllProducts."+exp.toString());	
			throw new ServiceException("ERROR: Service Exception occured in findAllProducts."+exp.toString());
		}
	}
	
	public Optional<Product> findByProductName(String productName) throws ServiceException {
		try {
	    	return productRepository.findByProductName(productName);
		} catch (Exception exp) {
			_LOGGER.error("ERROR: Service Exception occured in findByProductName."+exp.toString());	
			throw new ServiceException("ERROR: Service Exception occured in findByProductName."+exp.toString());
		}
    }
	
    public Page<Product> findByProductNameLike(String productName, Pageable pageable) throws ServiceException {
    	try {
    		return productRepository.findByProductNameLike(productName, pageable);
	    } catch (Exception exp) {
			_LOGGER.error("ERROR: Service Exception occured in findByProductNameLike."+exp.toString());	
			throw new ServiceException("ERROR: Service Exception occured in findByProductNameLike."+exp.toString());
		}
    }
    
    public Page<Product> findByProductDescriptionLike(String productDescription, Pageable pageable) throws ServiceException {
    	try {
	    	return productRepository.findByProductDescriptionLike(productDescription, pageable);
	    } catch (Exception exp) {
			_LOGGER.error("ERROR: Service Exception occured in findByProductDescriptionLike."+exp.toString());	
			throw new ServiceException("ERROR: Service Exception occured in findByProductDescriptionLike."+exp.toString());
		}
    }
    
    public Page<Product> findByCasNumberLike(String casNumber, Pageable pageable) throws ServiceException {
    	try {
	    	return productRepository.findByCasNumberLike(casNumber, pageable);
	    } catch (Exception exp) {
			_LOGGER.error("ERROR: Service Exception occured in findByCasNumberLike."+exp.toString());	
			throw new ServiceException("ERROR: Service Exception occured in findByCasNumberLike."+exp.toString());
		}
    }
    
    public Page<Product> findByProductNameDesCasNumber(String productName, 
    													String productDescription, 
    													String casNumber,
    													Pageable pageable) throws ServiceException {
    	try {
	    	return productRepository.findByProductNameDesCasNumber(productName, productDescription, casNumber, pageable);
	    } catch (Exception exp) {
			_LOGGER.error("ERROR: Service Exception occured in findByProductNameDesCanNumber."+exp.toString());	
			throw new ServiceException("ERROR: Service Exception occured in findByProductNameDesCanNumber."+exp.toString());
		}
    }
    
	public Optional<Product> findByProductId(Long productId) throws ServiceException {
		try {
	        return productRepository
	                .findByProductId(productId);
		} catch (Exception exp) {
			_LOGGER.error("ERROR: Service Exception occured in findByProductId."+exp.toString());	
			throw new ServiceException("ERROR: Service Exception occured in findByProductId."+exp.toString());
		}
    }
	
}
