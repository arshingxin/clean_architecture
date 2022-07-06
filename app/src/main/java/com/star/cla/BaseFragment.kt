package com.star.cla

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.star.cla.ui.LoadingView
import com.star.extension.log.logStar

open class BaseFragment: Fragment(), LoadingView {
    private val TAG = BaseFragment::class.java.simpleName
    private val DEBUG = true

    override fun showLoading() {
        if (DEBUG) logStar(TAG, "showLoading")
        hideAllView()
    }

    override fun hideLoading() {
        if (DEBUG) logStar(TAG, "hideLoading")
    }

    override fun showRetry() {
        if (DEBUG) logStar(TAG, "showRetry")
        hideAllView()
    }

    override fun hideRetry() {
        if (DEBUG) logStar(TAG, "hideRetry")
    }

    override fun hideAllView() {
        hideRetry()
        hideLoading()
    }

    override fun showToast(errorMsg: String) {
        if (DEBUG) logStar(TAG, "showErrorToast $errorMsg")
        MainApplication.uiThread {
            Toast.makeText(activity, errorMsg, Toast.LENGTH_SHORT).show()
        }
    }
}