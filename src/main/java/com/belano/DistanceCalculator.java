package com.belano;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Distance calculator
 */
public class DistanceCalculator {
	final static Logger logger = LoggerFactory.getLogger(DistanceCalculator.class);

	public final static String searchRouteRegex = "^([ABCDE])(-[ABCDE])+$";

	/** pseudo adjacency list based on a map implementation */
	private static Map<TownKey, List<NeighbourTown>> adjList;

	/** distance calculator algorithm strategy **/
	private AlgorithmStrategy algorithmStrategy;

	public DistanceCalculator(
			Map<TownKey, List<NeighbourTown>> adjList) {
		this.adjList = adjList;
		this.algorithmStrategy = new IterativeStrategy();	// default
	}

	public DistanceCalculator(
			Map<TownKey, List<NeighbourTown>> adjList, AlgorithmStrategy strategy) {
		this.adjList = adjList;
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

		@Override public int calculate(String textRoute) {
			if (!textRoute.matches(searchRouteRegex)) {
				throw new InvalidRouteException("Invalid route: [" + textRoute + "]");
			}
			String[] route = textRoute.split("-");
			int acc = 0, i = 0, j = 1;
			return calculateAcc(route, acc, i, j);
		}

		private int calculateAcc(String[] route, int acc, int i, int j) {
			if (route.length == j) {
				return acc;
			}
			TownKey current = TownKey.convert(route[i]);
			TownKey target = TownKey.convert(route[j]);
			List<NeighbourTown> adj = adjList.get(current);
			for (NeighbourTown neighbourTown : adj) {
				if (neighbourTown.getKey().equals(target)) {
					logger.debug("i {} j {} total {} distance {}",
							i, j, acc, neighbourTown.getDistance());
					return calculateAcc(route, acc + neighbourTown.getDistance(), ++i,
							++j);
				}
			}
			logger.debug("no route found from {} to {}, returning 0", current, target);
			return 0;
		}
	}

	static class IterativeStrategy implements AlgorithmStrategy {

		@Override public int calculate(String textRoute) {
			if (!textRoute.matches(searchRouteRegex)) {
				throw new InvalidRouteException("Invalid route: [" + textRoute + "]");
			}
			int totalDistance = 0;
			StringTokenizer st = new StringTokenizer(textRoute, "-");
			TownKey current = TownKey.convert(st.nextToken());
			while (st.hasMoreTokens()) {
				List<NeighbourTown> adj = adjList.get(current);
				TownKey target = TownKey.convert(st.nextToken());
				boolean found = false;
				for (NeighbourTown neighbourTown : adj) {
					if (neighbourTown.getKey().equals(target)) {
						logger.debug("Adding {} to current total distance {}",
								neighbourTown.getDistance(), totalDistance);
						totalDistance += neighbourTown.getDistance();
						found = true;
						break;
					}
				}
				if (!found) {
					logger.debug("no route found from {} to {}, returning 0", current, target);
					return 0;
				}
				current = target;
			}
			return totalDistance;
		}
	}

}
