package mpdev.springboot.aoc2020.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DfsTest {

    @Test
    fun `Builds Graph and Converts to String`() {
        val graph = graph1()
        val dfs = Dfs<Int>()
        println(dfs.graphToString(graph, graph[0]))
        assertThat(graph.getNodes().size).isEqualTo(8)
        assertThat(graph[0].getConnectedNodes().size).isEqualTo(3)
        assertThat(graph[1].getConnectedNodes().size).isEqualTo(2)
        assertThat(graph[2].getConnectedNodes().size).isEqualTo(3)
        assertThat(graph[3].getConnectedNodes().size).isEqualTo(1)
        assertThat(graph[4].getConnectedNodes().size).isEqualTo(2)
        assertThat(graph[5].getConnectedNodes().size).isEqualTo(0)
        assertThat(graph[6].getConnectedNodes().size).isEqualTo(1)
        assertThat(graph[7].getConnectedNodes().size).isEqualTo(0)
    }

    @Test
    fun `Finds All Paths (Graph)`() {
        val graph = graph1()
        val dfs = Dfs<Int>()
        val paths = dfs.allPaths(graph[0], graph[7]).map { it.map { n -> n.getId() } }
        paths.forEach { path -> println(path) }
        assertThat(paths.size).isEqualTo(6)
    }

    private fun graph1() = Graph<Int>()
        .also { graph ->
            (0..7).forEach { graph.addNode(it) }
            graph.connect(0, 1)
            graph.connect(0, 2)
            graph.connect(0, 3)
            graph.connect(1, 4)
            graph.connect(1, 5)
            graph.connect(2, 5)
            graph.connect(2, 6)
            graph.connect(2, 7)
            graph.connect(3, 6)
            graph.connect(4, 5)
            graph.connect(5, 7)
            graph.connect(6, 7)
        }
}