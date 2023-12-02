import utils.readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput1 = readInput("DayXX_test1")
    check(part1(testInput1) == 0)

    val testInput2 = readInput("DayXX_test2")
    check(part2(testInput2) == 0)

    val input = readInput("DayXX")
    println(part1(input))
    println(part2(input))
}
