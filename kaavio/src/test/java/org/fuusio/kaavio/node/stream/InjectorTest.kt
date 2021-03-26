package org.fuusio.kaavio.node.stream

import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.node.controlflow.Trigger
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class InjectorTest : KaavioTest() {

    @Test
    fun `Test injecting an Int`() {
        // Given
        val injector = IntInjector()
        val sink = IntSink()

        injector.output connect sink.input

        // When
        injector.inject(42)

        // Then
        assertTrue(sink.hasValue())
        assertEquals(42, sink.value)
    }

    @Test
    fun `Test injecting an Int using a Trigger`() {
        // Given
        val injector = IntInjector()
        val sink = IntSink()
        val trigger = Trigger()

        trigger.output connect injector.input
        injector.output connect sink.input
        injector.value = 42

        // When
        trigger.fire()

        // Then
        assertTrue(sink.hasValue())
        assertEquals(42, sink.value)
    }
}