package com.star.cla

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.star.cla.log.logStar
import com.star.cla.ui.LoadingView

open class BaseFragment: Fragment(), LoadingView {
    private val TAG = BaseFragment::class.java.simpleName
    private val DEBUG = true

    override fun showLoading() {
        if (DEBUG) logStar("showLoading")
    }

    override fun hideLoading() {
        if (DEBUG) logStar("hideLoading")
    }

    override fun showRetry() {
        if (DEBUG) logStar("showRetry")
    }

    override fun hideRetry() {
        if (DEBUG) logStar("hideRetry")
    }

    override fun showToast(errorMsg: String) {
        if (DEBUG) logStar("showErrorToast $errorMsg")
        MainApplication.getMainThreadHandler()?.post {
            Toast.makeText(activity, errorMsg, Toast.LENGTH_SHORT).show()
        }
    }
}