package com.belano;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Unit test for {@link com.belano.DistanceCalculator} class
 */
public class DistanceCalculatorTest {

	private static final String UNEXPECTED_DISTANCE = "Unexpected distance";

	@Test
	public void shouldParseGraphUponConstruction() {
		// arrange
		GraphParser parser = mock(GraphParser.class);
		String graph = "AB5, BC10";

		// act
		new DistanceCalculator(graph, parser);

		// assert
		verify(parser).parse(graph);
	}

	@Test
	public void shouldCalculateDistanceIterative() {
		// given
		AdjacencyList adjList = new AdjacencyList();
		adjList.put(TownKey.A, Arrays.asList(
				new NeighbourTown(TownKey.B, 5),
				new NeighbourTown(TownKey.D, 5),
				new NeighbourTown(TownKey.E, 7)
		));

		adjList.put(TownKey.B, Arrays.asList(
				new NeighbourTown(TownKey.C, 4)
		));

		adjList.put(TownKey.C, Arrays.asList(
				new NeighbourTown(TownKey.D, 8),
				new NeighbourTown(TownKey.E, 2)
		));

		adjList.put(TownKey.D, Arrays.asList(
				new NeighbourTown(TownKey.C, 8),
				new NeighbourTown(TownKey.E, 6)
		));

		adjList.put(TownKey.E, Arrays.asList(
				new NeighbourTown(TownKey.B, 3)
		));

		DistanceCalculator calculator = new DistanceCalculator(adjList);

		assertThat(UNEXPECTED_DISTANCE, calculator.getDistance("A-B-C"), is(9));
		assertThat(UNEXPECTED_DISTANCE, calculator.getDistance("A-D"), is(5));
		assertThat(UNEXPECTED_DISTANCE, calculator.getDistance("A-D-C"), is(13));
		assertThat(UNEXPECTED_DISTANCE, calculator.getDistance("A-E-B-C-D"), is(22));
		assertThat(UNEXPECTED_DISTANCE, calculator.getDistance("A-E-D"), is(0));
	}

	@Test
	public void shouldCalculateDistanceRecursive() {
		// given
		AdjacencyList adjList = new AdjacencyList();
		adjList.put(TownKey.A, Arrays.asList(
				new NeighbourTown(TownKey.B, 5),
				new NeighbourTown(TownKey.D, 5),
				new NeighbourTown(TownKey.E, 7)
		));

		adjList.put(TownKey.B, Arrays.asList(
				new NeighbourTown(TownKey.C, 4)
		));

		adjList.put(TownKey.C, Arrays.asList(
				new NeighbourTown(TownKey.D, 8),
				new NeighbourTown(TownKey.E, 2)
		));

		adjList.put(TownKey.D, Arrays.asList(
				new NeighbourTown(TownKey.C, 8),
				new NeighbourTown(TownKey.E, 6)
		));

		adjList.put(TownKey.E, Arrays.asList(
				new NeighbourTown(TownKey.B, 3)
		));

		DistanceCalculator calculator = new DistanceCalculator(new DistanceCalculator.RecursiveStrategy(adjList));

		assertThat(UNEXPECTED_DISTANCE, calculator.getDistance("A-B-C"), is(9));
		assertThat(UNEXPECTED_DISTANCE, calculator.getDistance("A-D"), is(5));
		assertThat(UNEXPECTED_DISTANCE, calculator.getDistance("A-D-C"), is(13));
		assertThat(UNEXPECTED_DISTANCE, calculator.getDistance("A-E-B-C-D"), is(22));
		assertThat(UNEXPECTED_DISTANCE, calculator.getDistance("A-E-D"), is(0));
	}

}
