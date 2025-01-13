import Operation.*

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
        var sumOfCalibrations = 0L
        for (line in input) {
            val calibrationValue = line.split(":")[0].toLong()
            val numbers = line.split(" ").drop(1).map(String::toLong)
            if (calibrationValue.canBeMadeFrom(numbers, true)) {
                sumOfCalibrations += calibrationValue
            }
        }
        return sumOfCalibrations
    }

    val input = readInput("Day07")
    // getPossibleCombinationsOfOperations(5).forEach {
    //     it.println()
    // }
    // return
    part1(input).println()
    part2(input).println()
}

private fun Long.canBeMadeFrom(numbers: List<Long>, allowConcatenation: Boolean = false): Boolean {
    val possibleCombinations =
        if (allowConcatenation)
            getPossibleCombinationsOfOperationsWithConcatenation(numbers.size)
        else
            getPossibleCombinationsOfOperations(numbers.size)

    for (combination in possibleCombinations) {
        // println("Combination: $combination")
        var result = if (combination[0] == MULTIPLICATION) 1L else 0L
        for ((index, operation) in combination.withIndex()) {
            // println("index $index, operation $operation")
            // println("Applying $operation on $result and ${numbers[index]}")
            result = operation.apply(result, numbers[index])
        }
        if (result == this) {
            // println("$combination on $numbers makes $this")
            return true
        }
        // else {
        //     println("$combination on $numbers makes $result")
        // }
    }
    // println("cannot make $this from $numbers")
    return false
}

fun getPossibleCombinationsOfOperations(size: Int): List<List<Operation>> {
    return (1 until size)
        .fold(listOf(listOf(ADDITION), listOf(MULTIPLICATION)))
        { combinationsList, _ ->
            combinationsList.flatMap { listOf(it + ADDITION, it + MULTIPLICATION) }
        }
        // todo actually make it a sequence
}

fun getPossibleCombinationsOfOperationsWithConcatenation(size: Int): List<List<Operation>> {
    return (1 until size)
        .fold(listOf(listOf(ADDITION), listOf(MULTIPLICATION), listOf(CONCATENATION)))
        { combinationsList, _ ->
            combinationsList.flatMap { listOf(it + ADDITION, it + MULTIPLICATION, it + CONCATENATION) }
        }
        // todo actually make it a sequence
}

enum class Operation {
    ADDITION,
    MULTIPLICATION,
    CONCATENATION;

    fun apply(a: Long, b: Long): Long = when (this) {
        ADDITION -> a + b
        MULTIPLICATION -> a * b
        CONCATENATION -> "$a$b".toLong()
    }
}