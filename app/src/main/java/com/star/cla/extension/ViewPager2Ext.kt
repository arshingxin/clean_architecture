package com.star.cla.extension

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import androidx.viewpager2.widget.ViewPager2

fun ViewPager2.setCurrentItem(
    index: Int,
    duration: Long,
    interpolator: TimeInterpolator = LinearInterpolator(),
    pagePxWidth: Int = width,
    startAnimatorAction: (ValueAnimator) -> Unit
) {
    val pxToDrag: Int = pagePxWidth * (index - currentItem)
    val animator = ValueAnimator.ofInt(0, pxToDrag)
    var previousValue = 0
    animator.addUpdateListener { valueAnimator ->
        val currentValue = valueAnimator.animatedValue as Int
        val currentPxToDrag = (currentValue - previousValue).toFloat()
        fakeDragBy(-currentPxToDrag)
        previousValue = currentValue
    }
    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) { beginFakeDrag() }
        override fun onAnimationEnd(animation: Animator?) { endFakeDrag() }
        override fun onAnimationCancel(animation: Animator?) { /* Ignored */ }
        override fun onAnimationRepeat(animation: Animator?) { /* Ignored */ }
    })
    animator.interpolator = interpolator
    animator.duration = duration
    animator.start()
    startAnimatorAction.invoke(animator)
}