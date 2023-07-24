package mpdev.springboot.aoc2020.solutions.day19

import mpdev.springboot.aoc2020.utils.AocException

class MessageValidator(input: List<String>) {

    companion object {
        const val MATCHED = ""
        const val REPEAT_RULE_ID = 1_999_999
    }

    val rules: MutableMap<Int,RuleData<*>> = mutableMapOf()
    val messages: MutableList<String> = mutableListOf()

    init {
        processInput(input)
    }

    /**
     * match string against rule
     * returns "": when all matched, String: the remaining part of s when the first part of s matched, null when no match
     */
    fun match(s: String, ruleId: Int): String? {
        val rule = rules[ruleId] ?: AocException("rule id $ruleId does not exist")
        if (rule is RuleData.StringValue)
            return if (s.startsWith(rule.data)) s.substring(rule.data.length) else null
        else {
            (rule as RuleData.ListValue).data.forEach { subRule ->
                var stringToMatch = s
                stringToMatch = matchSubRule(stringToMatch, subRule) ?: return@forEach
                return stringToMatch
            }
            return null
        }
    }

    private fun matchSubRule(s: String, ruleIds: List<Int>): String? {
        var stringToMatch = s
        ruleIds.forEach {
            stringToMatch = match(stringToMatch, it) ?: return null
        }
        return stringToMatch
    }

    /**
     * matches rule repeated 1 or more times
     * returns list of substrings left after each match occurs
     * sets up temporary dummy rule to achieve this
     */
    fun matchRepeated(s: String, ruleId: Int): List<String> {
        val ruleIds = mutableListOf(ruleId)
        val result = mutableListOf<String>()
        while (true) {
            rules[REPEAT_RULE_ID] = RuleData.ListValue(listOf(ruleIds))
            val remaining = match(s, REPEAT_RULE_ID) ?: return result
            ruleIds.add(ruleId)
            result.add(remaining)
        }
    }

    /**
     * matches part of the string against rule 1 repeated and the remaining string against rule 2 repeated
     */
    fun match2(s: String, ruleId1: Int, ruleId2: Int): Boolean {
        // find all remaining strings after first rule is repeatedly matched
        val strToMatchAfterRule1 = matchRepeated(s, ruleId1)
        // for each remaining string from above step
        // find all remaining strings after second rule is repeatedly matched
        val strToMatchAfterRule2 = strToMatchAfterRule1.map { matchRepeated(it, ruleId2) }
        strToMatchAfterRule2.indices.forEach { match1Indx ->
            val remainderList = strToMatchAfterRule2[match1Indx]
            strToMatchAfterRule2[match1Indx].indices.forEach { match2Indx ->
                // we have an overall match when the string fully matches both rules
                // and the second rule is matched less times than the first one
                if (remainderList[match2Indx] == MATCHED && match2Indx < match1Indx)
                    return true
            }
        }
        return false
    }

    /////////////////////////////////////////////////////

    private fun processInput(input: List<String>) {
        var state = 0
        input.forEach { line ->
            if (state == 0)
                state = processRule(line)
            else
                messages.add(line)
        }
    }

    private fun processRule(line: String): Int {
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