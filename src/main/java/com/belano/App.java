package com.belano;

import java.util.*;

/**
 * Main app
 *
 */
public class App 
{

    public static void main( String[] args )
    {
        System.out.println("--Trains exercise--");
		System.out.println("-------------------");
		System.out.println( "1-Graph definition (use format \"AB5\")" );
		DistanceCalculator calculator;
		while(true) {
			List<String> partsList;
			try {
				partsList = getGraphDefinition("Enter your graph definition (or \"done\" to finish graph): ");
				String[] partsArr = new String[partsList.size()];
				partsArr = partsList.toArray(partsArr);
				calculator = new DistanceCalculator(partsArr, new GraphParser());
			} catch (Exception e) {
				System.out.println("Could not instantiate distance calculator due to: " + e.getMessage());
				continue;
			}
			System.out.println("Entered graph definition: " + partsList);
			break;
		}

		System.out.println("-------------------");
		System.out.println("2-Calculate distance (use format \"A-B-C\")" );
		while (true) {
			Scanner input = new Scanner(System.in);
			System.out.print("Enter your route (or \"done\" to exit): ");
			try {
				String route = input.next();
				if ("done".equalsIgnoreCase(route)) {
					break;
				}
				if (!route.matches(DistanceCalculator.SEARCH_ROUTE_REGEX)) {
					System.out.println("Invalid route, try again");
					continue;
				}

				int distance = calculator.getDistance(route);
				if (distance == 0) {
					System.out.println("NO SUCH ROUTE");
				} else {
					System.out.println("distance: " + distance);
				}
			} catch (Exception e) {
				System.out.println("Could not calculate distance due to: " + e.getMessage());
			}
		}

		System.out.println("-------------------");
		System.out.println("--done--");
    }

	private static List<String> getGraphDefinition(String prompt) {
		List<String> parts = new ArrayList<>();
		Scanner input = new Scanner(System.in);
		while(true) {
			System.out.print(prompt);
			String part = input.next();
			if ("done".equalsIgnoreCase(part)) {
				break;
			}
			if (!part.matches(GraphParser.ROUTE_REGEX)) {
				System.out.println("Invalid route, try again");
				continue;
			}
			System.out.println("you entered: " + part);
			parts.add(part);
		}
		return parts;
	}

}
