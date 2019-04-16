package com.belano;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Distance calculator
 */
public class DistanceCalculator {
	private static final Logger logger = LoggerFactory.getLogger(DistanceCalculator.class);

	public static final String SEARCH_ROUTE_REGEX = "^([ABCDE])(-[ABCDE])+$";


	/** distance calculator algorithm strategy **/
	private AlgorithmStrategy algorithmStrategy;

	public DistanceCalculator(
			AdjacencyList adjList) {
		this.algorithmStrategy = new IterativeStrategy(adjList);	// default
	}

	public DistanceCalculator(AlgorithmStrategy strategy) {
		this.algorithmStrategy = strategy;
	}

	public DistanceCalculator(String graph, GraphParser parser) {
		this(parser.parse(graph));
	}

	public DistanceCalculator(String[] graph, GraphParser parser) {
		this(parser.parse(graph));
	}

	/**
	 * Gets distance from route
	 * @param textRoute
	 * @return computed distance or 0 if no such route
	 */
	public int getDistance(String textRoute) {
		return algorithmStrategy.calculate(textRoute);
	}


	static class RecursiveStrategy implements AlgorithmStrategy {

		private AdjacencyList adjList;

		public RecursiveStrategy(AdjacencyList adjList) {
			this.adjList = adjList;
		}

		@Override public int calculate(String textRoute) {
			if (!textRoute.matches(SEARCH_ROUTE_REGEX)) {
				throw new InvalidRouteException("Invalid route: [" + textRoute + "]");
			}
			String[] route = textRoute.split("-");
			int acc = 0;
			int i = 0;
			int j = 1;
			return calculateAcc(route, acc, i, j);
		}

		private int calculateAcc(String[] route, int acc, int i, int j) {
			if (route.length == j) {
				return acc;
			}
			TownKey currentTown = TownKey.convert(route[i]);
			TownKey targetTown = TownKey.convert(route[j]);
			List<NeighbourTown> neighbourTowns = adjList.get(currentTown);
			for (NeighbourTown neighbourTown : neighbourTowns) {
				if (neighbourTown.getKey().equals(targetTown)) {
					logger.debug("i {} j {} total {} distance {}",
							i, j, acc, neighbourTown.getDistance());
					return calculateAcc(route, acc + neighbourTown.getDistance(), ++i,
							++j);
				}
			}
			logger.debug("no route found from {} to {}, returning 0", currentTown, targetTown);
			return 0;
		}
	}

	static class IterativeStrategy implements AlgorithmStrategy {

		private AdjacencyList adjList;

		public IterativeStrategy(AdjacencyList adjList) {
			this.adjList = adjList;
		}

		@Override public int calculate(String textRoute) {
			if (!textRoute.matches(SEARCH_ROUTE_REGEX)) {
				throw new InvalidRouteException("Invalid route: [" + textRoute + "]");
			}
			int totalDistance = 0;
			StringTokenizer st = new StringTokenizer(textRoute, "-");
			TownKey currentTown = TownKey.convert(st.nextToken());
			while (st.hasMoreTokens()) {
				List<NeighbourTown> neighbourTowns = adjList.get(currentTown);
				TownKey targetTown = TownKey.convert(st.nextToken());
				boolean found = false;
				for (NeighbourTown neighbourTown : neighbourTowns) {
					if (neighbourTown.getKey().equals(targetTown)) {
						logger.debug("Adding {} to currentTown total distance {}",
								neighbourTown.getDistance(), totalDistance);
						totalDistance += neighbourTown.getDistance();
						found = true;
						break;
					}
				}
				if (!found) {
					logger.debug("no route found from {} to {}, returning 0", currentTown, targetTown);
					return 0;
				}
				currentTown = targetTown;
			}
			return totalDistance;
		}
	}

}
