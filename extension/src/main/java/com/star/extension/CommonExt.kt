package com.star.extension

import android.content.Context
import java.io.File

private const val TAG = "CommonExt"
private const val DEBUG = false

fun File.readFile(): String {
    if (!exists()) return "${this.absolutePath} 設定檔不存在"
    val lineList = mutableListOf<String>()
    val stringBuffer = StringBuffer()
    useLines { lines -> lines.forEach { lineList.add(it) } }
    lineList.forEachIndexed { index, s ->
        if (index == lineList.size - 1)
            stringBuffer.append(s)
        else
            stringBuffer.append(s).append("\n")
    }
    return stringBuffer.toString().trim()
}

fun File.readFileAndContainKeyWord(keyWord: String): Boolean {
    return readFile().contains(keyWord)
}

fun String.packageNameToAppName(context: Context): String {
    val appApplicationInfo =
        context.packageManager.getApplicationInfo(this, 0)
    return context.packageManager?.getApplicationLabel(
        appApplicationInfo
    ).toString()
}

fun randomInt(start: Int = 1, end: Int = 99) = (start..end).random()

fun randomLong(start: Long = 1, end: Long = 99) = (start..end).random()