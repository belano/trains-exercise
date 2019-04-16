package com.belano;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses text graph
 */
public class GraphParser {

	final Logger logger = LoggerFactory.getLogger(GraphParser.class);

	// not so bulletproof regex
	public static final String ROUTE_REGEX = "([ABCDE])([ABCDE])(\\d+)";
	public static final Pattern PATTERN = Pattern.compile(ROUTE_REGEX);

	/**
	 * Parses array graph to obtain the adjacency list
	 * @param routes
	 * @return map based adjacency list
	 */
	public AdjacencyList parse(String[] routes) {
		AdjacencyList adjList = new AdjacencyList();
		for(String route : routes) {
			String currentRoute = route.trim();
			if (currentRoute.matches(ROUTE_REGEX)) {
				logger.debug("route: {}", currentRoute);
				Matcher m = PATTERN.matcher(currentRoute);
				if (m.find()) {
					// split into regex groups
					TownKey source = TownKey.convert(m.group(1));
					TownKey destination = TownKey.convert(m.group(2));
					int distance = Integer.parseInt(m.group(3));
					adjList.addNeighbourTown(source, destination, distance);
				}
			} else {
				throw new GraphParserException("Not valid route found [" + currentRoute + "]");
			}
		}
		return adjList;
	}

	/**
	 * Parses comma-separated text graph to obtain the adjacency list
	 * @param graph
	 * @return map based adjacency list
	 */
	public AdjacencyList parse(String graph) {
		return parse(graph.split(","));
	}
}
