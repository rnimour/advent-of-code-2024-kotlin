private val N = 50
private val grid = Array(N) { Array(N) { Pair('.', false) } } // antenna, antinode yes or no
private val charsInGrid = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

private operator fun Pair<Int, Int>.minus(antenna2: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first - antenna2.first, this.second - antenna2.second)

private operator fun Pair<Int, Int>.plus(antenna2: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first + antenna2.first, this.second + antenna2.second)

private operator fun Int.times(antenna: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this * antenna.first, this * antenna.second)

private operator fun Pair<Int, Int>.times(antenna2: Pair<Int, Int>): Collection<Pair<Int, Int>> {
    val antinodes = mutableListOf<Pair<Int, Int>>()
    val direction = antenna2 - this
    var antinode = this + direction
    var i = 1
    while (!outOfBounds(antinode.first, antinode.second)) {
        antinodes.add(antinode)
        antinode = this + (++i * direction)
    }
    return antinodes
}

fun outOfBounds(x: Int, y: Int): Boolean = x < 0 || x >= N || y < 0 || y >= N

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
                antinodes.add(antenna1 + (antenna1 - antenna2))
                antinodes.add(antenna2 + (antenna2 - antenna1))
            }
        }
        for (antinode in antinodes) {
            val x = antinode.first
            val y = antinode.second
            if (outOfBounds(x, y)) {
                continue
            }
            grid[x][y] = Pair(grid[x][y].first, true) // yuck
        }
    }

    fun addSuperAntiNodesOf(coordinates: List<Pair<Int, Int>>) {
        val antinodes = mutableListOf<Pair<Int, Int>>()
        for (antenna1 in coordinates) {
            for (antenna2 in coordinates) {
                if (antenna1 == antenna2) {
                    continue
                }
                antinodes.addAll(antenna1 * antenna2)
            }
        }
        for (antinode in antinodes) {
            val x = antinode.first
            val y = antinode.second
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
        charsInGrid.clear()
        readGrid(input)
        for (entry in charsInGrid) {
            addSuperAntiNodesOf(entry.value)
        }
        printGrid()

        return grid.sumOf { row -> row.count { it.second } }
    }

    val input = readInput("Day08")
    part1(input).println()
    println()
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