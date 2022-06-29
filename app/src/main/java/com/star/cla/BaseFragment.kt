package com.star.cla

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.star.cla.ui.LoadingView

class BaseFragment: Fragment(), LoadingView {
    private val TAG = BaseFragment::class.java.simpleName
    private val DEBUG = false

    override fun showErrorToast(errorMsg: String) {
        Toast.makeText(activity, errorMsg, Toast.LENGTH_SHORT).show()
    }
}