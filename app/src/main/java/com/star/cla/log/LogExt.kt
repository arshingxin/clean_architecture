package com.star.cla.log

import com.star.cla.MainApplication
import com.star.cla.extension.*
import com.star.data.extension.get
import timber.log.Timber
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

private fun reloadPath() = "${MainApplication.getExternalFilePath()}/log/${
    System.currentTimeMillis().toTimeString("yyyyMMdd")
}"

fun logStar(tag: String, msg: String?) {
    Timber.d("[$tag][star debug]${msg}")
}

fun logStarError(tag: String, msg: String?) {
    logStar(tag, msg.toString())
    Timber.e("[$tag][star debug error]$msg")
}

fun log(tag: String, msg: String) {
    Timber.d("[$tag]$msg")
}

fun logError(tag: String, msg: String) {
    logStar(tag, msg)
    Timber.e("[$tag]$msg")
    logToFile(tag, msg, LogType.Error)
}

fun logException(tag: String, msg: String) {
    logError(tag, msg)
    logToFile(tag, msg, LogType.Exception)
}

fun Throwable.log(tag: String) {
    logException(tag, message.toString())
}

fun logToFile(tag: String, msg: String, logType: LogType = LogType.Log) {
    logStar(tag, msg)
    writeToLogFile(
        tag,
        msg,
        reloadPath(),
        logType
    )
}

sealed class LogType {
    object Log : LogType()
    object Error : LogType()
    object Exception : LogType()
}

fun writeToLogFile(tag: String, msg: String, folderPath: String, logType: LogType = LogType.Log) {
    if (MainApplication.getExternalFilePath().isEmpty()) return
    val folder = File(folderPath)
    folder.createDirIfNotExists()
    val file = File(
        folder,
        "${
            System.currentTimeMillis().toTimeString("yyyyMMdd")
        }.${
            when (logType) {
                is LogType.Log -> "log"
                is LogType.Error -> "error"
                is LogType.Exception -> "exception"
            }
        }.${getFileCount(logType)}.txt"
    )
    if (!file.exists()) file.createNewFile()
    // 單個檔案不超過4mb
    if (file.length() / 1024 > 4096) {
        var count = getFileCount(logType)
        count++
        saveFileCount(count, logType)
    }
    var outputStreamWriter: OutputStreamWriter? = null
    var bufferWriter: BufferedWriter? = null
    try {
        val fot = FileOutputStream(file, true)
        outputStreamWriter = OutputStreamWriter(fot)
        bufferWriter = BufferedWriter(outputStreamWriter)
        // write the contents to the file
        bufferWriter.write(
            "*** ${
                System.currentTimeMillis().toTimeString()
            } TAG[$tag<${Thread.currentThread().stackTrace[4].lineNumber}>]:$msg"
        )
        bufferWriter.append("\r\n\n")
    } catch (e: Exception) {
        logStarError(tag, e.message)
    } finally {
        bufferWriter?.flush()
        outputStreamWriter?.close()
    }
}

private fun getFileCount(logType: LogType) = MainApplication.getSharePreferences()?.get(
        "${System.currentTimeMillis().toTimeString("yyyyMMdd")}_${
            when (logType) {
                is LogType.Log -> "log"
                is LogType.Error -> "error"
                is LogType.Exception -> "exception"
            }
        }_count", 0
    ) ?: 0

private fun saveFileCount(count: Int, logType: LogType) {
    MainApplication.getSharePreferences()
        ?.set(
            "${System.currentTimeMillis().toTimeString("yyyyMMdd")}_${
                when (logType) {
                    is LogType.Log -> "log"
                    is LogType.Error -> "error"
                    is LogType.Exception -> "exception"
                }
            }_count", count
        )
}
