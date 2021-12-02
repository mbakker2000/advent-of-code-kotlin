package `2021`

import readInput
import kotlin.math.pow

fun main() {
    val regex = """^(F|B){7}(L|R){3}""".toRegex()
    fun solution(input: List<String>) = input
        .filter { regex.matches(it) }
        .map { it.substring(0, 7) to it.substring(7) }
        .map {
            var result = it.first.toCharArray().mapIndexed() { index, item ->
                val a: Int = 2.0.pow(index + 1).toInt()
                when (item) {
                    'B' -> (128 / a) to 0
                    'F' -> 0 to -(128 / a)
                    else -> 0 to 0
                }
            }.fold(0 to 127) { acc, item ->
                acc.first + item.first to acc.second + item.second
            } to it.second.toCharArray().mapIndexed() { index, item ->
                val a: Int = 2.0.pow(index + 1).toInt()
                when (item) {
                    'R' -> (8 / a) to 0
                    'L' -> 0 to -(8 / a)
                    else -> 0 to 0
                }
            }.fold(0 to 7) { acc, item ->
                acc.first + item.first to acc.second + item.second
            }
            result.first.first to result.second.second
        }

    fun part1(input: List<String>): Int {
        val result = solution(input)
            .map { pair -> pair.first * 8 + pair.second }

            .fold(0) { acc, nr ->
                if (acc > nr) acc else nr
            }
        return result
    }

    fun part2(input: List<String>): Int {
        val result = solution(input)
            .map { pair -> pair.first * 8 + pair.second }
            .sorted()
            .zipWithNext() { first, second ->
                if (second - first == 1) 0 to 0 else first to second
            }
            .filter { it.first != 0 }
            .map {  it.first + 1 }
            .fold(0) { acc , i ->
                i
            }

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/Day05_test")
    check(part1(testInput) == 357)

    val input = readInput("2020/Day05")
    println("part1: " + part1(input))
    println("part2: " + part2(input))
}
