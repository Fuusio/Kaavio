package org.fuusio.kaavio.node.graph

class Calculator : NestedGraphNode<CalculatorGraph>(CalculatorGraph()) {
    val number1 = delegateInputOf(graph.inputNumber1.input)
    val number2 = delegateInputOf(graph.inputNumber2.input)
    val addition = delegateOutputOf(graph.additionResult.output)
    val multiplication = delegateOutputOf(graph.multiplicationResult.output)
}

