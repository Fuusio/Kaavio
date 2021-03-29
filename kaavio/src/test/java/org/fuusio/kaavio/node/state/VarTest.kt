package org.fuusio.kaavio.node.state

import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.node.stream.StringInjector
import org.fuusio.kaavio.node.stream.StringSink
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
}