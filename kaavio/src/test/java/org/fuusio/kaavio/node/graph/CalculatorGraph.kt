package org.fuusio.kaavio.node.graph

import org.fuusio.kaavio.graph.AbstractGraph
import org.fuusio.kaavio.node.function.Fun2
import org.fuusio.kaavio.node.state.FloatVar

data class CalculatorGraph(
    val inputNumber1: FloatVar = FloatVar(),
    val inputNumber2: FloatVar = FloatVar(),
    val additionOutput: FloatVar = FloatVar(),
    val multiplicationOutput: FloatVar = FloatVar(),
    val addition: Fun2<Float, Float, Float> = Fun2 { number1, number2 -> number1 + number2 },
    val multiplication: Fun2<Float, Float, Float> = Fun2 { number1, number2 -> number1 * number2 },
    val additionResult: FloatVar = FloatVar(),
    val multiplicationResult: FloatVar = FloatVar(),
    ) : AbstractGraph() {
    override fun onConnectNodes() {
        inputNumber1.output connect addition.arg1
        inputNumber2.output connect addition.arg2

        inputNumber1.output connect multiplication.arg1
        inputNumber2.output connect multiplication.arg2

        addition.output connect additionResult.input
        multiplication.output connect multiplicationResult.input
    }
}