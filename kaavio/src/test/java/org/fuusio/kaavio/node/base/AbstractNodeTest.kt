package org.fuusio.kaavio.node.base

import org.fuusio.kaavio.KaavioTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@DisplayName("Given AbstractNode implementation")
internal class AbstractNodeTest : KaavioTest() {

    class IntInputsNode : AbstractNode() {
        val input1 = inputOf<Int>()
        val input2 = inputOf<Int>()
        val input3 = inputOf<Int>()

        var isOnFiredInvoked = false

        override fun onFired() {
            isOnFiredInvoked = true
        }
    }

    @DisplayName("When receiving value via input1")
    @Nested
    inner class OnReceiveInput1Cases {

        // Test subject
        private val node = IntInputsNode()

        @BeforeEach
        fun beforeCase() {
            node.input1.onReceive(1)
        }

        @Test
        @DisplayName("Then the method onFired should not be invoked")
        fun case1() {
            assertFalse(node.isOnFiredInvoked)
        }
    }

    @DisplayName("When receiving values via input1 and input2")
    @Nested
    inner class OnReceiveInput1Input2Cases {

        // Test subject
        private val node = IntInputsNode()

        @BeforeEach
        fun beforeCase() {
            node.input1.onReceive(1)
            node.input2.onReceive(2)
        }

        @Test
        @DisplayName("Then the method onFired should not be invoked")
        fun case1() {
            assertFalse(node.isOnFiredInvoked)
        }
    }

    @DisplayName("When receiving value via all inputs")
    @Nested
    inner class OnReceiveAllInputsCases {

        // Test subject
        private val node = IntInputsNode()

        @BeforeEach
        fun beforeCase() {
            node.input1.onReceive(1)
            node.input2.onReceive(2)
            node.input3.onReceive(2)
        }

        @Test
        @DisplayName("Then the method onFired should be invoked")
        fun case1() {
            assertTrue(node.isOnFiredInvoked)
        }
    }
}