import utils.readInput

typealias Draw = Map<String, Int>

fun main() {
    fun String.gameInfo(): Pair<Int, List<Draw>> {
        val infoParts = split(": ")

        val id = infoParts[0].split(" ")[1].toInt()

        val draws =
            infoParts[1]
                .split("; ")
                .map { draw ->
                    draw
                        .split(", ")
                        .associate { colorDraw ->
                            colorDraw
                                .split(" ")
                                .let { (count, color) ->
                                    color to count.toInt()
                                }
                        }
                        .withDefault { 0 }
                }

        return id to draws
    }

    fun Draw.isValid(): Boolean {
        return getValue("red") <= 12 &&
                getValue("green") <= 13 &&
                getValue("blue") <= 14
    }

    fun part1(input: List<String>): Int {
        return input
            .sumOf { line ->
                val (id, draws) = line.gameInfo()
                if (draws.all(Draw::isValid)) id else 0
            }
    }

    fun List<Draw>.power(): Int {
        return maxOf { it.getValue("red") } *
                maxOf { it.getValue("green") } *
                maxOf { it.getValue("blue") }
    }

    fun part2(input: List<String>): Int {
        return input
            .sumOf { line ->
                val (_, draws) = line.gameInfo()
                draws.power()
            }
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
