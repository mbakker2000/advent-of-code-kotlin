package `2021`

import readInput

fun main() {

    fun solution(
        input: List<String>,
        fn: (Pair<String, Int>) -> Pair<Int, Int>,
        fn2: (Pair<Int, Int>, Pair<Int, Int>) -> Pair<Int, Int>
    ): Int {
        val regex = """^(forward|down|up)\s[0-9]$""".toRegex()
        val result = input
            .filter { regex.matches(it) }
            .map { row ->
                val (cmd, num) = row.split(" ")
                cmd to num.toInt()
            }
            .map(fn)
            .fold(0 to 0) { acc, i ->
                fn2(acc, i)
            }

        return result.first * result.second
    }


    fun part1(input: List<String>): Int {
        return solution(input, {
            when (it.first) {
                "forward" -> it.second to 0
                "down" -> 0 to it.second
                "up" -> 0 to -it.second
                else -> 0 to 0
            }
        }, { acc, i ->
            acc.first + i.first to acc.second + i.second
        })
    }


    fun part2(input: List<String>): Int {
        var aim = 0
        return solution(input, {
            when (it.first) {
                "forward" -> it.second to 0
                "down" -> 0 to it.second
                "up" -> 0 to -it.second
                else -> 0 to 0
            }
        }, { acc, i ->
            aim += i.second
            acc.first + i.first to (acc.second + (i.first * aim))
        })
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("2021/Day02_test")
    check(part1(testInput) == 150)

    val input = readInput("2021/Day02")
    println("part1:" + part1(input))
    check(part2(testInput) == 900)
    println("part2:" + part2(input))
}
