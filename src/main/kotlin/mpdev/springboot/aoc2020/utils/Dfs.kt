package mpdev.springboot.aoc2020.utils

import java.util.*

class Dfs<T> {

    /**
     * procedure DFS(G, v) is
     *     let S be a stack
     *     S.push(v)
     *     while S is not empty do
     *         v = S.pop()
     *         if v is not labeled as discovered then
     *             label v as discovered
     *             for all edges from v to w in G.adjacentEdges(v) do
     *                 S.push(w)
     */

    /**
     * traverses the graph and executes function f(T) for every node
     */
    fun traverseGraph(start: Vertex<T>, f: (T) -> Unit = {}) {
        val stack = Stack<Vertex<T>>().also { it.push(start) }
        val visited = mutableListOf<Vertex<T>>()
        while (stack.isNotEmpty()) {
            val current = stack.pop()
            f(current.getId())
            if (!visited.contains(current)) {
                visited.add(current)
                current.getConnectedNodes().forEach { connection ->
                    stack.push(connection)
                }
            }
        }
    }
}