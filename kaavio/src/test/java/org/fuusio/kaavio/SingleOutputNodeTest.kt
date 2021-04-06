package org.fuusio.kaavio

import org.fuusio.kaavio.node.stream.IntInjector
import org.fuusio.kaavio.node.stream.IntSink
import org.junit.Assert
import org.junit.Test

class SingleOutputNodeTest : KaavioTest() {

    class FooNode : SingleOutputNode<Int>() {
        var input = inputOf<Int>()
        var isFired = false

        override fun onFired() {
            isFired = true
            output.transmit(input.value)
        }
    }

    @Test
    fun `Test FooNode with a received value`() {
        // Given
        val foo = FooNode()
        val injector = IntInjector()
        val sink = IntSink()

        injector.output connect foo.input
        foo.output connect sink.input

        // When
        injector.inject(42)

        // Then
        Assert.assertTrue(foo.isFired)
        Assert.assertTrue(sink.hasValue())
        Assert.assertEquals(42, sink.value)
    }
}