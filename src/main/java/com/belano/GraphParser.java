package com.belano;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses text graph
 */
public class GraphParser {

	final static Logger logger = LoggerFactory.getLogger(GraphParser.class);

	// not so bulletproof regex
	public final static String routeRegex = "([ABCDE])([ABCDE])(\\d+)";
	public final static Pattern pattern = Pattern.compile(routeRegex);

	/**
	 * Parses array graph to obtain the adjacency list
	 * @param routes
	 * @return map based adjacency list
	 */
	public Map<TownKey, List<NeighbourTown>> parse(String[] routes) {
		Map<TownKey, List<NeighbourTown>> adjList =
				new HashMap<TownKey, List<NeighbourTown>>();
		for(String route : routes) {
			String currentRoute = route.trim();
			if (currentRoute.matches(routeRegex)) {
				logger.debug("route: [" + currentRoute + "]");
				Matcher m = pattern.matcher(currentRoute);
				if (m.find()) {
					// split into regex groups
					TownKey source = TownKey.convert(m.group(1));
					TownKey dest = TownKey.convert(m.group(2));
					int distance = Integer.valueOf(m.group(3));
					if (adjList.get(source) != null) {
						List<NeighbourTown> neighbourTowns = adjList.get(source);
						neighbourTowns.add(new NeighbourTown(dest, distance));
					} else {
						// needs concrete implementation as list is not immutable
						adjList.put(source, new ArrayList<NeighbourTown>(
								Arrays.asList(new NeighbourTown(dest, distance))));
					}
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
	public Map<TownKey, List<NeighbourTown>> parse(String graph) {
		return parse(graph.split("\\,"));
	}
}
