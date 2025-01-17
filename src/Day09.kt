private val N = 10000
private val compressedData = ByteArray(N)
private val gaps = ByteArray(N)
private val unCompressedData = ShortArray(N * 10) // 100k is not so short though... ha ha ha
private var lastDataIndex = N * 10

fun main() {

    fun readDataAndGaps(input: List<String>) {
        var dataIndex = 0
        input[0].toCharArray().forEachIndexed { index, c ->
            if (index.isEven()) {
                compressedData[index / 2] = c.code.toByte()
            } else {
                gaps[index / 2] = c.code.toByte()
            }
            unCompressedData.fill(c.code.toShort(), dataIndex, dataIndex + c.code)
            dataIndex += c.code
        }
        lastDataIndex = dataIndex - 1
    }

    fun thereAreGaps(): Boolean {
        TODO("Not yet implemented")
    }

    fun moveOneData() {
        TODO("Not yet implemented")
    }

    fun compressData() {
        while (thereAreGaps()) {
            moveOneData()
        }
    }

    fun part1(input: List<String>): Int {
        readDataAndGaps(input)
        compressData()

        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}

private fun Int.isEven(): Boolean = this % 2 == 0
