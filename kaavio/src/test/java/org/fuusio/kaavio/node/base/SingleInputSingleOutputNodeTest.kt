package org.fuusio.kaavio.node.base

import io.mockk.verify
import org.fuusio.kaavio.Ctx
import org.fuusio.kaavio.KaavioTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue

@DisplayName("Given SingleInputSingleOutputNode")
class SingleInputSingleOutputNodeTest : KaavioTest() {

    // Test subject
    private val node = object : SingleInputSingleOutputNode<Int, Int>() {
        var isOnFiredInvoked = false

        override fun onFired(ctx: Ctx) {
            isOnFiredInvoked = true
            output.transmit(ctx, input.get(ctx))
        }
    }

    private val ctx = Ctx()

    @BeforeEach
    fun beforeEachCase() {
        ctx.clear()
    }

    @DisplayName("When receiving Int value 42 via input")
    @Nested
    inner class OnReceiveCases {

        @BeforeEach
        fun beforeCase() {
            node.input.onReceive(ctx,42)
        }

        @Test
        @DisplayName("Then the input of the SingleInputNode should have a value")
        fun case1() {
            assertTrue(node.input.hasValue(ctx))
        }

        @Test
        @DisplayName("Then the method onFired should have been invoked")
        fun case2() {
            assertTrue(node.isOnFiredInvoked)
        }
    }

    @DisplayName("When transmitting Int value 42 to connected Input")
    @Nested
    inner class TransmitCases {

        private val input = mockInput<Int>()

        @BeforeEach
        fun beforeCase() {
            node.output connect input
            node.output.transmit(ctx,42)
        }

        @Test
        @DisplayName("Then connected Input should have the value")
        fun case1() {
            verify { input.onReceive(ctx, 42) }
        }
    }
}