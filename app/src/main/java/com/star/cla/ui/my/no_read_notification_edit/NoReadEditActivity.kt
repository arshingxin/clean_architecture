package com.star.cla.ui.my.no_read_notification_edit

import android.os.Bundle
import androidx.core.view.isVisible
import com.star.cla.BaseActivity
import com.star.cla.R
import com.star.cla.databinding.ActivityNewsEditBinding
import com.star.cla.extension.setTextColor
import com.star.extension.log.logStar

class NoReadEditActivity : BaseActivity() {
    private val TAG = NoReadEditActivity::class.java.simpleName
    private val DEBUG = true
    private var _binding: ActivityNewsEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (DEBUG) logStar(TAG, "onCreate")
        _binding = ActivityNewsEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.apply {
            back.setOnClickListener {
                finish()
            }
            title.text = "通知"
        }
        binding.toolbar.actionOne.apply {
            text = "刪除"
            isVisible = true
            setTextColor(
                applicationContext,
                if ((binding.newsEditList.adapter?.itemCount ?: 0) > 0) R.color.blue
                else R.color.dark_blue
            )
            if ((binding.newsEditList.adapter?.itemCount ?: 0) > 0)
                setOnClickListener {
                    if (DEBUG) logStar(TAG, "刪除")
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}