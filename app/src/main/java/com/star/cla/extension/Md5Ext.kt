package com.star.cla.extension

import com.star.cla.log.logStar
import com.star.cla.log.logStarError
import java.io.File
import java.io.FileInputStream
import java.math.BigInteger
import java.security.MessageDigest

private val TAG = "MD5Ext"
private val DEBUG = false

fun String?.compareMD5(file: File, name: String? = "", exceptionAction: ((String) -> Unit)? = null): Boolean {
    if (this.isNullOrEmpty() || !file.exists()) {
        logStarError(TAG, "compareMD5 file:: $file")
        logStarError(TAG, "compareMD5 server 的MD5:: $this")
        logStarError(TAG, "compareMD5 本地檔案是否存在:: ${file.exists()}, filePath:: ${file.absolutePath}")
        logStarError(TAG, "compareMD5 本地檔案名稱:: $name")
        if (file.exists()) file.delete()
        return false
    }
    if(DEBUG) logStar(TAG, "compareMD5 server的MD5:: $this")
    val md = MessageDigest.getInstance("MD5")
    var inputStream: FileInputStream? = null
    var fileMD5: String? = null
    try {
        inputStream = FileInputStream(file)
    } catch (e: Exception) {
        logStarError(TAG, e.message.toString())
    }

    if (inputStream != null) {
        val buffer = ByteArray(8192)
        var read: Int
        try {
            while (inputStream.read(buffer).also { read = it } > 0) {
                md.update(buffer, 0, read)
            }
            var md5Sum = md.digest()
            val bigInt = BigInteger(1, md5Sum)
            fileMD5 = bigInt.toString(16)
            fileMD5 = String.format("%32s", fileMD5).replace(' ', '0')
        } catch (e: Exception) {
            val error = e.message
            if (error?.contains("End of String") == true) exceptionAction?.invoke(error)
            logStarError(TAG, "compareMD5 $name is exception:: ${e.message}")
        } finally {
            inputStream.close()
        }
        return if (fileMD5.isNullOrEmpty()) {
            logStarError(TAG, "compareMD5 本地檔案 $name 的 MD5:: $fileMD5")
            false
        } else {
            if (fileMD5.length < 32) {
                while (32 - fileMD5!!.length > 0) {
                    fileMD5 = "0$fileMD5"
                }
            }
            val match = this.equals(fileMD5, ignoreCase = true)
            if (match) file.absolutePath.updateLastModifyTime()
            else file.delete()
            match
        }
    } else {
        logStarError(TAG, "compareMD5 $name inputStream:: $inputStream")
        return false
    }
}

fun String?.compareMD5(localFileUrl: String?, name: String? = "", exceptionAction: ((String) -> Unit)? = null): Boolean {
    return if (this.isNullOrEmpty()) {
        logStarError(TAG, "server md5 is null or empty")
        false
    } else if (localFileUrl.isNullOrEmpty()) {
        logStarError(TAG, "localFileUrl is null or empty")
        false
    } else if (!File(localFileUrl).exists()) {
        logStarError(TAG, "local file is not exist")
        false
    } else {
        checkMd5(localFileUrl, name, exceptionAction)
    }
}

fun String.checkMd5(localFileUrl: String, name: String? = "", exceptionAction: ((String) -> Unit)? = null): Boolean {
    val file = File(localFileUrl)
    if(DEBUG) logStar(TAG, "compareMD5 server的MD5:: $this")
    val md = MessageDigest.getInstance("MD5")
    var inputStream: FileInputStream? = null
    var fileMD5: String? = null
    try {
        inputStream = FileInputStream(file)
    } catch (e: Exception) {
        logStarError(TAG, e.message.toString())
    }

    if (inputStream != null) {
        val buffer = ByteArray(8192)
        var read: Int
        try {
            while (inputStream.read(buffer).also { read = it } > 0) {
                md.update(buffer, 0, read)
            }
            var md5Sum = md.digest()
            val bigInt = BigInteger(1, md5Sum)
            fileMD5 = bigInt.toString(16)
            fileMD5 = String.format("%32s", fileMD5).replace(' ', '0')
        } catch (e: Exception) {
            val error = e.message
            if (error?.contains("End of String") == true) exceptionAction?.invoke(error)
            logStarError(TAG, "compareMD5 $name is exception:: ${e.message}")
        } finally {
            inputStream.close()
        }
        return if (fileMD5.isNullOrEmpty()) {
            logStarError(TAG, "compareMD5 本地檔案 $name 的 MD5:: $fileMD5")
            false
        } else {
            if (fileMD5.length < 32) {
                while (32 - fileMD5!!.length > 0) {
                    fileMD5 = "0$fileMD5"
                }
            }
            val match = this.equals(fileMD5, ignoreCase = true)
            if (match) file.absolutePath.updateLastModifyTime()
            else file.delete()
            match
        }
    } else {
        logStarError(TAG, "compareMD5 $name inputStream:: $inputStream")
        return false
    }
}