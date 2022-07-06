package com.star.extension

import android.os.Build
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.util.Log
import androidx.core.text.HtmlCompat
import com.star.extension.log.log
import com.star.extension.log.logStar
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

private const val TAG = "StringExt"
private const val DEBUG = false

fun String.md5() = encrypt(this, "MD5")

fun String.equalsIgnoreCase(other: String) = this.lowercase(Locale.ROOT).contentEquals(
    other.lowercase(
        Locale.ROOT
    )
)

private fun encrypt(string: String?, type: String): String {
    val bytes = MessageDigest.getInstance(type).digest(string!!.toByteArray())
    return bytes2Hex(bytes)
}

internal fun bytes2Hex(bts: ByteArray): String {
    var des = ""
    var tmp: String
    for (i in bts.indices) {
        tmp = Integer.toHexString(bts[i].toInt() and 0xFF)
        if (tmp.length == 1) {
            des += "0"
        }
        des += tmp
    }
    return des
}

private fun isHttpUrl(url: String?) =
    null != url && url.lowercase(Locale.ROOT).startsWith("http://")

private fun isHttpsUrl(url: String?) =
    null != url && url.lowercase(Locale.ROOT).startsWith("https://")

fun String.getFileExt(): String {
    return if (this.lastIndexOf(".") > 0) this.substring(this.lastIndexOf(".") + 1, this.length)
    else this
}

fun String.toTimestampSeconds(format: String = "yyyy/MM/dd HH:mm"): Long {
    val formatter = SimpleDateFormat(format)
    val date = formatter.parse(this)
    return try {
        date.time / 1000L
    } catch (e: Exception) {
        e.log(TAG)
        throwException(TAG, e.message.toString())
    }
}

fun Long.isErrorTimeHint() = this == ERROR_TIME_HINT

val ERROR_TIME_HINT = 556654987L

fun String.toTimestamp(format: String = "yyyy/MM/dd HH:mm"): Long {
    if (contains("null") || isEmpty()) return ERROR_TIME_HINT
    val formatter = SimpleDateFormat(format)
    val date = formatter.parse(this)
    return try {
        date.time
    } catch (e: Exception) {
        e.log(TAG)
        throw Exception("${e.message}")
    }
}

private fun toHex(byteArray: ByteArray): String {
    return with(StringBuilder()) {
        byteArray.forEach {
            val hex = it.toInt() and (0xFF)
            val hexStr = Integer.toHexString(hex)
            if (hexStr.length == 1) append("0").append(hexStr)
            else append(hexStr)
        }
        toString()
    }
}

fun String.formatHtml(): Spanned {
    return processHtml(this)
}

private fun processHtml(s: String): Spanned {
    val SPAN_PATTERN: Pattern =
        Pattern.compile("<span.*?background(?:-color)?:\\s*?#([^,]*?)[\\s;\"].*?>(.*?)</span>")
    val HTML_COLOR_OPAQUE_MASK = 0xFF000000.toInt()
    // Easy for API 24+.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return HtmlCompat.fromHtml(s, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    // HtmlCompat.fromHtml() for API 24+ can handle more <span> attributes than we try to here.
    // We will just process the background-color attribute.

    // HtmlCompat.fromHtml() will remove the spans in our string. Escape them before processing.
    var escapedSpans = s.replace("<span ", "&lt;span ", true)
    escapedSpans = escapedSpans.replace("</span>", "&lt;/span&gt;", true)

    // Process all the non-span tags the are supported pre-API 24.
    val spanned = HtmlCompat.fromHtml(escapedSpans, HtmlCompat.FROM_HTML_MODE_LEGACY)

    // Process HTML spans. Identify each background-color attribute and replace the effected
    // text with a BackgroundColorSpan. Here we assume that the background color is a hex number
    // starting with "#". Other value such as named colors can be handled with additional
    // processing.
    val sb = SpannableStringBuilder(spanned)
    val m: Matcher = SPAN_PATTERN.matcher(sb)
    do {
        if (m.find()) {
            val regionEnd = m.start(0) + m.group(2).length
            sb.replace(m.start(0), m.end(0), m.group(2))
                .setSpan(
                    BackgroundColorSpan(Integer.parseInt(m.group(1), 16) or HTML_COLOR_OPAQUE_MASK),
                    m.start(0),
                    regionEnd,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            m.reset(sb)
            m.region(regionEnd, sb.length)
        }
    } while (!m.hitEnd())
    return sb
}

fun String.isVideo() = lowercase(Locale.ROOT).endsWith(".mp4")

fun String.isGif() = lowercase(Locale.ROOT).endsWith("gif")

/**
 * 取得特定文字後 n 個字
 * @param afterKeyWord 特定文字
 * @param n N個字
 * @return 回傳文字
 */
fun String.getSpecificNTextAfterKeyWord(afterKeyWord: String, n: Int): String {
    if (!contains(afterKeyWord)) return ""
    val index = indexOf(afterKeyWord) + afterKeyWord.length
    if (index + n > length) return ""
    return substring(index, index + n)
}

/**
 * 插入特定文字在 index 之後
 * @param index 插入文字的位置
 * @param insertWord 要插入的文字
 * @return 回傳插入文字後的完整字串
 */
fun String.insertWordsAfterIndex(index: Int, insertWord: String): String {
    return if (length < index) this
    else substring(0, index) + insertWord + substring(index, length)
}

/**
 * 轉換 SN to Int
 */
fun String.toDelayTime(): Int {
    Log.i(TAG, "toDelayTime SN: $this")
    if (isEmpty()) return randomInt(end = 30)
    val s = substring(length - 1, length)
    val list = s.toCharArray()
    var sum = 0
    list.forEach {
        sum += it.code
    }
    if (DEBUG) logStar(TAG, "toDelayTime sum: $sum")
    return sum % 10
}