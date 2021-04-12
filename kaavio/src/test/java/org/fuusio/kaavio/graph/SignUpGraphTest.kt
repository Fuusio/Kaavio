package org.fuusio.kaavio.graph

import org.fuusio.kaavio.debug.node.Probes
import org.junit.jupiter.api.*

@DisplayName("Given a SignUpGraph")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SignUpGraphTest : GraphTest() {

    @DisplayName("When receiving: [foo] -> userName")
    @Nested
    inner class InputsCases1 {

        private val graph = SignUpGraph()
        private val probes = Probes()

        @BeforeEach
        fun beforeEachCase() {
            graph.apply {
                probes connect userNameValid.output
                probes connect allInputsValid.output

                activate()

                userName.input.onReceive("foo")
            }
        }

        @Test
        @DisplayName("Then output of the following nodes should be true: userNameValidator; and no value: allInputsValid")
        fun case1() {
            graph.apply {
                probes.assertOutput(userNameValid,true)
                probes.assertNoOutput(allInputsValid)
            }
        }
    }

    @DisplayName("When receiving: [foo] -> userName, [foo@bar.com] -> email1, [bar@foo.com] -> email2, [abcd1234] -> password1, [abcd1234] -> password2")
    @Nested
    inner class InputsCases2 {

        private val graph = SignUpGraph()
        private val probes = Probes()

        @BeforeEach
        fun beforeEachCase() {
            graph.apply {
                probes connect userNameValid.output
                probes connect emailValid.output
                probes connect emailsEqual.output
                probes connect passwordValid.output
                probes connect passwordsEqual.output
                probes connect allInputsValid.output

                activate()

                userName.input.onReceive("foo")
                email1.input.onReceive("foo@bar.com")
                email2.input.onReceive("bar@foo.com")
                password1.input.onReceive("abcd1234")
                password2.input.onReceive("abcd1234")
            }
        }

        @Test
        @DisplayName("Then output of the following nodes should be true: userNameValid, emailValid, passwordsEqual, passwordValid; and false: emailsEqual, allInputsValid")
        fun case1() {
            graph.apply {
                probes.assertOutput(userNameValid,true)
                probes.assertOutput(emailValid,true)
                probes.assertOutput(emailsEqual,false)
                probes.assertOutput(passwordValid,true)
                probes.assertOutput(passwordsEqual,true)
                probes.assertOutput(allInputsValid,false)
            }
        }
    }

    @DisplayName("When receiving: [foo] -> userName, [foo@bar.com] -> email1, [foo@bar.com] -> email2, [abcd1234] -> password1, [abcd1234] -> password2")
    @Nested
    inner class InputsCases3 {

        private val graph = SignUpGraph()
        private val probes = Probes()

        @BeforeEach
        fun beforeEachCase() {
            graph.apply {
                probes connect userNameValid.output
                probes connect emailValid.output
                probes connect emailsEqual.output
                probes connect passwordValid.output
                probes connect passwordsEqual.output
                probes connect allInputsValid.output

                activate()

                userName.input.onReceive("foo")
                email1.input.onReceive("foo@bar.com")
                email2.input.onReceive("foo@bar.com")
                password1.input.onReceive("abcd1234")
                password2.input.onReceive("abcd1234")
            }
        }

        @Test
        @DisplayName("Then output of the following nodes should be true: userNameValid, emailsEqual, emailValid, passwordsEqual, passwordValid, allInputsValid")
        fun case1() {
            graph.apply {
                probes.assertOutput(userNameValid,true)
                probes.assertOutput(emailValid,true)
                probes.assertOutput(emailsEqual,true)
                probes.assertOutput(passwordValid,true)
                probes.assertOutput(passwordsEqual,true)
                probes.assertOutput(allInputsValid,true)
            }
        }
    }
}