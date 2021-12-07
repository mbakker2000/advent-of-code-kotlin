package `2021`

import readInput
import kotlin.math.abs

fun main() {

    fun solution(input: List<String>, fn: (Long) -> Long): Long {
        val crabs = input[0].split(",").map { it.toInt() }
        return ( 0 until crabs.maxOf { it }).minOf {  target ->
            crabs.sumOf {
                (0L until abs(it - target)).sumOf(fn)
            }
        }

    }
    fun part1(input: List<String>): Long {
        return solution(input) {
            1L
        }
    }

    fun part2(input: List<String>): Long {
        return solution(input) {
            it + 1
        }
    }
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2021/Day07_test")

    check(part1(testInput) == 37L)

    val input = readInput("2021/Day07")
    println("part1:" + part1(input))//46464
    //check(part2(testInput) == 1924)//1924
    println("part2:" + part2(input))//25925
}
