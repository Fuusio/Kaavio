package org.fuusio.kaavio.testbench

import org.fuusio.kaavio.Kaavio
import org.fuusio.kaavio.Node
import org.fuusio.kaavio.input.DebugActionInput
import org.fuusio.kaavio.input.DebugInput
import org.fuusio.kaavio.output.DebugOutput
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class NodeTestBench : TestBench() {

    @BeforeAll
    fun beforeAll() {
        Kaavio.isDebugMode = true
    }

    @AfterAll
    fun afterAll() {
        Kaavio.isDebugMode = false
    }

    companion object {
        fun getInputAndOutputNames(node: Node) {
            node::class.memberProperties.forEach { property ->
                val field = property.javaField

                if (field != null) {
                    field.isAccessible = true

                    when (val port = field.get(node)) {
                        is DebugActionInput<*> -> {
                            port.name = property.name
                        }
                        is DebugInput<*> -> {
                            port.name = property.name
                        }
                        is DebugOutput<*> -> {
                            port.name = property.name
                        }
                    }
                }
            }
        }
    }
}