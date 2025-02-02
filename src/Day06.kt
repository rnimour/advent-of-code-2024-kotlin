import Direction.*
import Field.VISITED

private val N = 130
private val grid = Array(N) { arrayOfNulls<Field>(N) }
private val gridDirections = Array(N) { Array<MutableSet<Direction>>(N, init = { _ -> mutableSetOf() }) }
private var guard = Guard(Pair(0, 0), UP)

fun main() {

    fun walk(): Int {
        val newGuardPosition = when (guard.direction) {
            UP -> Pair(guard.position.first - 1, guard.position.second)
            DOWN -> Pair(guard.position.first + 1, guard.position.second)
            RIGHT -> Pair(guard.position.first, guard.position.second + 1)
            LEFT -> Pair(guard.position.first, guard.position.second - 1)
        }
        var visited = 0
        if (grid[newGuardPosition.first][newGuardPosition.second] == Field.BLOCKED) {
            guard.direction = guard.direction.next()
            return walk()
        }
        if (grid[guard.position.first][guard.position.second] != VISITED) {
            grid[guard.position.first][guard.position.second] = VISITED
            visited = 1
        }
        if (!gridDirections[guard.position.first][guard.position.second].add(guard.direction)) {
            throw LoopException()
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

    fun part2(input: List<String>): Int {
        var squaresWhichResultInLoop = 0
        for (i in 0..<N) {
            for (j in 0..<N) {
                readGrid(input)
                gridDirections.forEach { it.forEach { it.clear() } }
                if (grid[i][j] == Field.EMPTY) {
                    grid[i][j] = Field.BLOCKED // add an obstacle
                    try {
                        while (true) {
                            walk()
                        }
                    } catch (e: ArrayIndexOutOfBoundsException) {
                        /* guard escaped, so */ continue
                    } catch (_: LoopException) {
                        squaresWhichResultInLoop++
                    }
                }
            }
        }
        return squaresWhichResultInLoop // 1770
    }

    val input = readInput("Day06")
    readGrid(input)
    printGrid()
    // return
    part1().println()
    part2(input).println()
}

private fun printGrid() {
    // grid[0][0] is the top left corner (printed first)
    // grid[N-1][0] is the bottom left corner
    for (i in 0..<N) {
        for (j in 0..<N) {
            print(
                when (grid[i][j]) {
                    Field.EMPTY -> '.'
                    Field.BLOCKED -> '#'
                    VISITED -> 'X'
                    Field.GUARD -> '^'
                    null -> TODO()
                }
            )
        }
        println()
    }
    repeat(8) { println() }
}

fun readGrid(input: List<String>) =
    input.forEachIndexed { index, line ->
        line.forEachIndexed { index2, c ->
            grid[index][index2] = when (c) {
                '.' -> Field.EMPTY
                '#' -> Field.BLOCKED
                '^' -> Field.EMPTY.also {
                    guard.position = Pair(index, index2)
                    guard.direction = UP
                }

                else -> throw Exception("Unknown field")
            }
        }
    }

class Guard(var position: Pair<Int, Int>, var direction: Direction)

class LoopException : RuntimeException()

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
        when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }
}