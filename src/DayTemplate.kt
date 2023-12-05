import utils.readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("DayXX_test")
    val input = readInput("DayXX")

    check(part1(testInput) == 0)
    println(part1(input))

//    check(part2(testInput) == 0)
//    println(part2(input))
}
