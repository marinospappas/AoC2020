package mpdev.springboot.aoc2020.solutions.day16

import mpdev.springboot.aoc2020.utils.AocException

class TicketScanner(input: List<String>) {

    var rules: List<Rule>
    var tickets: List<Ticket>
    var ownTicket: Ticket

    val fieldsMap = mutableMapOf<String,MutableList<Int>>()      // field name to field index('es)
    private var fieldsRange: IntRange

    init {
        val (rules, ownTicket, tickets) = processInput(input)
        this.rules = rules
        this.ownTicket = ownTicket
        this.tickets = tickets
        fieldsRange = (0 .. ownTicket.data.lastIndex)
    }

    fun getOwnTicketField(name: String) = ownTicket.data[fieldsMap[name]!!.first()]

    fun decodeTicketFields() {
        val validTickets = tickets.filter { it.invalidData.isEmpty() }
        // 1. match all the rules to indexes (some rules may be mapped to more than 1 index)
        rules.forEach { rule ->
            fieldsRange.forEach { fieldIndex ->
                if (validTickets.all { validItemForRule(it.data[fieldIndex], rule) }) {
                    if (fieldsMap[rule.name] != null)  // rule has already matched (we have more than one candidate index)
                        fieldsMap[rule.name]!!.add(fieldIndex)     // in this case add this index to the list
                    else
                        fieldsMap[rule.name] = mutableListOf(fieldIndex)
                }
            }
        }
        // 2. prune the map so that each rule maps to 1 index
        while (fieldsMap.values.any { it.size > 1 }) {
            val uniqueMappings = fieldsMap.values.filter { it.size == 1 }.map { it.first() }
            fieldsMap.filter { it.value.size > 1 }.forEach { it.value.removeAll(uniqueMappings) }
        }
    }

    private fun validItemForRule(item: Int, rule: Rule) = item in rule.range1 || item in rule.range2

    fun updateInvalidTicketData() {
        tickets.forEach { ticket ->
            ticket.data.forEach { item -> if (!validTicketItem(item)) ticket.invalidData.add(item) }
        }
    }

    private fun validTicketItem(item: Int): Boolean {
        rules.forEach { rule -> if (validItemForRule(item, rule)) return true }
        return false
    }

    private fun processInput(input: List<String>): Triple<List<Rule>, Ticket, List<Ticket>> {
        var mode = 0 // read rules
        var res = 0
        val rules: MutableList<Rule> = mutableListOf()
        val tickets: MutableList<Ticket> = mutableListOf()
        val ownTicket = Ticket()
        input.forEach { line ->
            when (mode) {
                0 -> res = processRule(line, rules)
                1 -> res = processOwnTicket(line, ownTicket)
                2 -> res = processOtherTickets(line, tickets)
            }
            mode = res
        }
        return Triple(rules, ownTicket, tickets)
    }

    private fun processRule(line: String, rules: MutableList<Rule>): Int {
        if (line.isEmpty())
            return 1
        // class: 1-3 or 5-7
        val match = Regex("""([a-z ]+): (\d+)-(\d+) or (\d+)-(\d+)""").find(line)
        try {
            val (name, from1, to1, from2, to2) = match!!.destructured
            rules.add(Rule(name, from1.toInt() .. to1.toInt(), from2.toInt() .. to2.toInt()))
        } catch (e: Exception) {
            throw AocException("bad input line $line")
        }
        return 0
    }

    private fun processOwnTicket(line: String, ownTicket: Ticket): Int {
        // your ticket:
        // 7,1,14
        return when (line) {
            "" -> 2
            "your ticket:" -> 1
            else -> {
                ownTicket.data = line.split(",").map { it.toInt() }
                1
            }
        }
    }

    private fun processOtherTickets(line: String, tickets: MutableList<Ticket>): Int {
        // nearby tickets:
        // 7,3,47
        // 40,4,50
        if (line == "nearby tickets:")
            return 2
        tickets.add(Ticket(line.split(",").map { it.toInt() }))
        return 2
    }
}

data class Ticket(var data: List<Int> = emptyList(), var invalidData: MutableList<Int> = mutableListOf())

data class Rule(var name: String, var range1: IntRange, var range2: IntRange)
