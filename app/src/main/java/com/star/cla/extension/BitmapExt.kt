package com.star.cla.extension

import android.graphics.Bitmap
import android.graphics.Matrix
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers.io
import java.io.ByteArrayOutputStream
import java.io.File

private sealed class CompressToFileStatus {
    data class Success(val file: File) : CompressToFileStatus()
    data class Fail(val file: File, val errorMsg: String) : CompressToFileStatus()
}

fun Bitmap.compressToFile(toFilePath: String, quality: Int = 10): Observable<File?> {
    return Observable
        .just(true)
        .map {
            try {
                val byteArrayOutputStream = ByteArrayOutputStream()
                compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
                val file = File(toFilePath)
                if (!file.exists()) file.createNewFile()
                file.writeBytes(byteArrayOutputStream.toByteArray())
                CompressToFileStatus.Success(file)
            } catch (e: Exception) {
                CompressToFileStatus.Fail(File(toFilePath), e.message.toString())
            }
        }
        .flatMap {
            when (it) {
                is CompressToFileStatus.Success -> {
                    Observable.just(it.file)
                }
                is CompressToFileStatus.Fail -> {
                    Observable.error(RuntimeException("${it.file.absolutePath}檔案壓縮失敗"))
                }
            }
        }
        .subscribeOn(io())
}

private sealed class CompressToBitmapStatus {
    data class Success(val file: File) : CompressToBitmapStatus()
    data class Fail(val file: File, val errorMsg: String) : CompressToBitmapStatus()
}

fun Bitmap.compressToBitmap(toFilePath: String, quality: Int = 10): Observable<Bitmap> {
    return Observable
        .just(true)
        .map {
            try {
                val byteArrayOutputStream = ByteArrayOutputStream()
                compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
                val file = File(toFilePath)
                if (!file.exists()) file.createNewFile()
                file.writeBytes(byteArrayOutputStream.toByteArray())
                CompressToBitmapStatus.Success(file)
            } catch (e: Exception) {
                CompressToBitmapStatus.Fail(File(toFilePath), e.message.toString())
            }

        }
        .flatMap {
            when (it) {
                is CompressToBitmapStatus.Success -> {
                    it.file.fileToBitmap()
                }
                is CompressToBitmapStatus.Fail -> {
                    Observable.error(RuntimeException("${it.file.absolutePath}檔案壓縮失敗"))
                }
            }
        }
        .subscribeOn(io())
}

private sealed class RotateBitmapStatus {
    data class Success(val bitmap: Bitmap) : RotateBitmapStatus()
    object Fail : RotateBitmapStatus()
}

fun Bitmap.rotate(degrees: Float): Observable<Bitmap> {
    return Observable
        .just(true)
        .map {
            try {
                val matrix = Matrix().apply { postRotate(degrees) }
                RotateBitmapStatus.Success(
                    Bitmap.createBitmap(
                        this,
                        0,
                        0,
                        width,
                        height,
                        matrix,
                        true
                    )
                )
            } catch (e: Exception) {
                RotateBitmapStatus.Fail
            }
        }
        .flatMap {
            when (it) {
                is RotateBitmapStatus.Success -> {
                    Observable.just(it.bitmap)
                }
                is RotateBitmapStatus.Fail -> {
                    Observable.error(RuntimeException("bitmap旋轉失敗"))
                }
            }
        }
        .subscribeOn(io())
}