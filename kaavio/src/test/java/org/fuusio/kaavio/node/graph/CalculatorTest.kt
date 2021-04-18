package org.fuusio.kaavio.node.graph

import org.fuusio.kaavio.testbench.NestedGraphNodeTestBench

class CalculatorTest : NestedGraphNodeTestBench<Calculator>() {

    override fun testCases()= cases(
        inputValues(2f, 3f) to outputValues(5f, 6f),
    )

    override fun injectionInputs() = inputs(node.number1, node.number2)

    override fun probedOutputs() = outputs(node.addition, node.multiplication)

    override fun node() = Calculator()
}