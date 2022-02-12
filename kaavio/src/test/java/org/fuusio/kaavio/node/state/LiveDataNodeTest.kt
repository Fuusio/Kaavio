package org.fuusio.kaavio.node.state

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.fuusio.kaavio.KaavioTest
import org.fuusio.kaavio.node.debug.StringInjector
import org.junit.After
import org.junit.Assert
import org.junit.Before

class LiveDataNodeTest : KaavioTest() {

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        // provide the scope explicitly, in this example using a constructor parameter
        Dispatchers.setMain(testDispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    //@Test
    @ExperimentalCoroutinesApi
    fun `Test LiveData with a received value`() {
        // Given
        val liveData = LiveDataNode<String>()
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
               Assert.assertEquals(FOO, liveData.value)
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