package com.faceunity.pta_art.evergrande.net

class NetException(val msgCode: Int, val msg: String) : Throwable() {
    override fun toString(): String {
        return "errorCode=" + msgCode + " msg=" + msg
    }
}