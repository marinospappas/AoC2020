package mpdev.springboot.aoc2020.solutions.day19

import mpdev.springboot.aoc2020.utils.AocException
import mpdev.springboot.aoc2020.utils.Graph

class MessageValidator(input: List<String>) {

    val rules: MutableMap<Int,RuleData<*>> = mutableMapOf()
    val messages: MutableList<String> = mutableListOf()
    val rulesGraph = Graph<RuleData<*>>()

    init {
        processInput(input)
        createRulesGraph()
    }

    fun createRulesGraph() {
        rules.values.forEach { v ->
            if (v is RuleData.StringValue)
                rulesGraph.addNode(RuleData.StringValue(v.data))
            //else
              //  (v.data as RuleData.ListValue).data.forEach { rulesGraph.addNode(RuleData.ListValue(listOf(it))) }
        }
        rulesGraph.getNodes().values.map { it.nodeId }.forEach { n ->
            val connectedId = mutableListOf<Int>()
            // n.forEach { id -> connectedId.addAll(rules[id]!!) }

        }
    }

    /////////////////////////////////////////////////////

    fun processInput(input: List<String>) {
        var state = 0
        input.forEach { line ->
            if (state == 0)
                state = processRule(line)
            else
                messages.add(line)
        }
    }

    fun processRule(line: String): Int {
        if (line.isEmpty())
            return 1
        // 0: 4 1 5
        // 1: 2 3 | 3 2
        // 4: "a"
        try {
            val (id, ruleData) = Regex("""(\d+): (.+)""").find(line)!!.destructured
            if (ruleData.matches(Regex(""""[a-z]+"""")))
                rules[id.toInt()] = RuleData.StringValue(ruleData.removePrefix("\"").removeSuffix("\""))
            else {
                val data = ruleData.split("|").map { it.split(" ").filter { s -> s.isNotEmpty() }.map { i -> i.toInt() } }
                rules[id.toInt()] = RuleData.ListValue(data)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw AocException("bad input line $line")
        }
        return 0
    }
}

sealed class RuleData<T>(val data: T) {
    class StringValue(data: String): RuleData<String>(data)
    class ListValue(data: List<List<Int>>): RuleData<List<List<Int>>>(data)
    override fun toString() = data.toString()
}