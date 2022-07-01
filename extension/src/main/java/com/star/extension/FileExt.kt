package com.star.extension

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.star.extension.log.logStar
import com.star.extension.log.logStarError
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers.io
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

private const val TAG = "FileExt"
private const val DEBUG = false

fun File.createDirIfNotExists(): Boolean {
    var ret = true
    if (!exists()) {
        if (!mkdirs()) {
            ret = if (absoluteFile.startsWith("/null")) {
                false
            } else {
                logStarError(TAG, "createDirIfNotExists fail:: $absolutePath")
                false
            }
        }
    }
    return ret
}

fun String?.createDirIfNotExists(): Boolean {
    return if (this.isNullOrEmpty()) false
    else File(this).createDirIfNotExists()
}

fun String.isFileExist() = this.isNotEmpty() && File(this).exists()

fun String.is777(): Boolean {
    val f = File(this)
    return isFileExist() && f.canWrite() && f.canRead() && f.canExecute()
}

fun String.updateLastModifyTime() {
    if (!isFileExist()) return
    File(this).setLastModified(System.currentTimeMillis())
}

fun String.copyTo(
    newFilePath: String,
    fileCopyFinishedAction: (Pair<String, String>) -> Unit,
    finishAction: ((Boolean) -> Unit?)? = null
) {
    if (DEBUG) logStar(TAG, "[${isFileExist()}]$this copy to $newFilePath")
    if (isFileExist()) {
        fileCopyFinishedAction.invoke(Pair(this, newFilePath))
        File(this).copyTo(File(newFilePath), true)
        finishAction?.invoke(true)
    } else finishAction?.invoke(false)
}

fun File?.deleteFile() {
    if (this != null && exists() && isFile) delete()
}

fun String.filePathToUri(): Uri = Uri.fromFile(File(this))

fun String.filePathToInputStream(): InputStream? {
    return if (this.isFileExist()) FileInputStream(this)
    else null
}

fun String.toFileMultiPart(): MultipartBody.Part {
    val mainFile = File(this)
    return mainFile.toFileMultiPart()
}

fun File.toFileMultiPart(): MultipartBody.Part {
    val requestFile = this
        .asRequestBody("multipart/form-data".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("file", name, requestFile)
}

fun String.filePathToBitmap(): Observable<Bitmap> {
    return File(this).fileToBitmap()
}

private sealed class FileToBitmapStatus {
    data class Success(val bitmap: Bitmap) : FileToBitmapStatus()
    data class Fail(val errorMsg: String) : FileToBitmapStatus()
}

fun File.fileToBitmap(): Observable<Bitmap> {
    return Observable
        .just(true)
        .map {
            try {
                val bitmap = BitmapFactory.decodeStream(inputStream())
                val msg = "$absolutePath 檔案轉成Bitmap成功"
                if (DEBUG) logStar(TAG, msg)
                Log.i(TAG, msg)
                FileToBitmapStatus.Success(bitmap)
            } catch (e: Exception) {
                FileToBitmapStatus.Fail(e.message.toString())
            }
        }
        .flatMap {
            when (it) {
                is FileToBitmapStatus.Success -> {
                    Observable.just(it.bitmap)
                }
                is FileToBitmapStatus.Fail -> {
                    Observable.error(RuntimeException("${absolutePath}, ${it.errorMsg}"))
                }
            }
        }
        .subscribeOn(io())
}

private sealed class CopyToFilePathStatus {
    data class Success(val file: File) : CopyToFilePathStatus()
    data class Fail(val errorMsg: String) : CopyToFilePathStatus()
}

fun InputStream.copyToFilePath(filePath: String): Observable<File> {
    return Observable
        .just(true)
        .map {
            try {
                val file = File(filePath)
                file.outputStream().use { this.copyTo(it) }
                val msg = "${file.absolutePath} 檔案複製成功"
                if (DEBUG) logStar(TAG, msg)
                Log.i(TAG, msg)
                CopyToFilePathStatus.Success(file)
            } catch (e: Exception) {
                CopyToFilePathStatus.Fail(e.message.toString())
            }
        }
        .flatMap {
            when (it) {
                is CopyToFilePathStatus.Success -> {
                    Observable.just(it.file)
                }
                is CopyToFilePathStatus.Fail -> {
                    Observable.error(RuntimeException("${filePath}, ${it.errorMsg}"))
                }
            }
        }
        .subscribeOn(io())
}

@SuppressLint("CheckResult")
private var copyRawToFilePathDisposable: Disposable? = null
fun MutableList<InputStream>.copyRawToFilePath(filePathList: MutableList<String>) {
    var index = 0
    copyRawToFilePathDisposable?.dispose()
    copyRawToFilePathDisposable = Observable
        .fromIterable(this)
        .concatMap { inputStream ->
            filePathList.getOrElse(index) { null }?.let {
                inputStream.copyToFilePath(it)
            }
        }
        .map {
            val msg = "${it.absolutePath} 檔案複製成功"
            if (DEBUG) logStar(TAG, msg)
            Log.i(TAG, msg)
        }
        .subscribeOn(io())
        .subscribe({ index++ }, { it.report(TAG) })
}

private var copyRawListToFilePathDisposable: Disposable? = null
fun MutableList<Int>.copyRawToFilePath(res: Resources, filePathList: MutableList<String>) {
    var index = 0
    copyRawListToFilePathDisposable?.dispose()
    copyRawListToFilePathDisposable = Observable
        .fromIterable(this)
        .concatMap { resId ->
            filePathList.getOrElse(index) { null }?.let { filePath ->
                res.openRawResource(resId).copyToFilePath(filePath)
            }
        }
        .map {
            val msg = "${it.absolutePath} 檔案複製成功"
            if (DEBUG) logStar(TAG, msg)
            Log.i(TAG, msg)
        }
        .subscribeOn(io())
        .subscribe({ index++ }, { it.report(TAG) })
}