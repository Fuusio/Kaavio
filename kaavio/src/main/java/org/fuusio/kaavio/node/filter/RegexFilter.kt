package org.fuusio.kaavio.node.filter

import org.fuusio.kaavio.SingleInputSingleOutputNode

class RegexFilter(private val pattern: Regex, name: String? = null)
    : SingleInputSingleOutputNode<String,String>(name) {

    constructor(pattern: String, name: String? = null) : this(Regex(pattern), name)

    override fun onFired() {
        val value = input.value
        if (pattern.matches(value)) emit(value)
    }
}