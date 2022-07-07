package com.star.cla.ui.setting

import android.os.Bundle
import androidx.core.view.isVisible
import com.star.cla.BaseActivity
import com.star.cla.R
import com.star.cla.databinding.ActivitySettingBinding
import com.star.cla.extension.setTextColor
import com.star.extension.clearCache
import com.star.extension.log.logStar

/**
 * 我的 - 我的設定
 */
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
        binding.otherTitleLayout.title.text = "其他功能"
        binding.otherItemCleanCache.apply {
            if (cacheDir.length() > 0)
                binding.otherItemCleanCache.title.setTextColor(applicationContext, R.color.grey)
            else clearCacheTextColor()
            val msg = "清除快取"
            mainLayout.setOnClickListener {
                if (DEBUG) logStar(TAG, msg)
                clearCache { success ->
                    if (success) {
                        showToast("已清除快取！")
                        clearCacheTextColor()
                    }
                }
            }
            title.text = msg
        }
        binding.otherItemDeleteAccount.apply {
            val msg = "申請刪除會員帳號"
            mainLayout.setOnClickListener {
                if (DEBUG) logStar(TAG, msg)
                showDialog(
                    "刪除會員資格",
                    "刪除帳號將失去會員福利，如已累積的點數’優惠券使用權’分批購商品領取資格等．",
                    leftButtonText = "取消",
                    leftButtonAction = {
                        dialog?.dismiss()
                    },
                    rightButtonText = "提出申請",
                    rightAction = {
                        if (DEBUG) logStar(TAG, "提出申請")
                        dialog?.dismiss()
                    })
            }
            title.text = msg
        }
        binding.logoutLayout.mainLayout.setOnClickListener {
            val msg = "登出"
            if (DEBUG) logStar(TAG, msg)
            showDialog(
                contentText = "確定要登出嗎？",
                leftButtonText = "取消",
                leftButtonAction = {
                    dialog?.dismiss()
                },
                rightButtonText = "確定",
                rightAction = {
                    dialog?.dismiss()
                })
        }
    }

    private fun clearCacheTextColor() {
        binding.otherItemCleanCache.title.setTextColor(applicationContext, R.color.clean_cache)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}