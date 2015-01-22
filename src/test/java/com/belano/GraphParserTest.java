package com.belano;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Unit test for {@link com.belano.GraphParser} class
 */
public class GraphParserTest {

	private GraphParser parser;

	@Before
	public void setUp() {
		parser = new GraphParser();
	}

	@Test
	public void shouldParseValidRoutes() {
		// arrange
		String graph = "AB5, BC4, AC10";
		List<NeighbourTown> expectedA = Arrays.asList(
				new NeighbourTown(TownKey.B, 5),
				new NeighbourTown(TownKey.C, 10)
		);
		List<NeighbourTown> expectedB = Arrays.asList(
				new NeighbourTown(TownKey.C, 4)
		);

		// act
		Map<TownKey, List<NeighbourTown>> townKeyListMap = parser.parse(graph);

		// assert
		assertThat("Neighbour towns do not match", townKeyListMap.get(TownKey.A),
				is(expectedA));
		assertThat("Neighbour towns do not match", townKeyListMap.get(TownKey.B),
				is(expectedB));
		assertThat("Expected no towns", townKeyListMap.get(TownKey.C),
				is(nullValue()));
	}

	@Test(expected = com.belano.GraphParserException.class)
	public void shouldThrowExceptionWhenParsingNonValidRoute() {
		// arrange
		String graph = "XY5, WZ6";

		// act
		parser.parse(graph);
	}

}
