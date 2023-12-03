import utils.GridPoint2d
import utils.extensions.intersects
import utils.readInput

data class Number(
    val value: Int,
    val startPoint: GridPoint2d,
) {
    val allPoints: Set<GridPoint2d> =
        buildSet {
            repeat(value.toString().length) { index ->
                add(startPoint.shiftBy(dx = index))
            }
        }

    val neighbors: Set<GridPoint2d> =
        buildSet {
            allPoints.forEach { point ->
                addAll(point.surroundingPoints())
            }

            removeAll(allPoints)
        }
}

data class Symbol(val point: GridPoint2d, val mayBeGear: Boolean)

fun main() {
fun Char.isSymbol() = this != '.'
fun Char.mayBeGear() = this == '*'

fun List<String>.parseSchematic(): Pair<Set<Number>, Set<Symbol>> {
    val numbers = mutableSetOf<Number>()
    val symbols = mutableSetOf<Symbol>()

    var value: Int? = null
    var startPoint: GridPoint2d? = null

    fun recordNumber() {
        numbers += Number(value!!, startPoint!!)
        value = null
        startPoint = null
    }

    val width = get(0).length
    for (y in 0..lastIndex) {
        for (x in 0..<width) {
            val point = GridPoint2d(x, y)
            val char = get(y)[x]

            if (char.isDigit()) {
                val digit = "$char".toInt()
                value = value?.times(10)?.plus(digit) ?: digit
                if (startPoint == null) startPoint = point
            } else {
                if (value != null) recordNumber()
                if (char.isSymbol()) symbols += Symbol(point, char.mayBeGear())
            }
        }

        if (value != null) recordNumber()
    }

    return numbers to symbols
}

    fun part1(input: List<String>): Int {
        val (numbers, symbols) = input.parseSchematic()
        val symbolPoints = symbols.map(Symbol::point)

        return numbers
            .filter { it.neighbors intersects symbolPoints }
            .sumOf(Number::value)
    }

    fun part2(input: List<String>): Int {
        val (numbers, symbols) = input.parseSchematic()

        return symbols
            .filter { symbol -> symbol.mayBeGear }
            .map { symbol ->
                val adjacentPartNumbers =
                    numbers.filter {
                        it.allPoints intersects symbol.point.surroundingPoints()
                    }

                symbol to adjacentPartNumbers
            }
            .filter { (_, adjacentPartNumbers) -> adjacentPartNumbers.size == 2 }
            .sumOf { (_, gearPartNumbers) ->
                gearPartNumbers
                    .map(Number::value)
                    .reduce(Int::times)
            }
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
