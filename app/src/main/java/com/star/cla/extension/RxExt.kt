package com.star.cla.extension

import com.star.cla.BuildConfig
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function
import java.util.*
import java.util.concurrent.TimeUnit

private const val DEBUG = false
private const val TAG = "RxExt"

fun Disposable.addTo(compositeDisposable: CompositeDisposable?): Disposable =
    apply {
        if (compositeDisposable?.isDisposed == true) return@apply
        compositeDisposable?.add(this)
    }

class RetryWithDelayDefault(
    private val tag: String = TAG,
    private val maxRetries: Int = 5,
    private val retryDelayMinutes: Long = if (BuildConfig.DEBUG) 1L else 5
): Function<Observable<Throwable>, Observable<*>> {
    private var retryCount: Int = 0
    init {
        this.retryCount = 0
    }

    override fun apply(attempts: Observable<Throwable>): Observable<*> {
        return attempts
            .flatMap { throwable ->
                if (throwable is Exception) {
                    if (++retryCount < maxRetries) {
                        val throwableMsg = throwable.message
                        if (throwableMsg?.isSkip() == true) Observable.error(throwable)
                        else {
                            // When this Observable calls onNext, the original
                            // Observable will be retried (i.e. re-subscribed).
                            Observable.timer(
                                randomLong(end = retryDelayMinutes),
                                TimeUnit.MINUTES
                            )
                        }
                    } else Observable.error(throwable)
                    // Max retries hit. Just pass the error along.
                } else {
                    Observable.error<Any>(throwable)
                }
            }
    }
}

fun <T : Any> Observable<T>.retryWhenCustom(tag: String): Observable<T> {
    return retryWhen(RetryWithDelayDefault(tag))
}

fun <T : Any> Observable<T>.repeatWhenCustom(
    periodTime: Long = 2,
    tu: TimeUnit = if (BuildConfig.DEBUG) TimeUnit.MINUTES else TimeUnit.HOURS
) = repeatWhen { it.delay(periodTime, tu) }

fun <T : Any> Observable<T>.delayEach(interval: Long, timeUnit: TimeUnit) =
    Observable.zip(
        this,
        Observable.interval(interval, timeUnit)
    ) { item, _ -> item }

fun String?.isSkip() =
    this?.lowercase(Locale.ROOT)?.contains("404") == true ||
            this?.lowercase(Locale.ROOT)
                ?.contains("Error in creating destination file".lowercase(Locale.ROOT)) == true