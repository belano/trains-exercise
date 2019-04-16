package com.belano;

import java.util.*;

public class AdjacencyList {

    /** pseudo adjacency list based on a map implementation */
    private Map<TownKey, List<NeighbourTown>> adjList =
            new EnumMap<>(TownKey.class);

    public void addNeighbourTown(TownKey source, TownKey destination, int distance) {
        if (adjList.get(source) != null) {
            List<NeighbourTown> neighbourTowns = adjList.get(source);
            neighbourTowns.add(new NeighbourTown(destination, distance));
        } else {
            // needs concrete implementation as list is not immutable
            adjList.put(source, new ArrayList<>(
                    Collections.singletonList(new NeighbourTown(destination, distance))));
        }
    }

    public List<NeighbourTown> get(TownKey current) {
        return adjList.get(current);
    }

    public void put(TownKey key, List<NeighbourTown> towns) {
        adjList.put(key, towns);
    }
}
