private val N = 10000
private val GAP = (-1).toShort() // use magic value -1 as value indicating gap
private val compressedData = ShortArray(N)
private val gaps = ShortArray(N)
private val unCompressedData = ShortArray(N * 10) // 100k is not so short though... ha ha ha
private var lastDataIndex = N * 10 - 1
private var firstGapIndex = 0

fun main() {

    fun readDataAndGaps(input: List<String>) {
        var dataIndex = 0
        input[0].toCharArray().forEachIndexed { index, c ->
            if (index.isEven()) {
                compressedData[index / 2] = c.digitToShort()
                unCompressedData.fill((index / 2).toShort(), dataIndex, dataIndex + c.digitToShort())
            } else {
                gaps[index / 2] = c.digitToShort()
                unCompressedData.fill(GAP, dataIndex, dataIndex + c.digitToShort())
            }
            dataIndex += c.digitToShort()
        }
        lastDataIndex = dataIndex - 1
        // println("lastDataIndex: $lastDataIndex")
        firstGapIndex = input[0].toCharArray()[0].digitToInt() // - 1 + 1
    }

    fun thereAreGaps(): Boolean = firstGapIndex < lastDataIndex

    fun moveOneData() {
        while (unCompressedData[lastDataIndex] == GAP) {
            lastDataIndex-- // don't count gaps as data
        }
        if (unCompressedData[firstGapIndex] == GAP) {
            // move one data
            unCompressedData[firstGapIndex++] = unCompressedData[lastDataIndex]
            unCompressedData[lastDataIndex--] = GAP

        } else {
            // oh, advance firstGapIndex to the first -1 in front of the current position
            while (unCompressedData[firstGapIndex] != GAP) {
                firstGapIndex++
            }
        }
        // if (firstGapIndex > 49880) {
        //     println(unCompressedData.sliceArray(49880..49920).joinToString(" "))
        // }
    }

    fun compressData() {
        // var index = 0
        while (thereAreGaps()) {
            moveOneData()
            // if (index++ % 10 == 0) {
            //     println(unCompressedData.joinToString(" ").take(200))
            // }
            // if (index > 2000) {return}
        }
    }

    fun checksum(): Long {
        var sum = 0L
        unCompressedData.forEachIndexed { index, value ->
            if (value == GAP) {
                println("lastDataIndex: $lastDataIndex")
                println("firstGapIndex: $firstGapIndex")
                println("data around 49900:")
                println(unCompressedData.sliceArray(49880..49920).joinToString(" "))
                // println("data around index $index:")
                // println(unCompressedData.sliceArray(index - 10 ..index + 10).joinToString(" "))
                return sum
            }
            sum += index * value
        }
        throw IllegalStateException("shouldn't reach here: were gaps not added to the end?")
    }

    fun part1(input: List<String>): Long {
        readDataAndGaps(input)
        compressData()
        return checksum()
    }

    fun part2(input: List<String>): Long {
        return 0
    }

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}

private fun Char.digitToShort(): Short = digitToInt().toShort()

private fun Int.isEven(): Boolean = this % 2 == 0
