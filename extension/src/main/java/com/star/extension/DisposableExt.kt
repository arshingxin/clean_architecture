package com.star.extension

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

private val TAG = "DisposableExt"

fun Disposable?.removeFrom(compositeDisposable: CompositeDisposable?) {
    this?.let {
        if (!it.isDisposed) it.dispose()
        compositeDisposable?.remove(it)
    }
}