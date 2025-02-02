import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.streams.toList

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun String.toInts(): List<Int> = split(" ").stream().mapToInt { it.toInt() }.toList()

fun <E> MutableList<E>.swap(indexFirst: Int, indexSecond: Int) {
    val temp = this[indexFirst]
    this[indexFirst] = this[indexSecond]
    this[indexSecond] = temp
}
