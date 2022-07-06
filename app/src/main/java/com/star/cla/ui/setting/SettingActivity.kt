package com.star.cla.ui.setting

import android.os.Bundle
import androidx.core.view.isVisible
import com.star.cla.BaseActivity
import com.star.cla.databinding.ActivitySettingBinding
import com.star.extension.log.logStar

class SettingActivity : BaseActivity() {
    private val TAG = SettingActivity::class.java.simpleName
    private val DEBUG = true
    private var _binding: ActivitySettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (DEBUG) logStar(TAG, "onCreate")
        _binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.back.setOnClickListener {
            finish()
        }
        binding.pushLayoutDivideLineHint.text = "* 開啟通知以接收即時交易訊息"
        binding.pushItemOpen.apply {
            val msg = "開啟推播通知"
            title.text = msg
            switchButton.setOnCheckedChangeListener { _, isChecked ->
                if (DEBUG) logStar(TAG, "$msg: $isChecked")
                binding.pushLayoutDivideLine.isVisible = isChecked
                binding.pushLayoutDivideLineHint.isVisible = !isChecked
                binding.pushItemMarketing.mainLayout.isVisible = isChecked
                binding.pushItemNews.mainLayout.isVisible = isChecked
            }
        }
        binding.pushItemMarketing.apply {
            val msg = "接受行銷活動訊息"
            title.text = msg
            switchButton.setOnCheckedChangeListener { _, isChecked ->
                if (DEBUG) logStar(TAG, "$msg: $isChecked")
            }
        }
        binding.pushItemNews.apply {
            val msg = "接受新聞推播"
            title.text = msg
            switchButton.setOnCheckedChangeListener { _, isChecked ->
                if (DEBUG) logStar(TAG, "$msg: $isChecked")
            }
        }
        binding.otherItemCleanCache.apply {
            val msg = "清除快取"
            mainLayout.setOnClickListener {
                if (DEBUG) logStar(TAG, msg)
            }
            title.text = msg
        }
        binding.otherItemDeleteAccount.apply {
            val msg = "申請刪除會員帳號"
            mainLayout.setOnClickListener {
                if (DEBUG) logStar(TAG, msg)
            }
            title.text = msg
        }
        binding.logoutLayout.mainLayout.setOnClickListener {
            val msg = "登出"
            if (DEBUG) logStar(TAG, msg)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}