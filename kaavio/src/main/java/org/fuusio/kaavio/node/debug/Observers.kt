package org.fuusio.kaavio.node.debug

import org.fuusio.kaavio.Node
import org.fuusio.kaavio.Output
import org.fuusio.kaavio.output.DebugOutput

class Observers {

    private val probes = mutableMapOf<Node, Observer<*>>()

    /**
     * Returns the [Observer] connected to a given [node].
     */
    operator fun get(node: Node): Observer<*> = probes[node]!!

    /**
     * Returns the latest value received by the [Observer] connected to a given [node].
     */
    fun getValue(node: Node) = probes[node]!!.latestValue

    /**
     * Creates a [Observer] and connects it to given [output]. Returns the created [Observer].
     */
    infix fun <O :Any>connect(output: Output<O>): Observer<O> {
        val probe = Observer(output as DebugOutput<O>)
        probes[output.node] = probe
        output.addReceiver(probe)
        return probe
    }

    /**
     * Tests if the given [Node] has transmitted the specified [value] to its connected [Observer].
     */
    fun hasOutputValue(node: Node, value: Any): Boolean =
        when (val probe = probes[node]) {
            null -> throw IllegalArgumentException("Node ${node.name} is not connected to a Probe.")
            else -> probe.hasValue(value)
        }

    /**
     * Tests if the given [Node] has not transmitted the any value to its connected [Observer].
     */
    fun hasNoOutputValue(node: Node): Boolean =
        when (val probe = probes[node]) {
            null -> throw IllegalArgumentException("Node ${node.name} is not connected to a Probe.")
            else -> !probe.hasValue()
        }
}