package com.belano;

/**
 * Custom exception
 */
public class InvalidTownKeyException extends IllegalArgumentException {

	public InvalidTownKeyException(String message) {
		super(message);
	}
}
