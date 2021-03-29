package org.fuusio.kaavio.node.state

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.node.stream.StringInjector
import org.fuusio.kaavio.node.stream.StringSink
import org.junit.Assert
import org.junit.Test

class LiveDataTest : KaavioTest() {

    @Test
    fun `Test LiveData with a received value`() {
        // Given
        val liveData = LiveData<String>()
        val injector = StringInjector()
        val sink = StringSink()

        injector.output connect liveData.input
        liveData.output connect sink.input

        // Then
        Assert.assertFalse(liveData.hasObservers())

        // When
        GlobalScope.launch {
            injector.inject(FOO)

            // Then
            Assert.assertTrue(liveData.hasValue())
            Assert.assertTrue(sink.hasValue())
            Assert.assertEquals(FOO, liveData.state)
            Assert.assertEquals(FOO, sink.value)
        }

        // When
        // TODO liveData.observe(mockk<LifecycleOwner>(),)
    }
}