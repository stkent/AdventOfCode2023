import utils.println
import utils.readInput

fun main() {
    fun String.history(): List<Int> {
        return split("""\s+""".toRegex()).map(String::toInt)
    }

    fun List<Int>.nextValue(): Int {
        if (all { it == 0 }) return 0
        return last() + windowed(2).map { it[1] - it[0] }.nextValue()
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            line
                .history()
                .nextValue()
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            line
                .history()
                .reversed()
                .nextValue()
        }
    }

    val testInput = readInput("Day09_test")
    val input = readInput("Day09")

    check(part1(testInput) == 114)
    check(part1(input).println() == 2038472161)

    check(part2(testInput) == 2)
    check(part2(input).println() == 1091)
}
