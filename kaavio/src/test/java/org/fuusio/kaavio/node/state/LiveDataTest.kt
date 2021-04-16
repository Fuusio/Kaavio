package org.fuusio.kaavio.node.state

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.node.stream.StringInjector
import org.fuusio.kaavio.node.stream.StringSink
import org.junit.After
import org.junit.Assert
import org.junit.Before

class LiveDataTest : KaavioTest() {

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        // provide the scope explicitly, in this example using a constructor parameter
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    //@Test
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

        runBlockingTest {
           withContext(Dispatchers.Main) {
               injector.inject(FOO)

               // Then
               Assert.assertTrue(liveData.hasValue())
               Assert.assertTrue(sink.hasValue())
               Assert.assertEquals(FOO, liveData.state)
               Assert.assertEquals(FOO, sink.value)
           }
        }

        // When
        //liveData.observe(mockk(),mockk())

        // Then
        // Assert.assertTrue(liveData.hasObservers())
    }

    companion object {
        const val FOO = "foo"
    }
}