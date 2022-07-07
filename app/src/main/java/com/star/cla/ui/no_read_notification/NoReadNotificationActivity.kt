package com.star.cla.ui.no_read_notification

import android.os.Bundle
import com.star.cla.BaseActivity
import com.star.cla.databinding.ActivityNewsCollectionBinding
import com.star.extension.log.logStar

/**
 * 未讀通知
 */
class NoReadNotificationActivity: BaseActivity() {
    private val TAG = NoReadNotificationActivity::class.java.simpleName
    private val DEBUG = true
    private var _binding: ActivityNewsCollectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (DEBUG) logStar(TAG, "onCreate")
        _binding = ActivityNewsCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.apply {
            back.setOnClickListener {
                finish()
            }
            title.text = "通知"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}