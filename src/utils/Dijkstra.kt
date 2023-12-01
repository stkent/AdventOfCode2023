@file:Suppress("unused", "MemberVisibilityCanBePrivate", "DuplicatedCode")

package utils

import kotlin.math.min

// Based on https://en.wikipedia.org/w/index.php?title=Dijkstra%27s_algorithm&oldid=931177808
fun <T> T.shortestDistanceMap(neighbors: T.() -> Map<T, Int>): Map<T, Int> {
    val unvisited = mutableMapOf<T, Int>()
        .withDefault { Int.MAX_VALUE }
        .also { it[this] = 0 }

    val visited = mutableMapOf<T, Int>()

    while (unvisited.isNotEmpty()) {
        val (currentNode, currentPathLength) = unvisited.minByOrNull { (_, pathLength) -> pathLength }!!
        unvisited -= currentNode

        currentNode
            .neighbors()
            .filterNot { (neighborNode, _) -> neighborNode in visited }
            .mapValues { (_, edgeLength) -> currentPathLength + edgeLength }
            .forEach { (neighborNode, neighborPathLength) ->
                unvisited[neighborNode] = min(neighborPathLength, unvisited.getValue(neighborNode))
            }

        visited += currentNode to currentPathLength
    }

    return visited
}

// Based on https://en.wikipedia.org/w/index.php?title=Dijkstra%27s_algorithm&oldid=931177808
fun <T> T.shortestDistanceTo(target: T, neighbors: T.() -> Map<T, Int>): Int? {
    val unvisited = mutableMapOf<T, Int>()
        .withDefault { Int.MAX_VALUE }
        .also { it[this] = 0 }

    val visited = mutableMapOf<T, Int>()

    while (unvisited.isNotEmpty()) {
        val (currentNode, currentPathLength) = unvisited.minByOrNull { (_, pathLength) -> pathLength }!!
        unvisited.getValue(target).let { if (it == currentPathLength) return it }
        unvisited -= currentNode

        currentNode
            .neighbors()
            .filterNot { (neighborNode, _) -> neighborNode in visited }
            .mapValues { (_, edgeLength) -> currentPathLength + edgeLength }
            .forEach { (neighborNode, neighborPathLength) ->
                unvisited[neighborNode] = min(neighborPathLength, unvisited.getValue(neighborNode))
            }

        visited += currentNode to currentPathLength
    }

    return null
}
