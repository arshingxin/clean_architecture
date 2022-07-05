package com.star.cla.ui

interface LoadingView {
    fun showLoading()
    fun hideLoading()
    fun showRetry()
    fun hideRetry()
    fun hideAllView()
    fun showToast(errorMsg: String)
}