package com.belano;

/**
 * Custom exception
 */
public class InvalidRouteException extends IllegalArgumentException {
	public InvalidRouteException(String message) {
		super(message);
	}
}
