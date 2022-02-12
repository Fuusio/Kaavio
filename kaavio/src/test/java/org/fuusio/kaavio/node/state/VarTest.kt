package org.fuusio.kaavio.node.state

import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.node.debug.IntProbe
import org.fuusio.kaavio.node.function.Fun2
import org.fuusio.kaavio.node.debug.IntInjector
import org.fuusio.kaavio.node.debug.StringInjector
import org.junit.Assert
import org.junit.Test

class VarTest : KaavioTest() {

    @Test
    fun `Test Var with a received value`() {
        // Given
        val `var` = Var<String>()
        val injector = StringInjector()
        val sink = StringSink()

        injector.output connect `var`.input
        `var`.output connect sink.input

        // When
        injector.inject("foo")

        // Then
        Assert.assertTrue(`var`.hasValue())
        Assert.assertTrue(sink.hasValue())
        Assert.assertEquals("foo", `var`.value)
        Assert.assertEquals("foo", sink.value)
    }

    @Test
    fun `Test Var as StatefulNode`() {
        // Given
        val intVar = IntVar(2)
        val injector = IntInjector()
        val probe = IntProbe()
        val funNode = Fun2<Int, Int, Int> { int1: Int, int2: Int -> int1 + int2}

        injector.output connect funNode.arg1
        intVar.output connect funNode.arg2
        funNode.output connect probe

        // When
        injector.inject(40)

        // Then
        Assert.assertTrue(intVar.hasValue())
        Assert.assertTrue(probe.hasValue())

        probe.hasValue(42)
    }
}