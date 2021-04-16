package org.fuusio.kaavio

import org.fuusio.kaavio.KaavioTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Given SingleInputNode")
internal class SingleInputNodeTest : KaavioTest() {

    // Test subject
    private val node = object : SingleInputNode<Int>() {
        var isOnFiredInvoked = false

        override fun onFired() {
            isOnFiredInvoked = true
        }
    }

    @DisplayName("When receiving Int value 42 via input")
    @Nested
    inner class OnReceiveCases {

        @BeforeEach
        fun beforeCase() {
            node.input.onReceive(42)
        }

        @Test
        @DisplayName("Then the input of the SingleInputNode should have a value")
        fun case1() {
            assertTrue(node.input.hasValue())
        }

        @Test
        @DisplayName("Then the method onFired should have been invoked")
        fun case2() {
            assertTrue(node.isOnFiredInvoked)
        }
    }
}