package com.star.cla.extension

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.star.cla.BaseFragment

fun Fragment.replaceChildFragment(layoutId: Int, fragment: BaseFragment, data: Bundle? = null) {
    val transaction = childFragmentManager.beginTransaction()
    if (data != null) fragment.arguments = data
    transaction.replace(layoutId, fragment, "")
    transaction.commitAllowingStateLoss()
}