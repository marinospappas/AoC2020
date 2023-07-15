package mpdev.springboot.aoc2020.solutions.day10

import mpdev.springboot.aoc2020.utils.Bfs
import mpdev.springboot.aoc2020.utils.Graph

class JoltAdapters(input: List<String>) {

    var data: List<Int>
    var graph: Graph<Int>
    private val bfs = Bfs<Int>()

    init {
        data = input.map { it.toInt() }
        val maxJolt = data.max()+3
        data = data.toMutableList().also { it.add(0) }.also { it.add(maxJolt) }.sorted()
        graph = Graph()
        updateNodes()
    }

    fun getAllPaths(a: Int, b: Int): List<List<Int>> {
        return bfs.allPaths(graph[a], graph[b]).map { vrtx -> vrtx.map { it.getId() } }
    }

    fun getAllCombinations(): Long {
        val pathsCountList = mutableListOf<Int>()
        var i1 = 0
        while (i1 < data.size-1) {
            if (data[i1+1] - data[i1] == 3) {   // if diff is 3, there is only 1 path
                ++i1
                continue
            }
            var i2 = data.size - 1     // else find the next index where diff is 3 and find all paths in between
            for (i in (i1+1) .. data.size-2)
                if (data[i+1] - data[i] == 3) {
                    i2 = i
                    break
                }
            pathsCountList.add(bfs.allPaths(graph[data[i1]], graph[data[i2]]).count())
            i1 = i2 + 1
        }
        return pathsCountList.fold(1L) { acc, i -> acc * i }
    }

    private fun updateNodes() {
        data.forEach { node -> graph.addNode(node) }
        data.forEach { node ->
            (1..3).forEach {
                if (data.contains(node+it))
                    graph.connect(node, node + it)
            }
        }
    }

    override fun toString() = bfs.graphToString(graph, graph[0])
}