package `2021`

import readInput

fun main() {
    val compleLineRegec = """\*(\||[0-9]|X)*(((\|X)(\|(.){1,3}){4}){4}(\|X)|\|(\|X){5}(\|){2})(\||[0-9]|X)*\*""".toRegex()
    val replaceSpacesRegex = """\s+""".toRegex()

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
        return input.replace("*", "").split("|")
            .filter { it != "" }
            .filter { it != "X" }
            .map { it.toInt() }
            .fold(0) { acc, i -> acc + i }
    }

    fun part1(input: List<String>): Int {
        var current = "*|${toRaster(input)}|*"
        val turns = input[0].split(",")
            .map { it.toInt() }
        turns.forEach() { number ->
            current = current.replace("|$number|", "|X|")
            if (current.contains(compleLineRegec)) {
                return number * count(compleLineRegec.findAll(current).iterator().next().value)
            }
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        var current = "*|${toRaster(input)}|*"
        val turns = input[0].split(",")
            .map { it.toInt() }
        turns.forEach() { number ->
            current = current.replace("|$number|", "|X|")
            if (current.contains(compleLineRegec)) {
                val previous = current
                current = current.replace(compleLineRegec, "*")
                if (current.replace("*", "").isBlank()) {
                    return number * count(compleLineRegec.findAll(previous).iterator().next().value)
                }
            }
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
