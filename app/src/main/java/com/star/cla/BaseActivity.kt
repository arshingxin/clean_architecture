package com.star.cla

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.star.cla.dialog.CustomDialog
import com.star.extension.log.logStar

open class BaseActivity : AppCompatActivity() {
    private val TAG = BaseActivity::class.java.simpleName
    private val DEBUG = true
    var dialog: CustomDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showAllBars()
    }

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

    fun showToast(msg: String, duration: Int = Toast.LENGTH_LONG) {
        if (DEBUG) logStar(TAG, "showToast $msg")
        MainApplication.uiThread {
            Toast.makeText(this, msg, duration).show()
        }
    }

    override fun onDestroy() {
        closeDialog()
        super.onDestroy()
    }

    fun Activity.hideAllBars() {
        ImmersionBar.with(this)
            .fullScreen(true)
            .hideBar(BarHide.FLAG_HIDE_BAR)
            .init()
    }

    fun Activity.showAllBars() {
        ImmersionBar.with(this)
            .transparentBar()
            .hideBar(BarHide.FLAG_SHOW_BAR)
            .statusBarDarkFont(!isDarkThemeOn())
            .navigationBarDarkIcon(!isDarkThemeOn())
            .init()
    }

    fun Context.isDarkThemeOn(): Boolean {
        return resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
    }
}