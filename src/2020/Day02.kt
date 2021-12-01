package `2020`

import readInput
data class Item(var from : Int, var to : Int, var char : Char, var password: String)
fun main() {
    fun solution(input: List<String>, fn: (Item) -> Boolean): Int {
        val list = input
            .map { it.split(": ") }
            .map { Pair<String, Item>(it[0], Item(
                from = 0,
                to = 0,
                char = ' ',
                password = it[1])) }
            .map {
                val tokens = it.first.split(" ")
                Pair<String, Item>(tokens[0], Item(
                    from = 0,
                    to = 0,
                    char = tokens[1][0],
                    password = it.second.password)) }
            .map {
                val tokens = it.first.split("-")
                Pair<String, Item>(tokens[0], Item(
                    from = tokens[0].toInt() - 1,
                    to = tokens[1].toInt() - 1,
                    char = it.second.char,
                    password = it.second.password)) }
            .map { it.second }
            .filter(fn)

        return list.size

    }
    fun part1(input: List<String>): Int {
        return solution(input) {
            val theChar = it.char
            val from = it.from
            val to = it.to

            val valid = it.password.toCharArray()
                .filter { it == theChar}
            valid.size in from..to
        }
    }

    fun part2(input: List<String>): Int {
        return solution(input){
            val theChar = it.char
            val from = it.from
            val to = it.to

            val valid1 = it.password.toCharArray()[from] == theChar
            val valid2 = it.password.toCharArray()[to] == theChar
            valid1.xor(valid2)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/Day02_test")
    check(part1(testInput) == 0)

    val input = readInput("2020/Day02")
    println("part 1:" + part1(input))
    println("part 2:" + part2(input))
}
