import java.io.File

fun main(args: Array<String>) {
    val res = partOne("src/main/resources/input.txt")

    print("Part One: - The lowest location number is $res")
}


fun partOne(fileName: String): Long {
    val data = getData(fileName)
    val seeds = data.first
    val categories = data.second

    var changed = false
    for (i in 0 until seeds.size) {
        for (key in categories.keys) {
            //print("- $key ${seeds[i]}, ")
            for (triple in categories[key]!!) {
                if (seeds[i] >= triple.second &&
                    seeds[i] < (triple.second + triple.third) &&
                    !changed) {
                    seeds[i] = ((seeds[i] - triple.second) + triple.first)
                    changed = true
                }
            }
            changed = false
        }
    }
    return seeds.min()
}

fun getData(fileName: String): Pair<MutableList<Long>, MutableMap<String, MutableList<Triple<Long, Long, Long>>>> {
    val categories: MutableMap<String, MutableList<Triple<Long, Long, Long>>> = mutableMapOf()

    val input = readFile(fileName)

    val seedIndex = input.indexOfFirst { it.startsWith("seeds:") }
    val seeds: MutableList<Long> = input[seedIndex]
        .substring("seeds:".length)
        .split(" ")
        .filter { it.isNotEmpty() }
        .map { it.toLong() }
        .toMutableList()

    val seedsToSoilIndex = input.indexOfFirst { it.startsWith("seed-to-soil map:") }
    categories["seed-to-soil"] = emptyList<Triple<Long, Long, Long>>().toMutableList()
    categories["soil-to-fertilizer"] = emptyList<Triple<Long, Long, Long>>().toMutableList()
    categories["fertilizer-to-water"] = emptyList<Triple<Long, Long, Long>>().toMutableList()
    categories["water-to-light"] = emptyList<Triple<Long, Long, Long>>().toMutableList()
    categories["light-to-temperature"] = emptyList<Triple<Long, Long, Long>>().toMutableList()
    categories["temperature-to-humidity"] = emptyList<Triple<Long, Long, Long>>().toMutableList()
    categories["humidity-to-location"] = emptyList<Triple<Long, Long, Long>>().toMutableList()

    var currentMap = "seed-to-soil"
    for (i in seedsToSoilIndex until input.size) {
        if (input[i] == "") {
            if (input[i + 1].endsWith(" map:")) {
                currentMap = input[i + 1].removeSuffix(" map:")
            }
            continue
        }
        else if (input[i].startsWith(currentMap)) {
            continue
        } else {
            categories[currentMap]?.add(input[i]
                .split(" ")
                .map { it.toLong() }.let {
                    Triple(it[0], it[1], it[2])
                })
        }
    }
    return Pair(seeds, categories)
}

fun readFile(fileName: String): List<String>
        = File(fileName).readLines()