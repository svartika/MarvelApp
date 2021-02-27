package com.perpy.networkcontroller
import java.math.BigInteger
import java.security.MessageDigest
class MD5: Hasher {
    override fun hashcode(value: String): String {
        val messageDigest = getMD5Digest(value)
        val md5 = BigInteger(1, messageDigest).toString(16)
        return  "0"*(32-md5.length)+md5
    }
    private fun getMD5Digest(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray())

    operator fun String.times(i: Int) = (1..i).fold("") {
        acc, _ -> acc+this
    }
}