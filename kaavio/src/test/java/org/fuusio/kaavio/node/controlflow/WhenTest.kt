package org.fuusio.kaavio.node.controlflow

import org.fuusio.kaavio.Rx
import org.fuusio.kaavio.Tx
import org.fuusio.kaavio.node.debug.Injector
import org.fuusio.kaavio.testbench.SingleInputNodeTestBench
import org.fuusio.kaavio.testbench.toValueOption

internal class WhenTest : SingleInputNodeTestBench<String, Int>() {

    override fun testCases() = mapOf(
        "A" to 1.toValueOption(),
        "B" to 2.toValueOption(),
        "C" to 3.toValueOption(),
        "X" to 4.toValueOption(),
        )

    override fun node(injector: Tx<String>, probe: Rx<Int>) =
        When(listOf("A", "B", "C")).apply {
            val injector1 = Injector<Int>()
            val injector2 = Injector<Int>()
            val injector3 = Injector<Int>()
            val injector4 = Injector<Int>()
            injector1.value = 1
            injector2.value = 2
            injector3.value = 3
            injector4.value = 4
            injector connect input
            caseOutput("A") connect injector1.input
            caseOutput("B") connect injector2.input
            caseOutput("C") connect injector3.input
            elseOutput connect injector4.input
            injector1.output connect probe
            injector2.output connect probe
            injector3.output connect probe
            injector4.output connect probe
        }
}