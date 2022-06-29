package com.star.cla.extension

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.star.cla.log.logStar
import com.star.cla.log.logStarError
import org.json.JSONArray
import org.json.JSONObject

private const val TAG = "GsonExt"
private const val DEBUG = false

fun <T> String?.toDataBean(classOfT: Class<T>?): T? {
    return if (this.isJson()) Gson().fromJson(this, classOfT)
    else null
}

fun <T : Any> T.toJson(): String = Gson().toJson(this) ?: ""

inline fun <reified T> Gson.fromJson(json: String): T = fromJson(json, object : TypeToken<T>() {}.type)

fun String?.isJson(): Boolean {
    if (this.isNullOrEmpty()) return false
    if (DEBUG) logStar(TAG, "isJson:: $this")
    var jsonObject: JSONObject? = null
    try {
        jsonObject = JSONObject(this)
    } catch (e: Exception) {
        logStarError(TAG, "error: ${e.message}, json: $this")
    }
    return jsonObject != null
}

fun String?.isJsonArray(): Boolean {
    if (isNullOrEmpty()) return false
    if (DEBUG) logStar(TAG, "isJsonArray:: $this")
    var jsonArray: JSONArray? = null
    try {
        jsonArray = JSONArray(this)
    } catch (e: Exception) {
        logStarError(TAG, e.message.toString())
        logStarError(TAG, "error: ${e.message}, json: $this")
    }
    return jsonArray != null
}