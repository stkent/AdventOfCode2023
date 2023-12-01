@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package utils

import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min

data class GridPoint2d(val x: Int, val y: Int) {

    companion object {
        val origin = GridPoint2d(0, 0)
    }

    operator fun minus(vector: GridVector2d) = GridPoint2d(x - vector.x, y - vector.y)

    operator fun plus(vector: GridVector2d) = GridPoint2d(x + vector.x, y + vector.y)

    fun adjacentPoints(): Set<GridPoint2d> = setOf(
        copy(y = y + 1),
        copy(x = x + 1),
        copy(y = y - 1),
        copy(x = x - 1)
    )

    fun flipX(): GridPoint2d {
        return copy(x = -x)
    }

    fun flipY(): GridPoint2d {
        return copy(y = -y)
    }

    fun l1DistanceTo(other: GridPoint2d) = abs(x - other.x) + abs(y - other.y)

    fun l2DistanceTo(other: GridPoint2d) = hypot((x - other.x).toDouble(), (y - other.y).toDouble())

    fun lInfDistanceTo(other: GridPoint2d) = max(abs(x - other.x), abs(y - other.y))

    fun shiftBy(dx: Int = 0, dy: Int = 0): GridPoint2d {
        return copy(x = x + dx, y = y + dy)
    }

    fun surroundingPoints(): Set<GridPoint2d> = adjacentPoints().union(
        setOf(
            GridPoint2d(x + 1, y + 1),
            GridPoint2d(x + 1, y - 1),
            GridPoint2d(x - 1, y - 1),
            GridPoint2d(x - 1, y + 1)
        )
    )

    fun vectorTo(other: GridPoint2d): GridVector2d {
        return GridVector2d(other.x - x, other.y - y)
    }

}

data class GridBounds2d(val xMin: Int, val xMax: Int, val yMin: Int, val yMax: Int) {

    operator fun contains(point: GridPoint2d): Boolean {
        return point.x in xMin..xMax && point.y in yMin..yMax
    }

}

fun Iterable<GridPoint2d>.bounds(): GridBounds2d {
    var xMin = Int.MAX_VALUE
    var xMax = Int.MIN_VALUE
    var yMin = Int.MAX_VALUE
    var yMax = Int.MIN_VALUE

    val iterator = iterator()
    if (!iterator.hasNext()) {
        throw IllegalStateException("This method cannot be called on an empty Iterable.")
    }

    while (iterator.hasNext()) {
        val next = iterator.next()
        xMin = min(xMin, next.x)
        xMax = max(xMax, next.x)
        yMin = min(yMin, next.y)
        yMax = max(yMax, next.y)
    }

    return GridBounds2d(
        xMin = xMin,
        xMax = xMax,
        yMin = yMin,
        yMax = yMax
    )
}
