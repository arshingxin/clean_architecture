package com.star.cla.extension

import android.animation.Animator
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers.io

private const val DEBOUNCE_TIME = 200L
private  const val Duration = 1000L

fun View.focus() {
    isFocusable = true
    isFocusableInTouchMode = true
    requestFocus()
    requestLayout()
    postInvalidate()
}


fun View.debounceClick(time: Long = DEBOUNCE_TIME, action: () -> Unit) {
    var recordTime = 0L
    this.setOnClickListener {
        if (System.currentTimeMillis() - recordTime <= time) return@setOnClickListener
        else {
            recordTime = System.currentTimeMillis()
            action.invoke()
        }
    }
}

fun List<View?>.setOnClickListener(clickAction: () -> Unit) {
    forEach {
        it?.setOnClickListener {
            clickAction.invoke()
        }
    }
}

fun List<View>.setOnLongClickListener(clickAction: () -> Unit) {
    forEach {
        it.setOnLongClickListener {
            clickAction.invoke()
            true
        }
    }
}

/**
 * 從右邊進或出
 */
fun View.slideRight(enter: Boolean = false, duration: Long = Duration) {
    animate()?.setDuration(duration)?.translationX(if (enter) 0f else width.toFloat())
        ?.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator?) {
                visibility = if (enter) View.VISIBLE else View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }
        })?.start()
}

/**
 * 從上方進或出
 */
fun View.slideUp(enter: Boolean = false, duration: Long = Duration) {
    animate()?.setDuration(duration)?.translationY(if (enter) 0f else -height.toFloat())
        ?.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator?) {
                visibility = if (enter) View.VISIBLE else View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }
        })?.start()
}

/**
 * 從下方進或出
 */
fun View.slideDown(enter: Boolean = false, duration: Long = Duration) {
    animate()?.setDuration(duration)?.translationY(if (enter) 0f else height.toFloat())
        ?.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator?) {
                visibility = if (enter) View.VISIBLE else View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }
        })?.start()
}

fun NestedScrollView.scrollVerticalTo(targetView: View, yScroll: Int = 56) {
    smoothScrollTo(0, targetView.y.toInt() - yScroll)
}

fun RecyclerView.customAddOnScrollIdleListener(onScrollIdleAction: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState != RecyclerView.SCROLL_STATE_IDLE) return
            onScrollIdleAction.invoke()
        }
    })
}

private sealed class ViewToBitmapStatus {
    data class Success(val bitmap: Bitmap) : ViewToBitmapStatus()
    data class Fail(val errorMsg: String) : ViewToBitmapStatus()
}

fun View.toBitmap(): Observable<Bitmap> =
    Observable
        .just(true)
        .map {
            try {
                ViewToBitmapStatus.Success(
                    Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
                        draw(Canvas(this))
                    })
            } catch (e: Exception) {
                ViewToBitmapStatus.Fail(e.message.toString())
            }
        }
        .flatMap {
            when (it) {
                is ViewToBitmapStatus.Success -> {
                    Observable.just(it.bitmap)
                }
                is ViewToBitmapStatus.Fail -> {
                    Observable.error(RuntimeException(it.errorMsg))
                }
            }
        }
        .subscribeOn(io())
