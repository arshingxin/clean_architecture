package com.star.cla.ui.my.no_read_notification.no_read_notification_edit

import android.os.Bundle
import androidx.core.view.isVisible
import com.star.cla.R
import com.star.cla.base.BaseActivity
import com.star.cla.databinding.ActivityNoReadEditBinding
import com.star.cla.extension.setTextColor
import com.star.extension.log.logStar
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 未讀通知 - 編輯
 */
class NoReadEditActivity : BaseActivity() {
    private val TAG = NoReadEditActivity::class.java.simpleName
    private val DEBUG = true
    private var _binding: ActivityNoReadEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<NoReadEditViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (DEBUG) logStar(TAG, "onCreate")
        _binding = ActivityNoReadEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.apply {
            back.setOnClickListener {
                finish()
            }
            title.text = intent.extras?.getString("title") ?: ""
        }
        binding.toolbar.actionOne.apply {
            text = "刪除"
            isVisible = true
            setTextColor(
                applicationContext,
                if ((binding.noReadEditList.adapter?.itemCount ?: 0) > 0) R.color.blue
                else R.color.dark_blue
            )
            if ((binding.noReadEditList.adapter?.itemCount ?: 0) > 0)
                setOnClickListener {
                    if (DEBUG) logStar(TAG, "刪除")
                }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.resume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroy()
        _binding = null
    }
}