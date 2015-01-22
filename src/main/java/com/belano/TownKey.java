package com.belano;

/**
 * Holds town key constants
 */
public enum TownKey {
	A, B, C, D, E;

	public static boolean isKeyValid(String key) {
		for (TownKey k : TownKey.values()) {
			if (k.name().equals(key)) {
				return true;
			}
		}
		return false;
	}

	public static TownKey convert(String key) {
		if (isKeyValid(key)) {
			return TownKey.valueOf(key);
		}
		throw new InvalidTownKeyException("No valid town key [" + key + "]");
	}
}
