package com.belano;

import java.util.Objects;

/**
 * Stores town key and weight (distance)
 */
public class NeighbourTown {

	private TownKey key;
	private int distance;

	public NeighbourTown(TownKey key, int distance) {
		this.key = key;
		this.distance = distance;
	}

	public TownKey getKey() {
		return key;
	}

	public int getDistance() {
		return distance;
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, distance);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof NeighbourTown)) {
			return false;
		}

		if (this == o) {
			return true;
		}

		NeighbourTown other = (NeighbourTown) o;
		return this.getKey().equals(other.getKey())
				&& this.getDistance() == other.getDistance();
	}
}
