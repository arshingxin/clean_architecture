package com.star.cla

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.star.cla.dialog.CustomDialog
import com.star.extension.log.logStar

open class BaseActivity : AppCompatActivity() {
    private val TAG = BaseActivity::class.java.simpleName
    private val DEBUG = true
    var dialog: CustomDialog? = null

    fun showDialog(
        titleText: String? = "",
        contentText: String? = "",
        leftButtonText: String? = "",
        leftButtonAction: (() -> Unit?)? = null,
        rightButtonText: String? = "",
        rightAction: (() -> Unit?)? = null,
        widthRatio: Float? = 0.9f
    ) {
        closeDialog()
        dialog = CustomDialog(
            titleText,
            contentText,
            leftButtonText,
            leftButtonAction,
            rightButtonText,
            rightAction,
            widthRatio
        )
        dialog?.show(supportFragmentManager, TAG)
    }

    private fun closeDialog() {
        if (dialog == null) return
        dialog?.dismiss()
        dialog = null
    }

    fun showToast(msg: String) {
        if (DEBUG) logStar(TAG, "showToast $msg")
        MainApplication.uiThread {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        closeDialog()
        super.onDestroy()
    }
}