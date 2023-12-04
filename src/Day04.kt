import utils.extensions.pow
import utils.readInput

fun main() {
    fun String.matchCount(): Int {
        return this
            .split(""":\s+""".toRegex())[1]
            .split("""\s+\|\s+""".toRegex())
            .map { numbersString ->
                numbersString
                    .split("""\s+""".toRegex())
                    .map(String::toInt)
                    .toSet()
            }
            .reduce(Set<Int>::intersect)
            .count()
    }

    fun List<String>.parseCards(): List<Pair<Int, Int>> {
        return this.mapIndexed { index, string -> index to string.matchCount() }
    }

    fun part1(input: List<String>): Int {
        return input
            .parseCards()
            .sumOf { (_, matchCount) ->
                if (matchCount > 0) 2.pow(matchCount - 1) else 0
            }
    }

    fun part2(input: List<String>): Int {
        val initialCards = input.parseCards()
        val cardCounts = MutableList(input.size) { 1 }

        for ((index, matchCount) in initialCards) {
            repeat(matchCount) { offset ->
                cardCounts[index + (offset + 1)] += cardCounts[index]
            }
        }

        return cardCounts.sum()
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
