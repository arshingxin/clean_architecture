package com.star.cla.ui.my.no_read_notification

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.star.cla.BaseActivity
import com.star.cla.R
import com.star.cla.databinding.ActivityNoReadNotificationBinding
import com.star.cla.extension.setTextColor
import com.star.cla.ui.my.no_read_notification.no_read_notification_edit.NoReadEditActivity
import com.star.extension.log.logStar
import com.star.extension.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 未讀通知
 */
class NoReadNotificationActivity : BaseActivity() {
    private val TAG = NoReadNotificationActivity::class.java.simpleName
    private val DEBUG = true
    private var _binding: ActivityNoReadNotificationBinding? = null
    private val viewModel by viewModel<NoReadNotificationViewModel>()
    private val binding get() = _binding!!
    private val title: ArrayList<String> = arrayListOf("優惠報報", "個人訊息", "寵物報報")
    private var currentTitle: String = title[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (DEBUG) logStar(TAG, "onCreate")
        _binding = ActivityNoReadNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observe(viewModel.initLiveData) {
            val pageAdapter = NoReadPageAdapter(supportFragmentManager, lifecycle)
            binding.viewpager2.adapter = pageAdapter
            // 設定tab文字
            TabLayoutMediator(binding.tabLayout, binding.viewpager2) { tab, position ->
                tab.text = title[position]
            }.attach()
            binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.position?.apply {
                        currentTitle = title[this]
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })
        }
        binding.toolbar.apply {
            back.setOnClickListener { finish() }
            title.text = "通知"
        }

        binding.toolbar.actionOne.apply {
            text = "編輯"
            isVisible = true
            setTextColor(applicationContext, R.color.blue)
            setOnClickListener {
                if (DEBUG) logStar(TAG, "edit $currentTitle")
                val intent = Intent(this@NoReadNotificationActivity, NoReadEditActivity::class.java)
                intent.putExtra("title", currentTitle)
                startActivity(intent)
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