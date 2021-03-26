package org.fuusio.kaavio.node.navigation

import androidx.annotation.IdRes
import org.fuusio.kaavio.SingleInputNode

class Navigation(@IdRes private val resId: Int, name: String? = null) : SingleInputNode<Unit>() {

    override fun onFired() {
        TODO("Not yet implemented")
    }
}
