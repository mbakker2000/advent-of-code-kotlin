package `2021`

import readInput

fun main() {
    val regex = """(((\|X)(\|(.){1,3}){4}){4}(\|X)|\|(\|X){5}(\|){2})""".toRegex()
    val replaceSpacesRegex = """\s+""".toRegex()

    fun solution(number: Int, input: Pair<List<String>, List<String>>): Pair<List<String>, List<String>> {
        val boards = input.first
            .map { it.replace("|$number|", "|X|") }
            .map {
                if (it.contains(regex)) listOf("") to listOf(it) else listOf(it) to listOf("")
            }
            .fold(listOf<String>() to listOf(* input.second.toTypedArray())) { acc, item ->
                listOf(*item.first.toTypedArray(), * acc.first.toTypedArray()) to
                        listOf(*item.second.toTypedArray(), * acc.second.toTypedArray())
            }
        return boards.first.filter { it.isNotBlank() } to boards.second.filter { it.isNotBlank() }
    }

    fun toRaster(input: List<String>): String {
        return input
            .filter { !it.contains(',') }
            .map { it.trim().replace(replaceSpacesRegex, "|") }
            .reduce { acc, s ->
                val result = if (s == "") "$acc|*|" else "$acc|$s|"
                result
            }
    }

    fun count(input: String): Int {
        return input.split("|")
            .filter { it != "" }
            .filter { it != "X" }
            .map { it.toInt() }
            .fold(0) { acc, i -> acc + i }
    }

    fun part1(input: List<String>): Int {
        var current = "|${toRaster(input)}|"
        val turns = input[0].split(",")
            .asSequence()
            .map() {
                current = current.replace("|$it|", "|X|")
                it to current
            }
            .map {
                it.first to it.second.split("*").filter { it.contains(regex) }
            }
            .filter { it.second.isNotEmpty() }
            .map {
                it.first to count(it.second[0])
            }.first()
        return (turns.first.toInt() * turns.second)
    }

    fun part2(input: List<String>): Int {
        var pairs = toRaster(input)
            .split("*")
            .map { "||$it" } to listOf<String>()
        input[0]
            .split(",")
            .map { it.toInt() }
            .forEach() {
                val updated = solution(it, pairs)
                if (updated.first.isEmpty() && pairs.first.isNotEmpty()) {
                    return it * count(pairs.first.first().replace("|${it}|", "|X|"))
                }
                pairs = updated
            }
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2021/Day04_test")
    check(part1(testInput) == 4512)

    val input = readInput("2021/Day04")

    println("part1:" + part1(input))//46464
    check(part2(testInput) == 1924)//1924
    println("part2:" + part2(input))//25925
}
