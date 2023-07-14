package mpdev.springboot.aoc2020.solutions.day10

import mpdev.springboot.aoc2020.utils.Bfs
import mpdev.springboot.aoc2020.utils.Graph

class JoltAdapters(input: List<String>) {

    var data: List<Int>
    var graph: Graph<Int>

    init {
        data = input.map { it.toInt() }
        graph = Graph()
        updateNodes()
    }

    fun getAllPaths(a: Int, b: Int): List<List<Int>> {
        val bfs = Bfs<Int>()
        return bfs.allPaths(graph[a], graph[b]).map { vrtx -> vrtx.map { it.getId() } }
    }

    private fun updateNodes() {
        val fullData = data.toMutableList().also { it.add(0) }.also { it.add(data.max()+3) }
        fullData.forEach { node -> graph.addNode(node) }
        fullData.forEach { node ->
            (1..3).forEach {
                if (fullData.contains(node+it))
                    graph.connect(node, node + it)
            }
        }
    }

    fun graphToString(): String {
        return StringBuilder().also { s ->
            graph.getNodes().keys.forEach { id ->
                s.append("id: $id, connects to:").also {
                    graph.getNodes()[id]?.getConnectedNodes()?.forEach { n -> s.append(" ${n.getId()}") }
                }.also { s.append("\n") }
            }
        }.toString()
    }
}