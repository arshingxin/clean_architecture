package com.star.cla

import android.app.Activity
import android.widget.Toast
import com.star.cla.ui.LoadingView

class BaseActivity : Activity(), LoadingView {
    private val TAG = BaseActivity::class.java.simpleName
    private val DEBUG = false

    override fun showErrorToast(errorMsg: String) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
    }
}