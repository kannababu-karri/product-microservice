package com.restful.product.exception;

public class InvalidProductException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidProductException(String message) {
        super(message);
    }
}
