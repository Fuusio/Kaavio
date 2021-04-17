package org.fuusio.kaavio

import io.mockk.verify
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@DisplayName("Given Inlet")
internal class InletTest : KaavioTest() {

    // Test subject
    private val inlet = Inlet<Int>(mockNode())

    @DisplayName("When connecting to an Output")
    @Nested
    inner class ConnectCases {

        private var receiver: Rx<Int>? = null
        private val output = mockOutput<Int>()

        @BeforeEach
        fun beforeCase() {
            receiver = inlet connect output
        }

        @Test
        @DisplayName("Then Output should have the returned Rx instance as a receiver")
        fun case1() {
            assertNotNull(receiver)
            verify { output.addReceiver(receiver!!) }
        }
    }
}