package org.fuusio.kaavio.graph

import org.fuusio.kaavio.Ctx
import org.fuusio.kaavio.node.debug.Observers
import org.junit.jupiter.api.*

@DisplayName("Given a SignUpGraph")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SignUpGraphTest : GraphTest() {

    private val ctx = Ctx()

    @DisplayName("When receiving: [foo] -> userName")
    @Nested
    inner class InputsCases1 {

        private val graph = SignUpGraph()
        private val observers = Observers()

        @BeforeEach
        fun beforeEachCase() {
            ctx.clear()
            graph.apply {
                observers connect userNameValid.output
                observers connect allInputsValid.output

                activate()

                userName.input.onReceive(ctx,"foo")
            }
        }

        @Test
        @DisplayName("Then output of the following nodes should be true: userNameValidator; and no value: allInputsValid")
        fun case1() {
            graph.apply {
                observers.assertOutput(userNameValid,true)
                observers.assertNoOutput(allInputsValid)
            }
        }
    }

    @DisplayName("When receiving: [foo] -> userName, [foo@bar.com] -> email1, [bar@foo.com] -> email2, [abcd1234] -> password1, [abcd1234] -> password2")
    @Nested
    inner class InputsCases2 {

        private val graph = SignUpGraph()
        private val observers = Observers()

        @BeforeEach
        fun beforeEachCase() {
            ctx.clear()
            graph.apply {
                observers connect userNameValid.output
                observers connect emailValid.output
                observers connect emailsEqual.output
                observers connect passwordValid.output
                observers connect passwordsEqual.output
                observers connect allInputsValid.output

                activate()

                userName.input.onReceive(ctx,"foo")
                email1.input.onReceive(ctx,"foo@bar.com")
                email2.input.onReceive(ctx,"bar@foo.com")
                password1.input.onReceive(ctx,"abcd1234")
                password2.input.onReceive(ctx,"abcd1234")
            }
        }

        @Test
        @DisplayName("Then output of the following nodes should be true: userNameValid, emailValid, passwordsEqual, passwordValid; and false: emailsEqual, allInputsValid")
        fun case1() {
            graph.apply {
                observers.assertOutput(userNameValid,true)
                observers.assertOutput(emailValid,true)
                observers.assertOutput(emailsEqual,false)
                observers.assertOutput(passwordValid,true)
                observers.assertOutput(passwordsEqual,true)
                observers.assertOutput(allInputsValid,false)
            }
        }
    }

    @DisplayName("When receiving: [foo] -> userName, [foo@bar.com] -> email1, [foo@bar.com] -> email2, [abcd1234] -> password1, [abcd1234] -> password2")
    @Nested
    inner class InputsCases3 {

        private val graph = SignUpGraph()
        private val observers = Observers()

        @BeforeEach
        fun beforeEachCase() {
            ctx.clear()

            graph.apply {
                observers connect userNameValid.output
                observers connect emailValid.output
                observers connect emailsEqual.output
                observers connect passwordValid.output
                observers connect passwordsEqual.output
                observers connect allInputsValid.output

                activate()

                userName.input.onReceive(ctx,"foo")
                email1.input.onReceive(ctx,"foo@bar.com")
                email2.input.onReceive(ctx,"foo@bar.com")
                password1.input.onReceive(ctx,"abcd1234")
                password2.input.onReceive(ctx,"abcd1234")
            }
        }

        @Test
        @DisplayName("Then output of the following nodes should be true: userNameValid, emailsEqual, emailValid, passwordsEqual, passwordValid, allInputsValid")
        fun case1() {
            graph.apply {
                observers.assertOutput(userNameValid,true)
                observers.assertOutput(emailValid,true)
                observers.assertOutput(emailsEqual,true)
                observers.assertOutput(passwordValid,true)
                observers.assertOutput(passwordsEqual,true)
                observers.assertOutput(allInputsValid,true)
            }
        }
    }
}