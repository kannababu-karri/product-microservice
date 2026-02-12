package com.restful.product.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restful.product.entity.Product;
import com.restful.product.exception.ServiceException;
import com.restful.product.repository.ProductRepository;

@Service
public class ProductService {
	private static final Logger _LOGGER = LoggerFactory.getLogger(ProductService.class);
	
	@Autowired
	private ProductRepository productRepository;

	@Transactional
	public Product saveOrUpdate(Product product) throws ServiceException {
		try {
			return productRepository.save(product);
		} catch (Exception exp) {
			_LOGGER.error("ERROR: Service Exception occured in saveOrUpdate."+exp.toString());	
			throw new ServiceException("ERROR: Service Exception occured in saveOrUpdate."+exp.toString());
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
    
    public Page<Product> findByProductNameDesCanNumber(String productName, 
    													String productDescription, 
    													String casNumber,
    													Pageable pageable) throws ServiceException {
    	try {
	    	return productRepository.findByProductNameDesCanNumber(productName, productDescription, casNumber, pageable);
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
