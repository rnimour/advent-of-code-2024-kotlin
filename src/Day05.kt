fun main() {

    val rules: MutableList<Pair<Int, Int>> = mutableListOf()
    val updates: MutableList<MutableList<Int>> = mutableListOf()

    fun readRulesAndUpdates(input: List<String>) {
        for (s in input) {
            if (s.contains("|")) {
                // rule
                val parts = s.split("|")
                rules.add(Pair(parts[0].toInt(), parts[1].toInt()))
            } else if (s.contains(",")) {
                // update
                val parts = s.split(",")
                updates.add(parts.map { it.toInt() }.toMutableList())
            }
        }
    }

    fun part1(input: List<String>): Int {
        return updates
            .filter { it.satisfiesAll(rules) }
            .sumOf { it[it.size / 2] }
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (update in updates) {
            var correct = true
            while (!update.satisfiesAll(rules)) {
                correct = false
                update.reorderAccordingTo(rules)
            }
            sum += if (correct) 0 else update[update.size / 2]
        }
        return sum
    }

    val input = readInput("Day05")
    readRulesAndUpdates(input)
    part1(input).println()
    part2(input).println()
}

private fun MutableList<Int>.reorderAccordingTo(rules: MutableList<Pair<Int, Int>>) {
    for (rule in rules) {
        if (!satisfies(rule)) {
            swap(indexOf(rule.first), indexOf(rule.second))
        }
    }
}

private fun List<Int>.satisfiesAll(rules: List<Pair<Int, Int>>): Boolean = rules.all { this.satisfies(it) }

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
