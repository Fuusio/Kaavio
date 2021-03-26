package org.fuusio.kaavio

import org.fuusio.kaavio.factory.DefaultInputFactory
import org.fuusio.kaavio.factory.DefaultOutputFactory
import org.fuusio.kaavio.factory.InputFactory
import org.fuusio.kaavio.factory.OutputFactory

object Kaavio {
    var debugMode = false
    var inputFactory: InputFactory = DefaultInputFactory()
    var outputFactory: OutputFactory = DefaultOutputFactory()

    fun <I: Any> actionInput(node: Node, action: (I) -> Unit) =
        if (debugMode) inputFactory.createDebugActionInput(node, action) else inputFactory.createActionInput(node, action)

    fun <I: Any> input(node: Node): Input<I> =
        if (debugMode) inputFactory.createDebugInput(node) else inputFactory.createInput(node)

    fun <O: Any> output(node: Node): Output<O> =
        if (debugMode) outputFactory.createDebugOutput(node) else outputFactory.createOutput(node)
}
