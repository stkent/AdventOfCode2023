import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main() {
    fun waysToBeat(t: Long, d: Long): Long {
        val lowRoot = floor((t * t - sqrt((t * t - 4 * d).toDouble())) / 2).toLong()
        val highRoot = ceil((t * t + sqrt((t * t - 4 * d).toDouble())) / 2).toLong()
        return highRoot - lowRoot - 1
    }

    fun part1(ts: List<Long>, ds: List<Long>): Long {
        return ts.zip(ds)
            .map { (t, d) -> waysToBeat(t, d) }
            .reduce(Long::times)
    }

    fun part2(t: Long, d: Long): Long {
        return waysToBeat(t, d)
    }

    check(part1(listOf(7L, 15, 30), listOf(9L, 40, 200)) == 288L)
    println(part1(listOf(55L, 99, 97, 93), listOf(401L, 1485, 2274, 1405)))

    check(part2(71530L, 940200) == 71503L)
    println(part2(55999793L, 401148522741405))
}
