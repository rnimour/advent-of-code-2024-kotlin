import Direction.*
import Field.VISITED

val N = 130
val grid = Array(N) { arrayOfNulls<Field>(N) }
var guard = Guard(Pair(0, 0), UP)

fun main() {

    fun walk(): Int {
        val newGuardPosition = when (guard.direction) {
            UP -> Pair(guard.position.first - 1, guard.position.second)
            DOWN -> Pair(guard.position.first + 1, guard.position.second)
            RIGHT -> Pair(guard.position.first, guard.position.second + 1)
            LEFT -> Pair(guard.position.first, guard.position.second - 1)
        }
        var visited = 0
        if (grid[N-1-newGuardPosition.first][newGuardPosition.second] == Field.BLOCKED) {
            guard.direction = guard.direction.next()
            return walk()
        }
        if (grid[N-1-guard.position.first][guard.position.second] != VISITED) {
            grid[N-1-guard.position.first][guard.position.second] = VISITED
            visited = 1
        }
        guard.position = newGuardPosition
        return visited
    }

    fun part1(): Int {
        var squaresVisited = 0
        try {
            while (true) {
                squaresVisited += walk()
            }
        } catch (e: ArrayIndexOutOfBoundsException) {
            // yeah yeah yeah, exception for flow control is bad practice, shoot me.
            printGrid()
            return squaresVisited + 1
        }
    }

    fun part2(): Int {
        return 0
    }

    val input = readInput("Day06")
    readGrid(input)
    printGrid()
    // return
    part1().println()
    part2().println()
}

private fun printGrid() {
    // grid[0][0] is the bottom left corner
    // grid[N-1][0] is the top left corner (printed first)
    for (i in N-1 downTo 0) {
        for (j in 0..<N) {
            print(when(grid[i][j]) {
                Field.EMPTY -> '.'
                Field.BLOCKED -> '#'
                VISITED -> 'X'
                Field.GUARD -> '^'
                null -> TODO()
            })
        }
        println()
    }
    println()
}

fun readGrid(input: List<String>) =
    input.forEachIndexed { index, line ->
        line.forEachIndexed { index2, c ->
            grid[N-1-index][index2] = when (c) {
                '.' -> Field.EMPTY
                '#' -> Field.BLOCKED
                '^' -> Field.GUARD.also { guard.position = Pair(index, index2) }
                else -> throw Exception("Unknown field")
            }
        }
    }

class Guard(var position: Pair<Int, Int>, var direction: Direction)

enum class Field {
    EMPTY,
    BLOCKED,
    VISITED,
    GUARD
}

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    fun next() =
        // when (this) {
        //     UP -> LEFT
        //     RIGHT -> UP
        //     DOWN -> RIGHT
        //     LEFT -> DOWN
        // }
        when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }
}