import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int = input.stream()
        .map(String::toInts)
        .filter(::isSafe)
        .count()
        .toInt()

    fun part2(input: List<String>): Int = input.stream()
        .map(String::toInts)
        .filter(::isSafeDampened)
        .count()
        .toInt()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

// a report only counts as safe if both of the following are true:
//     The levels are either all increasing or all decreasing.
//     Any two adjacent levels differ by at least one and at most three.
fun isSafe(ints: List<Int>): Boolean =
    (
        ints.zipWithNext().all { (a, b) -> a > b } ||
        ints.zipWithNext().all { (a, b) -> a < b }
    ) &&
        ints.zipWithNext().all { (a, b) -> abs(a - b) <= 3 }

// if one level can be removed to fix it, it is considered safe
fun isSafeDampened(ints: List<Int>): Boolean {
    for (i in ints.indices) {
        if (isSafe(ints.take(i) + ints.drop(i + 1))) {
            return true
        }
    }
    return false
}
