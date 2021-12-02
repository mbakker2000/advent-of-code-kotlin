package `2020`

import readInput

fun main() {
    data class Hight(
        var value : Int?,
        var type : String?
    )
    data class Item(
        var byr: Int,
        var iyr: Int,
        var eyr: Int,
        var hgt: String,
        var hcl: String,
        var ecl: String,
        var pid: String,
        var cid: String
    )

    fun solution(input: List<String>, fn: (Item) -> Boolean): Int {
        var concat = ""
        val list = mutableListOf<String>()
        input.forEachIndexed() { index, item ->
            concat += " $item"
            if (item.isBlank()) {
                list.add(concat.trim())
                concat = ""
            }
        }
        var result = list
            .map { st ->
                st
                    .split(" ").associateTo(HashMap<String, String>()) {
                        val (left, right) = it.split(":")
                        left to right
                    }.toMap().let {
                        Item(
                            byr = it.get("byr")?.toIntOrNull() ?: 0,
                            iyr = it.get("iyr")?.toIntOrNull()?: 0,
                            eyr = it.get("eyr")?.toIntOrNull()?: 0,
                            hgt = it.get("hgt")?: "",
                            hcl = it.get("hcl")?: "",
                            ecl = it.get("ecl")?: "",
                            pid = it.get("pid")?: "",
                            cid = it.get("cid")?: "",
                        )
                    }
            }.map(fn)
            .filter { it }
        return result.size
    }

    fun part2(input: List<String>): Int {
        val hgtRegex = """^[0-9]*(cm|in)$""".toRegex()
        val hclRegex = """^#[0-9a-z]{6}$""".toRegex()
        val eclRegex = """^(amb|blu|brn|gry|grn|hzl|oth)$""".toRegex()
        val pidRegex = """^[0-9]{9}$""".toRegex()

        return solution(input){
                item ->
            var hgtNr = false
            if (hgtRegex.matches(item.hgt)) {
                if (item.hgt.contains("cm")) {
                    hgtNr = item.hgt.replace("cm", "").toInt() in 150..193
                } else
                if (item.hgt.contains("in")) {
                    hgtNr = item.hgt.replace("in", "").toInt() in 59..76
                }
            }
            val result = item.byr in 1920..2002 &&
                    item.iyr in 2010..2020 &&
                    item.eyr in 2020..2030 &&
                    hgtNr &&
                    hgtRegex.matches(item.hgt) &&
                    hclRegex.matches(item.hcl) &&
                    eclRegex.matches(item.ecl) &&
                    pidRegex.matches(item.pid)
            result
        }
    }
    fun part1(input: List<String>): Int {
        return solution(input){
                item ->
            val result = item.byr != 0 &&
                    item.iyr != 0 &&
                    item.eyr != 0 &&
                    !item.hgt.isNullOrBlank() &&
                    !item.hcl.isNullOrBlank() &&
                    !item.ecl.isNullOrBlank() &&
                    !item.pid.isNullOrBlank()
            result
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2020/Day04_test")
    check(part1(testInput) == 2)

    val input = readInput("2020/Day04")
    //println("part1:" + part1(input))
    println("part2:" + part2(input))
}
