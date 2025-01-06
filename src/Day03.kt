fun main() {

    fun part1(input: List<String>): Int {
        val regex = Regex("mul\\((\\d+),(\\d+)\\)")
        var sum = 0
        for (string in input) {
            sum += regex.findAll(string).sumOf {
                (it.groupValues[1].toInt() * it.groupValues[2].toInt())
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val regex = Regex("(mul\\((\\d+),(\\d+)\\))|(do(n't)?\\(\\))")
        var sum = 0
        var doMul = true;
        for (string in input) {
            for (element in regex.findAll(string)) {
                when (element.groupValues[0]) {
                    "do()" -> doMul = true
                    "don't()" -> doMul = false
                    else -> if (doMul) {
                        sum += element.groupValues[2].toInt() * element.groupValues[3].toInt()
                    }
                }
            }
        }
        return sum
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

