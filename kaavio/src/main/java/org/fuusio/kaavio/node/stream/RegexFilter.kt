package org.fuusio.kaavio.node.stream

import org.fuusio.kaavio.SingleInputSingleOutputNode

/**
 * [RegexFilter] implements a node that uses the given regular expression [pattern] to filter
 * the [String] values received by the node. The received [String] value is transmitted to output
 * only if the value matches to the specified pattern.
 */
class RegexFilter(private val pattern: Regex, name: String? = null)
    : SingleInputSingleOutputNode<String,String>(name) {

    constructor(pattern: String, name: String? = null) : this(Regex(pattern), name)

    override fun onFired() {
        val value = input.value
        if (pattern.matches(value)) emit(value)
    }
}