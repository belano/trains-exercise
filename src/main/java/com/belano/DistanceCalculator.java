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
	Map<TownKey, List<NeighbourTown>> adjList;

	public DistanceCalculator(
			Map<TownKey, List<NeighbourTown>> adjList) {
		this.adjList = adjList;
	}

	public DistanceCalculator(String graph, GraphParser parser) {
		this.adjList = parser.parse(graph);
	}

	public DistanceCalculator(String[] graph, GraphParser parser) {
		this.adjList = parser.parse(graph);
	}

	/**
	 * Gets distance from route
	 * @param textRoute
	 * @return computed distance or 0 if no such route
	 */
	public int getDistance(String textRoute) {
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
				return 0;
			}
			current = target;
		}
		return totalDistance;
	}
}
