package org.fuusio.kaavio

class MockNode : Node() {

    var onInputReceived = false

    override fun onInputReceived() {
        super.onInputReceived()
        onInputReceived = true
    }

    override fun onFired() {}
}