package `2021`

import readInput

fun main() {
    val compleLineRegec =
        """\*(\||[0-9]|X)*(((\|X)(\|(.){1,3}){4}){4}(\|X)|\|(\|X){5}(\|){2})(\||[0-9]|X)*\*""".toRegex()
    val replaceSpacesRegex = """\s+""".toRegex()

    fun toRaster(input: List<String>): String {
        return input
            .filter { !it.contains(',') }
            .map { it.trim().replace(replaceSpacesRegex, "|") }
            .reduce { acc, s ->
                val result = if (s == "") "$acc|*&seperator&*|" else "$acc|$s|"
                result
            }
    }

    fun count(input: String): Int {
        return input.replace("*", "").split("|")
            .filter { it != "" }
            .filter { it != "X" }
            .map { it.toInt() }
            .fold(0) { acc, i -> acc + i }
    }

    fun getTurns(input: List<String>): List<Pair<Int, String>> {
        var current = "*|${toRaster(input)}|*"
        val numbers = input[0].split(",")
            .map { it.toInt() }
        val turns = numbers
            .map() { number ->
                current = current.replace("|$number|", "|X|")
                number to current
            }
        return turns
    }

    fun bingos(turns: List<Pair<Int, String>>): List<Pair<Int, String>> {
        val list = turns.zipWithNext() { a, b ->
            a to b
        }
            .filter {
                val sec1 = compleLineRegec.findAll(it.first.second)
                var firstMatchSize = 0
                sec1.iterator().forEach { firstMatchSize++ }
                val sec2 = compleLineRegec.findAll(it.second.second)
                var secondMatchSize = 0
                sec2.iterator().forEach { secondMatchSize++ }
                firstMatchSize < secondMatchSize
            }.map {
                val pairs = compleLineRegec.findAll(it.first.second)
                    .map { matchResult -> matchResult.range.first to matchResult.range.last }.toList()
                var string = it.second.second
                pairs.forEach() { pair ->
                    string = string.toCharArray().mapIndexed() { index, char ->
                        if (pair.first <= index && index <= pair.second) '#' else char
                    }.joinToString("")
                }

                it.second.first to string
            }
        return list
    }

    fun calc(bingo: Pair<Int, String>): Int {
        return count(compleLineRegec.findAll(bingo.second).map { matchResult -> matchResult.value }
            .joinToString("")) * bingo.first
    }

    fun parts(input: List<String>): Pair<Int, Int> {
        val turns = getTurns(input)
        val bingos = bingos(turns)
        return calc(bingos[0]) to calc(bingos[bingos.size - 1])
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2021/Day04_test")
    val input = readInput("2021/Day04")

    val results = parts(input)
    val testResults = parts(testInput)

    check(testResults.first == 4512)
    println("part1:" + results.first)//46464
    check(testResults.second == 1924)//1924
    println("part2:" + results.second)//25925
}
