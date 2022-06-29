package com.star.cla.extension

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.star.cla.BaseFragment

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