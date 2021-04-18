package org.fuusio.kaavio.testbench

import org.fuusio.kaavio.Kaavio
import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.Node
import org.fuusio.kaavio.debug.node.Probe
import org.fuusio.kaavio.graph.Graph
import org.fuusio.kaavio.input.DebugActionInput
import org.fuusio.kaavio.input.DebugInput
import org.fuusio.kaavio.output.DebugOutput
import org.fuusio.kaavio.util.Quadruple
import org.fuusio.kaavio.util.Quintuple
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

/**
 * [SingleOutputNodeTestBench] provides an abstract base class for implementing concrete test bench classes
 * for testing [Node] implementations.
 *
 * * [O] the type parameter for output value type
 */

abstract class SingleOutputNodeTestBench<O: Any> : NodeTestBench() {

    protected fun assertTestCase(node: Node, probe: Probe<O>, inputValues: List<Any>, valueOption: ValueOption<O>) {
        when (valueOption) {
            is Value, is Trigger -> {
                Assertions.assertTrue(
                    probe.hasValue(),
                    "Node '${Graph.getNodeName(node)}' did not output a value."
                )
                val value = probe.latestValue
                Assertions.assertEquals(
                    valueOption.value,
                    value,
                    invalidOutputMessage(node, inputValues, valueOption.value!!, value!!)
                )
            }
            is None -> {
                Assertions.assertFalse(
                    probe.hasValue(),
                    "Node '${Graph.getNodeName(node)}' should not have outputted a value."
                )
            }
        }
    }

    private fun invalidOutputMessage(
        node: Node,
        inputValues: List<Any>,
        expectedValue: O,
        actualValue: O
    ): String {
        val isSingleInput = inputValues.size == 1
        val nodeName = Graph.getNodeName(node)
        val valuesList = StringBuilder(if (isSingleInput) "" else "(")
        inputValues.forEach { value ->
            if (!isSingleInput && valuesList.length > 1) {
                valuesList.append(", ")
            }
            valuesList.append(valueToString(value))
        }
        if (!isSingleInput) valuesList.append(")")
        return "Test case for node '$nodeName' failed: For input ${if (isSingleInput) "value" else "values"}: $valuesList, expected output value: ${valueToString(expectedValue)}. Actual output value: ${valueToString(actualValue)}"
    }

    fun <T1, T2> pair(first: T1, second: T2): Pair<T1, T2> = Pair(first, second)

    fun <T1, T2, T3> triple(first: T1, second: T2, third: T3): Triple<T1, T2, T3> = Triple(first, second, third)

    fun <T1, T2, T3, T4> quadruple(first: T1, second: T2, third: T3, fourth: T4): Quadruple<T1, T2, T3, T4> = Quadruple(first, second, third, fourth)

    fun <T1, T2, T3, T4, T5> quintuple(first: T1, second: T2, third: T3, fourth: T4, fifth: T5): Quintuple<T1, T2, T3, T4, T5> = Quintuple(first, second, third, fourth, fifth)
}