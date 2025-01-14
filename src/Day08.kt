private val N = 50
private val grid = Array(N) { Array(N) { Pair('.', false) } } // antenna, antinode yes or no
private val charsInGrid = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

private operator fun Pair<Int, Int>.minus(antenna2: Pair<Int, Int>): Pair<Int, Int> =
    Pair(2 * this.first - antenna2.first, 2 * this.second - antenna2.second)

fun main() {

    fun readGrid(input: List<String>) =
        input.forEachIndexed { index, line ->
            line.forEachIndexed { index2, c ->
                charsInGrid.computeIfAbsent(c) { mutableListOf() }.add(Pair(index, index2))
                grid[index][index2] = Pair(c, false)
            }
        }.also { charsInGrid.remove('.') }

    fun printCharAmounts() {
        charsInGrid.forEach { (k, v) ->
            println("$k: ${v.size}")
        }
    }

    fun addAntiNodesOf(coordinates: List<Pair<Int, Int>>) {
        val antinodes = mutableListOf<Pair<Int, Int>>()
        for ((index, antenna1) in coordinates.withIndex()) {
            for (antenna2 in coordinates.drop(index + 1)) {
                antinodes.add(antenna1 - antenna2)
                antinodes.add(antenna2 - antenna1)
            }
        }
        for (antinode in antinodes) {
            val x = antinode.first
            val y = antinode.second
            if (x < 0 || x >= N || y < 0 || y >= N) {
                continue
            }
            grid[x][y] = Pair(grid[x][y].first, true) // yuck
        }
    }

    fun part1(input: List<String>): Int {
        readGrid(input)
        for (entry in charsInGrid) {
            addAntiNodesOf(entry.value)
        }
        printGrid()
        // printCharAmounts()

        return grid.sumOf { row -> row.count { it.second } }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}

private fun printGrid() {
    // grid[0][0] is the top left corner (printed first)
    // grid[N-1][0] is the bottom left corner
    for (i in 0..<N) {
        for (j in 0..<N) {
            print(
                if (grid[i][j].first != '.')
                    grid[i][j].first
                else if (grid[i][j].second)
                    '#'
                else
                    '.'
            )
        }
        println()
    }
}