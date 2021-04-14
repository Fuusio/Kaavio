package org.fuusio.kaavio.graph

import org.fuusio.kaavio.testbench.GraphTestBench

/**
 * An example of a [GraphTestBench] based test class for [SignUpGraph].
 */
internal class SignUpGraph2Test : GraphTestBench<SignUpGraph>() {

    /**
     * Returns the test cases as a [Map] where:
     *  * The map key is a [List] of input values that are injected to node inputs returned by
     *  [injectionInputs], and
     *  * The map value is a [List] of expected output values transmitted by the node outputs
     *  returned by [probedOutputs].
     */
    override fun testCases() = cases(

        inputValues(none, "foo@bar.com", "foo@bar.com") to
                outputValues(none, true, true, none, none, none),

        inputValues("foo") to
                outputValues(true, none, none, none, none, none),

        inputValues("foo", "foo@bar.com", "foo@bar.com", "abcd1234","abcd1234") to
                outputValues(true, true, true, true, true, true),

        inputValues("foo", "foo(at)bar.com", "foo@bar.com", "abcd1234","abcd1234") to
                outputValues(true, false, false, true, true, false),

        inputValues("f", "foo@bar.com", "foo@baz.com", "abcd4321","abcd1234") to
                outputValues(false, true, false, true, false, false),
    )

    /**
     * Returns a [List] of [org.fuusio.kaavio.Input]s for injecting test case values.
     */
    override fun injectionInputs() = inputs(
            graph.userName.input,
            graph.email1.input,
            graph.email2.input,
            graph.password1.input,
            graph.password2.input,
        )

    /**
     * Returns a [List] of [org.fuusio.kaavio.Output]s for capturing the output values to be tested
     * against defined expected values.
     */
    override fun probedOutputs() = outputs(
            graph.userNameValid.output,
            graph.emailValid.output,
            graph.emailsEqual.output,
            graph.passwordValid.output,
            graph.passwordsEqual.output,
            graph.allInputsValid.output,
        )

    /**
     * This method can be used to replace actual nodes in the given [graph] with mocked nodes.
     */
    override fun mockNodes(graph: SignUpGraph) {
        // Nothing to mock for these test cases
    }

    /**
     * Returns the [Graph] implementation to be tested.
     */
    override fun graph() = SignUpGraph()
}