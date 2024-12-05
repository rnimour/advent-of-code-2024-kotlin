import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        // make lists one and two
        val list1 = mutableListOf<Int>();
        val list2 = mutableListOf<Int>();
        for (line in input) {
            val both = line.split(" ")
                .filter { it != "" }
                .map(String::toInt)
                .toList()
            list1.add(both[0])
            list2.add(both[1])
        }
        // sort them
        list1.sort()
        list2.sort()
        // iterate over both at the same time, incrementing the absolute difference
        var diff = 0
        list1.forEachIndexed { index, item ->
            diff += abs(item - list2[index])
        }

        return diff
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
