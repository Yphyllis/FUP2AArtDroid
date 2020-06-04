package com.faceunity.pta_art.evergrande.utils

import java.io.File
import java.nio.charset.Charset

object ConfigFileUtil {

    fun saveConfigFile(filePath: String, fileJson: String): String {
        val file = File(filePath)
        if (!file.exists()) {
            file.delete()
            file.createNewFile()
        }
        file.printWriter(Charset.forName("UTF-8")).use {
            it.println(fileJson)
        }
        return file.absolutePath
    }

    fun readConfigFile(filePath: String): String {
        val file = File(filePath)
        if (file.exists()) {
            return file.readText(Charset.forName("UTF-8"))
        }
        return ""
    }

    fun copyFile(srcPath: String, dstPath: String) {
        if(srcPath == dstPath) return
        File(srcPath).runCatching {
            takeIf { it.exists() }?.inputStream()?.use { inputStream ->
                File(dstPath).outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }.onFailure {
            println(it.message)
        }
    }
}