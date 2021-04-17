package org.fuusio.kaavio

import io.mockk.verify
import org.junit.jupiter.api.*

@DisplayName("Given SingleOutputNode")
internal class SingleOutputNodeTest : KaavioTest() {

    // Test subject
    private val node =  object : SingleOutputNode<Int>() {
        override fun onFired() {}
    }

    @DisplayName("When transmitting Int value 42 to connected Input")
    @Nested
    inner class TransmitCases {

        private val input = mockInput<Int>()

        @BeforeEach
        fun beforeCase() {
            node.output connect input
            node.output.transmit(42)
        }

        @Test
        @DisplayName("Then connected Input should have the value")
        fun case1() {
           verify { input.onReceive(42) }
        }
    }
}