package com.star.cla.ui.my.no_read_notification

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.star.cla.BaseActivity
import com.star.cla.databinding.ActivityNoReadNotificationBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (DEBUG) logStar(TAG, "onCreate")
        _binding = ActivityNoReadNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observe(viewModel.initLiveData) {
            val pageAdapter = NoReadPageAdapter(supportFragmentManager, lifecycle)
            binding.viewpager2.adapter = pageAdapter
            val title: ArrayList<String> = arrayListOf("優惠報報", "個人訊息", "寵物報報")
            TabLayoutMediator(binding.tabLayout, binding.viewpager2) { tab, position ->
                tab.text = title[position]
            }.attach()
        }
        binding.toolbar.apply {
            back.setOnClickListener { finish() }
            title.text = "通知"
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