package com.faceunity.pta_art.evergrande.utils

import java.security.MessageDigest

/**
 * md5加密字符串
 * md5使用后转成16进制变成32个字节
 */
fun String.md5(): String {
    val digest = MessageDigest.getInstance("MD5")
    val result = digest.digest(this.toByteArray())
    //没转16进制之前是16位
    println("result${result.size}")
    //转成16进制后是32字节
    return toHex(result)
}

fun toHex(byteArray: ByteArray): String {
    val result = with(StringBuilder()) {
        byteArray.forEach {
            val hex = it.toInt() and (0xFF)
            val hexStr = Integer.toHexString(hex)
            if (hexStr.length == 1) {
                this.append("0").append(hexStr)
            } else {
                this.append(hexStr)
            }
        }
        this.toString()
    }
    //转成16进制后是32字节
    return result
}