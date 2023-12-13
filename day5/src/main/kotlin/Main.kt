import java.io.File

fun main(args: Array<String>) {
    val data = getData("src/main/resources/input.txt")
    var partOneSeeds = data.first
    val partTwoSeeds = data.first.map { it }.toMutableList()
    val categories = data.second

    partOneSeeds = convertSeedsPartOne(partOneSeeds, categories)
    val partTwoResult = findSmallesNumber(partTwoSeeds, categories)

    val partOneResult = partOneSeeds.min()

    println("Part One: - The lowest location number is $partOneResult")
    println("Part Two: - The lowest location number is $partTwoResult")
}


fun convertSeedsPartOne(seeds: MutableList<Long>, categories: MutableMap<String, MutableList<Triple<Long, Long, Long>>>): MutableList<Long> {
    var changed = false
    for (i in 0 until seeds.size) {
        for (key in categories.keys) {
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
    return seeds
}

fun findSmallesNumber(seeds: MutableList<Long>, categories: MutableMap<String, MutableList<Triple<Long, Long, Long>>>): Long {
    //println(seeds)
    var changed = false
    var smallesNumber: Long = 99999999
    println("Program will count ${seeds.size/2} for loops in varying sizes\nThis will take a while...")
    for (j in 0 until seeds.size step 2) {
        println("Counting to: ${((seeds[j] + seeds[j + 1]) - seeds[j])} in the for loop...")
        for (i in seeds[j] until (seeds[j] + seeds[j + 1])) {
            var seed = i
            for (key in categories.keys) {
                for (triple in categories[key]!!) {
                    if (seed >= triple.second &&
                        seed < (triple.second + triple.third) &&
                        !changed) {
                        seed = ((seed - triple.second) + triple.first)
                        changed = true
                    }
                }
                changed = false
            }

            0
            if (seed < smallesNumber) {
                smallesNumber = seed
            }
        }
    }
    return smallesNumber
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