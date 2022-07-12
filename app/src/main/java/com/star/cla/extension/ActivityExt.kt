package com.star.cla.extension

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import com.star.cla.base.BaseFragment
import com.star.extension.log.logStar
private val TAG = "ActivityExt"
private val DEBUG = true
fun FragmentActivity.replaceFragment(layoutId: Int, fragment: BaseFragment, data: Bundle? = null) {
    val transaction = supportFragmentManager.beginTransaction()
    if (data != null) fragment.arguments = data
    transaction.replace(layoutId, fragment)
    transaction.commit()
}

fun FragmentActivity.addFragment(layoutId: Int, fragment: BaseFragment, data: Bundle? = null) {
    val transaction = supportFragmentManager.beginTransaction()
    if (data != null) fragment.arguments = data
    transaction.add(layoutId, fragment)
    transaction.commit()
}

fun Activity.hideSoftKeyboard() {
    currentFocus?.apply {
        if (DEBUG) logStar(TAG, "hideSoftKeyboard")
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}
