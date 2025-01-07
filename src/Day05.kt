fun main() {

    val rules: MutableList<Pair<Int, Int>> = mutableListOf()
    val updates: MutableList<List<Int>> = mutableListOf()

    fun readRulesAndUpdates(input: List<String>) {
        for (s in input) {
            if (s.contains("|")) {
                // rule
                val parts = s.split("|")
                rules.add(Pair(parts[0].toInt(), parts[1].toInt()))
            } else if (s.contains(",")) {
                // update
                val parts = s.split(",")
                updates.add(parts.map { it.toInt() }.toList())
            }
        }
    }

    fun part1(input: List<String>): Int {
        readRulesAndUpdates(input)
        var sum = 0
        for (update in updates) {
            if (update.satisfiesAll(rules)) {
                sum += update[update.size / 2]
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

private fun List<Int>.satisfiesAll(rules: List<Pair<Int,Int>> ): Boolean = rules.all { this.satisfies(it) }

private fun List<Int>.satisfies(rule: Pair<Int, Int>): Boolean {
    val indexFirst = indexOf(rule.first)
    if (indexFirst == -1)
        return true

    val indexSecond = indexOf(rule.second)
    if (indexSecond == -1)
        return true

    if (indexFirst < indexSecond) {
        return true
    }
    return false
}
