fun main() {

    val letters = Array(140) { CharArray(140) }

    fun readIntoLetters(input: List<String>) {
        input.forEachIndexed { index1, line ->
            line.forEachIndexed() { index2, char ->
                letters[index1][index2] = char
            }
        }
    }

    fun xmasOffset(lineNumber: Int, columnNumber: Int, offsetX: Int, offsetY: Int): Int {
        val outerBoundX = columnNumber + 3 * offsetY
        val outerBoundY = lineNumber + 3 * offsetX
        if (outerBoundX >= 140 || outerBoundX < 0 || outerBoundY >= 140 || outerBoundY < 0) {
            return 0
        }
        if (letters[lineNumber + (offsetX * 1)][columnNumber + (offsetY * 1)] == 'M' &&
            letters[lineNumber + (offsetX * 2)][columnNumber + (offsetY * 2)] == 'A' &&
            letters[lineNumber + (offsetX * 3)][columnNumber + (offsetY * 3)] == 'S') {
            return 1
        }
        return 0
    }

    fun xmasCountOf(lineNumber: Int, columnNumber: Int): Int {
        var xmases = 0
        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) {
                    continue
                }
                xmases += xmasOffset(lineNumber, columnNumber, i, j)
            }
        }
        return xmases
    }

    fun part1(input: List<String>): Int {
        readIntoLetters(input)
        var xmasCount = 0
        letters.forEachIndexed { lineNumber, line ->
            line.forEachIndexed { columnNumber, char ->
                xmasCount += if ( char == 'X' ) xmasCountOf(lineNumber, columnNumber) else 0
            }
        }
        return xmasCount
    }

    fun m_up_and_s_down(lineNumber: Int, columnNumber: Int): Boolean =
        letters[lineNumber - 1][columnNumber - 1] == 'M' &&
        letters[lineNumber - 1][columnNumber + 1] == 'M' &&
        letters[lineNumber + 1][columnNumber - 1] == 'S' &&
        letters[lineNumber + 1][columnNumber + 1] == 'S'

    fun m_down_and_s_up(lineNumber: Int, columnNumber: Int): Boolean =
        letters[lineNumber - 1][columnNumber - 1] == 'S' &&
        letters[lineNumber - 1][columnNumber + 1] == 'S' &&
        letters[lineNumber + 1][columnNumber - 1] == 'M' &&
        letters[lineNumber + 1][columnNumber + 1] == 'M'

    fun m_left_and_s_right(lineNumber: Int, columnNumber: Int): Boolean =
        letters[lineNumber - 1][columnNumber - 1] == 'M' &&
        letters[lineNumber + 1][columnNumber - 1] == 'M' &&
        letters[lineNumber - 1][columnNumber + 1] == 'S' &&
        letters[lineNumber + 1][columnNumber + 1] == 'S'

    fun m_right_and_s_left(lineNumber: Int, columnNumber: Int): Boolean =
        letters[lineNumber - 1][columnNumber - 1] == 'S' &&
        letters[lineNumber + 1][columnNumber - 1] == 'S' &&
        letters[lineNumber - 1][columnNumber + 1] == 'M' &&
        letters[lineNumber + 1][columnNumber + 1] == 'M'

    fun x_masCountOf(lineNumber: Int, columnNumber: Int): Int {
        if (lineNumber == 0 || lineNumber == 139 || columnNumber == 0 || columnNumber == 139) {
            return 0
        }
        var x_mases = 0
        if (m_up_and_s_down(lineNumber, columnNumber)
            || m_down_and_s_up(lineNumber, columnNumber)
            || m_left_and_s_right(lineNumber, columnNumber)
            || m_right_and_s_left(lineNumber, columnNumber)) {
            x_mases += 1
        }
        return x_mases
    }

    fun part2(input: List<String>): Int {
        readIntoLetters(input)
        var xmasCount = 0
        letters.forEachIndexed { lineNumber, line ->
            line.forEachIndexed { columnNumber, char ->
                xmasCount += if ( char == 'A' ) x_masCountOf(lineNumber, columnNumber) else 0
            }
        }
        return xmasCount
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
