package org.fuusio.kaavio.app.feature.signup.ui

import org.fuusio.kaavio.testbench.GraphTestBench
import org.fuusio.kaavio.testbench.None

/**
 * [SignUpViewModelTest] is a [GraphTestBench] based test class for [SignUpViewModel].
 */
internal class SignUpViewModelTest : GraphTestBench<SignUpViewModel>() {

    /**
     * Returns the test cases as a [Map] where:
     *  * The map key is a [List] of input values that are injected to node inputs returned by
     *  [injectionInputs], and
     *  * The map value is a [List] of expected output values transmitted by the node outputs
     *  returned by [probedOutputs].
     */
    override fun testCases() = cases(
        inputs(None, "foo@bar.com", "foo@bar.com") to
                outputs(None, true, true, None, None, None),
        inputs("foo") to
                outputs(true, None, None, None, None, None),
        inputs("foo", "foo@bar.com", "foo@bar.com", "abcd1234","abcd1234") to
                outputs(true, true, true, true, true, true),
        inputs("foo", "foo(at)bar.com", "foo@bar.com", "abcd1234","abcd1234") to
                outputs(true, false, false, true, true, false),
        inputs("f", "foo@bar.com", "foo@baz.com", "abcd4321","abcd1234") to
                outputs(false, true, false, true, false, false),
    )

    /**
     * Returns a [List] of [org.fuusio.kaavio.Input]s for injecting test case input values.
     */
    override fun injectionInputs() = inputs(
        graph.userName.input,
        graph.email1.input,
        graph.email2.input,
        graph.password1.input,
        graph.password2.input,
    )

    /**
     * Returns a [List] of [org.fuusio.kaavio.Output]s for capturing the **actual** output values to
     * be asserted against defined **expected** values.
     */
    override fun probedOutputs() = outputs(
        graph.isUserNameValid.output,
        graph.isEmailValid.output,
        graph.areEmailsEqual.output,
        graph.isPasswordValid.output,
        graph.arePasswordsEqual.output,
        graph.isSignUpInfoValid.output,
    )

    /**
     * This method can be used to replace actual nodes in the given [graph] with mocked ones.
     * Examples of such nodes to be replaced are, e.g., nodes accessing data stores or performing
     * networking.
     */
    override fun mockNodes(graph: SignUpViewModel) {
        graph.
    }

    /**
     * Returns the [org.fuusio.kaavio.graph.Graph] implementation to be tested.
     */
    override fun graph() = SignUpViewModel()
}