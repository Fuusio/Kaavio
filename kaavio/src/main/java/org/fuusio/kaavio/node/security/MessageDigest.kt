package org.fuusio.kaavio.node.security

import android.util.Base64
import org.fuusio.kaavio.Ctx
import org.fuusio.kaavio.node.base.SingleOutputNode
import java.io.UnsupportedEncodingException

class MessageDigest(
    algorithm: String,
    private val iterations: Int = 2,
    private val encoderFlags: Int = Base64.DEFAULT,
    private val useSaltInput: Boolean = true
) : SingleOutputNode<String>() {

    val message = inputOf<String>()
    val salt = inputOf<String>()

    private val messageDigest = java.security.MessageDigest.getInstance(algorithm)

    override fun onFired(ctx: Ctx) {
        if (!useSaltInput && !salt.hasValue(ctx)) {
            salt.cacheValue(ctx, System.currentTimeMillis().toString())
        }
        output.transmit(ctx, hash(message.get(ctx), salt.get(ctx)))
    }

    private fun hash(message: String, salt: String): String {
        val saltBytes: ByteArray
        val messageBytes: ByteArray
        try {
            saltBytes = salt.toByteArray(charset("UTF-8"))
            messageBytes = message.toByteArray(charset("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException("Unsupported Encoding", e)
        }
        var digestBytes = digest(messageBytes, saltBytes)
        for (i in 0 until iterations - 1) {
            digestBytes = digest(digestBytes, saltBytes)
        }
        return Base64.encodeToString(digestBytes, encoderFlags)
    }

    private fun digest(input: ByteArray, salt: ByteArray): ByteArray {
        messageDigest.update(input)
        return messageDigest.digest(salt)
    }
}