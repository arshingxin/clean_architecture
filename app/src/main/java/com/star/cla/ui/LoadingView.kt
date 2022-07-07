package com.star.cla.ui

import android.widget.Toast

interface LoadingView {
    fun showLoading()
    fun hideLoading()
    fun showRetry()
    fun hideRetry()
    fun hideAllView()
    fun showToast(errorMsg: String, duration: Int = Toast.LENGTH_LONG)
}