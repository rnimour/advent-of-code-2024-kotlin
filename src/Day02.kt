import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int = input.stream()
        .map(String::toInts)
        .filter(::isSafe)
        .count()
        .toInt()

    fun part2(input: List<String>): Int {
        return 0
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

// a report only counts as safe if both of the following are true:
//     The levels are either all increasing or all decreasing.
//     Any two adjacent levels differ by at least one and at most three.
fun isSafe(ints: List<Int>): Boolean {
    return (ints.zipWithNext().all { (a, b) -> a > b } ||
            ints.zipWithNext().all { (a, b) -> a < b }
            ) &&
            ints.zipWithNext().all { (a, b) -> abs(a - b) <= 3 }
}
