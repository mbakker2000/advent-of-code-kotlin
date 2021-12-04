package `2021`

import readInput

fun main() {
    val regex = """((((\|X){1}(\|(.){1,3}){4}){1}){4}(\|X){1}|\|\|X\|X\|X\|X\|X\|\|)""".toRegex()


    fun solution(number: Int, input: Pair<List<String>, List<String>>): Pair<List<String>, List<String>> {
        val boards = input.first
            .map { it.replace("|$number|", "|X|") }
            .map {
                if (it.contains(regex)) {
                    listOf("") to listOf(it)
                } else {
                    listOf(it) to listOf("")
                }
            }
            .fold(listOf<String>() to listOf<String>(* input.second.toTypedArray())) { acc, item ->
                listOf(*item.first.toTypedArray(), * acc.first.toTypedArray()) to
                        listOf(*item.second.toTypedArray(), * acc.second.toTypedArray())
            }
        return boards.first.filter { it.isNotBlank() } to boards.second.filter { it.isNotBlank() }
    }


    fun part1(input: List<String>): Int {
        val boards = input
            .filter { !it.contains(',') }
            .map { it.trim().replace("\\s+".toRegex(), "|") }
            .reduce { acc, s ->
                val result = if (s == "") acc + "|*|" else acc + "|" + s + "|"
                result
            }
        var current = "|$boards|"
        val turns = input[0].split(",")
            .mapIndexed() { index, it ->
                current = current.replace("|$it|", "|X|")
                it to current
            }
            .map {
                it.first to it.second.split("*")
                    .filter { it.contains(regex) }
            }
            .filter { it.second.isNotEmpty() }
            .map {
                it.first to it.second[0]
                    .split("|")
                    .filter { it != "" }
                    .filter { it != "X" }
                    .map { it.toInt() }
                    .fold(0) { acc, i -> acc + i }
            }
            .first()

        return (turns.first.toInt() * turns.second)
    }
    fun part2(input: List<String>): Int {
        val boards = input
            .filter { !it.contains(',') }
            .map { it.trim().replace("\\s+".toRegex(), "|") }
            .reduce { acc, s ->
                val result = if (s == "") "$acc*" else "$acc$s||"
                result
            }.split("*")
            .map { "||$it" }

        var pairs = boards to listOf<String>()
        var theSpecialOne = Pair(0, "")
        input[0]
            .split(",")
            .map { it.toInt() }
            .forEach() {
                val updated = solution(it, pairs)
                if (updated.first.size == 0 && pairs.first.size != 0) {
                    theSpecialOne = it to pairs.first.first()
                }
                pairs = updated
            }

        val result = theSpecialOne.first to theSpecialOne.second
            .replace("|${theSpecialOne.first}|", "|X|")
            .split("|")
            .filter { it != "" }
            .filter { it != "X" }
            .map { it.toInt() }
            .fold(0) { acc, i -> acc + i }

        return result.first * result.second
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2021/Day04_test")
    check(part1(testInput) == 4512)

    val input = readInput("2021/Day04")

    println("part1:" + part1(input))//46464
    check(part2(testInput) == 1924)//1924
    println("part2:" + part2(input))//25925
}
