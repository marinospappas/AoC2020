package mpdev.springboot.aoc2020.solutions.day07

import mpdev.springboot.aoc2020.utils.AocException
import mpdev.springboot.aoc2020.utils.Dictionary
import java.util.*

class BagRules(input: List<String>) {

    var rulesList: MutableMap<BagKey,List<Pair<Int,BagKey>>> = mutableMapOf()
    val rootKey = BagKey("root".mapFromDictionary())
    var parentMap: MutableMap<BagKey,MutableList<BagKey>> = mutableMapOf()

    init {
        input.forEach { processInputLine(it) }
        buildRules()
    }

    private fun buildRules() {
        val allChildren = rulesList.values.flatten().map { it.second }.toSet()
        val rootLevelBagIds = rulesList.keys.filter { !allChildren.contains(it) }
        // create a root node
        rulesList[rootKey] = rootLevelBagIds.map { Pair(1,it) }
        // create a parent map (each bag can have "many" parents) - the tree upside-down
        rulesList.forEach { rule ->
            rule.value.forEach { item ->
                val parent = rule.key
                if (parentMap[item.second] != null)
                    parentMap[item.second]!!.add(parent)
                else
                    parentMap[item.second] = mutableListOf(parent)
            }
        }
    }

    fun getAllParentsToRoot(id: BagKey): List<BagKey> {
        // trace all the parents using DFS algorithm
        val stack = Stack<BagKey>()
        val discovered = mutableListOf<BagKey>()
        stack.push(id)
        while (stack.isNotEmpty()) {
            val current = stack.pop()
            if (!discovered.contains(current)) {
                discovered.add(current)
                parentMap[current]?.forEach { child -> stack.push(child) }
            }
        }
        return discovered - setOf(rootKey, id)
    }

    /* BFS version needs debugging (passes unit tests but failing the problem)
    fun getBagContents(id: BagKey): List<Pair<Int,BagKey>> {  // BFS version
        val result = mutableListOf<Pair<Int,BagKey>>()
        val queue = ArrayDeque<Pair<Int,BagKey>>()
        val discovered: MutableSet<Pair<BagKey, BagKey>> = mutableSetOf()
        queue.add(Pair(1, id))
        var parent = BagKey("".mapFromDictionary())
        discovered.add(Pair(parent,id))
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            rulesList[current.second]?.forEach { child ->
                if (!discovered.contains(Pair(current.second, child.second))) {
                    discovered.add(Pair(parent, current.second))
                    queue.add(Pair(current.first * child.first, child.second))
                    result.add(Pair(current.first * child.first, child.second))
                }
            }
            parent = current.second
        }
        return result
    }
     */

    fun getBagContents(id: BagKey, count: Int = 1): List<Pair<Int,BagKey>> {  // Recursive version
        val result = mutableListOf(Pair(count,id))
        rulesList[id]?.forEach { child ->
            result.addAll(
                getBagContents(child.second, child.first).map { Pair(count*it.first, it.second) }
            )
        } ?: return result
        return result
    }

    private fun processInputLine(line: String) {
        val match = Regex("""([a-z ]+) bags contain (.+).""").find(line)
        try {
            val (key, contents) = match!!.destructured
            rulesList[BagKey(key.mapFromDictionary())] = processRuleItems(contents)
        } catch (e: Exception) {
            throw AocException("bad input line $line")
        }
    }

    private fun processRuleItems(input: String): List<Pair<Int,BagKey>> {
        val result = mutableListOf<Pair<Int,BagKey>>()
        input.split(",").forEach { item ->
            if (item == "no other bags")
                return emptyList()
            val match = Regex("""\s*(\d+) (.+) bags*\s*""").find(item)
            val (count, bagKey) = match!!.destructured
            result.add(Pair(count.toInt(), BagKey(bagKey.mapFromDictionary())))
        }
        return result
    }

    fun printRules() {
        println("* Input rules")
        rulesList.forEach { println(it) }
        println("\n* Rules tree")
        printRule(rootKey, rulesList[rootKey]!!, "")
        println("\n* Parent map")
        parentMap.forEach { println(it) }
    }

    private fun printRule(key: BagKey, rule: List<Pair<Int,BagKey>>, prefix: String) {
        println("$key >>")
        rule.forEach { print("$prefix    ${it.first} x "); printRule(it.second, rulesList[it.second]!!, "$prefix    ") }
    }
}

data class BagKey(var id: Int) {
    override fun toString(): String {
        return dictionary.keyFromMappedValue(id)
    }
}

val dictionary = Dictionary()

fun String.mapFromDictionary() = dictionary[this]
