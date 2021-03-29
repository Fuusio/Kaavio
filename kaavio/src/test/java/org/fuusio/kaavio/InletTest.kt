package org.fuusio.kaavio

import org.junit.Assert.assertNotNull
import org.junit.Test

class InletTest : KaavioTest() {

    @Test
    fun `Test connecting an transmitter`() {
        // Given
        val transmitter = Output<Int>()
        val inlet = Inlet<Int>(mock())

        // When
        val receiver = inlet connect transmitter

        // Then
        assertNotNull(receiver)
    }
}