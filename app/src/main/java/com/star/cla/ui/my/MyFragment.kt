package com.star.cla.ui.my

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.star.cla.databinding.FragmentMyBinding
import com.star.cla.ui.my.news_collection.NewsCollectionActivity
import com.star.cla.ui.my.no_read_notification.NoReadNotificationActivity
import com.star.cla.ui.my.setting.SettingActivity
import com.star.extension.log.logStar
import com.star.extension.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 我的
 */
class MyFragment : Fragment() {
    private val TAG = MyFragment::class.java.simpleName
    private val DEBUG = true
    private var _binding: FragmentMyBinding? = null
    private val viewModel by viewModel<MyViewModel>()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (DEBUG) logStar(TAG, "onCreateView")
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (DEBUG) logStar(TAG, "onViewCreated")
        binding.actionSetting.setOnClickListener {
            if (DEBUG) logStar(TAG, "我的設定")
            startActivity(Intent(context, SettingActivity::class.java))
        }
        binding.userInfoLayout.apply {
            userTopRightLayout.setOnClickListener {
                if (DEBUG) logStar(TAG, "選擇個人頭像")
            }
            newLayout.setOnClickListener {
                if (DEBUG) logStar(TAG, "新聞收藏")
                startActivity(Intent(context, NewsCollectionActivity::class.java))
            }
            readLayout.setOnClickListener {
                if (DEBUG) logStar(TAG, "未讀通知")
                startActivity(Intent(context, NoReadNotificationActivity::class.java))
            }
        }
        observe(viewModel.userNameLiveData) {
            binding.userInfoLayout.userName.text = it
        }
        observe(viewModel.userSubNameLiveData) {
            binding.userInfoLayout.userSubName.text = it
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}