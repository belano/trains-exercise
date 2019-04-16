package com.belano;

import java.util.Arrays;

/**
 * Holds town key constants
 */
public enum TownKey {
	A, B, C, D, E;

	public static boolean isKeyValid(String key) {
		return Arrays.stream(TownKey.values()).anyMatch(k -> k.name().equals(key));
	}

	public static TownKey convert(String key) {
		if (isKeyValid(key)) {
			return TownKey.valueOf(key);
		}
		throw new InvalidTownKeyException("No valid town key [" + key + "]");
	}
}
