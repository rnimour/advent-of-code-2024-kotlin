import Operation.ADDITION
import Operation.MULTIPLICATION

fun main() {

    fun part1(input: List<String>): Long {
        var sumOfCalibrations = 0L
        for (line in input) {
            val calibrationValue = line.split(":")[0].toLong()
            val numbers = line.split(" ").drop(1).map(String::toLong)
            if (calibrationValue.canBeMadeFrom(numbers)) {
                sumOfCalibrations += calibrationValue
            }
        }
        return sumOfCalibrations
    }

    fun part2(input: List<String>): Long {
        return 0
    }

    val input = readInput("Day07")
    // getPossibleCombinationsOfOperations(5).forEach {
    //     it.println()
    // }
    // return
    part1(input).println()
    part2(input).println()
}

private fun Long.canBeMadeFrom(numbers: List<Long>): Boolean {
    // for combinations of numbers and operations
    for (combination in getPossibleCombinationsOfOperations(numbers.size)) {
        // println("Combination: $combination")
        var result = if (combination[0] == ADDITION) 0L else 1L
        for ((index, operation) in combination.withIndex()) {
            // println("index $index, operation $operation")
            // println("Applying $operation on $result and ${numbers[index]}")
            result = operation.apply(result, numbers[index])
        }
        if (result == this) {
            println("$combination on $numbers makes $this")
            return true
        }
        // else {
        //     println("$combination on $numbers makes $result")
        // }
    }
    // println("cannot make $this from $numbers")
    return false
}

fun getPossibleCombinationsOfOperations(size: Int): Sequence<List<Operation>> {
    return (1 until size)
        .fold(listOf(listOf(ADDITION), listOf(MULTIPLICATION)))
        { combinationsList, _ ->
            combinationsList.flatMap { listOf(it + ADDITION, it + MULTIPLICATION) }
        }
        .asSequence() // todo actually make it a sequence
}

enum class Operation {
    ADDITION,
    MULTIPLICATION;

    fun apply(a: Long, b: Long): Long = when (this) {
        ADDITION -> a + b
        MULTIPLICATION -> a * b
    }
}