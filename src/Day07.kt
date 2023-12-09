import HandType.*
import utils.extensions.charCounts
import utils.readInput
import kotlin.math.sign

enum class HandType {
    FiveOfAKind,
    FourOfAKind,
    FullHouse,
    ThreeOfAKind,
    TwoPair,
    OnePair,
    HighCard,
}

fun main() {
    /**
     * For sorting hands first by type, then by cards.
     */
    fun handSort(handType: (String) -> HandType, cardRanks: Map<Char, Int>): Comparator<String> {
        return compareBy(handType)
            .thenComparing { h1, h2 ->
                h1.zip(h2)
                    .firstOrNull { (c1, c2) -> c1 != c2 }
                    ?.let { (c1, c2) -> (cardRanks[c1]!! - cardRanks[c2]!!).sign }
                    ?: 0
            }
            .reversed()
    }

    fun List<String>.totalWinnings(handType: (String) -> HandType, cardRanks: Map<Char, Int>): Int {
        return this
            .map { line -> line.split("""\s+""".toRegex()) }
            .map { (hand, bid) -> hand to bid.toInt() }
            .sortedWith(compareBy(handSort(handType, cardRanks)) { (hand, _) -> hand })
            .map { (_, bid) -> bid }
            .withIndex()
            .sumOf { (rank, bid) -> (rank + 1) * bid }
    }

    val plainCardRanks: Map<Char, Int> =
        "AKQJT98765432"
            .withIndex()
            .associate { (index, char) -> char to index }

    fun String.plainHandType(): HandType {
        val counts = charCounts()

        return when {
            counts.size == 1 -> FiveOfAKind
            counts.size == 2 && counts.maxOf { it.value } == 4 -> FourOfAKind
            counts.size == 2 -> FullHouse
            counts.size == 3 && counts.maxOf { it.value } == 3 -> ThreeOfAKind
            counts.size == 3 -> TwoPair
            counts.size == 4 -> OnePair
            else -> HighCard
        }
    }

    fun part1(input: List<String>): Int {
        return input
            .totalWinnings(
                handType = String::plainHandType,
                cardRanks = plainCardRanks,
            )
    }

    val jokerCardRanks: Map<Char, Int> =
        "AKQT98765432J"
            .withIndex()
            .associate { (index, char) -> char to index }

    fun String.jokersHandType(): HandType {
        return jokerCardRanks
            .keys
            .minOf { c -> this.replace('J', c).plainHandType() }
    }

    fun part2(input: List<String>): Int {
        return input
            .totalWinnings(
                handType = String::jokersHandType,
                cardRanks = jokerCardRanks,
            )
    }

    val testInput = readInput("Day07_test")
    val input = readInput("Day07")

    check(part1(testInput) == 6440)
    println(part1(input).also { check(it == 253866470) })

    check(part2(testInput) == 5905)
    println(part2(input).also { check(it == 254494947) })
}
