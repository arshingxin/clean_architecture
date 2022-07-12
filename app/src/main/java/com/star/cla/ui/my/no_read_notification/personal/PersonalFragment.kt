package com.star.cla.ui.my.no_read_notification.personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.star.cla.base.BaseFragment
import com.star.cla.databinding.FragmentPersonalBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 個人訊息
 */
class PersonalFragment: BaseFragment() {
    private val TAG = PersonalFragment::class.java.simpleName
    private val DEBUG = true
    private val viewModel by viewModel<PersonalViewModel>()
    private var _binding: FragmentPersonalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonalBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun showLoading() {
        super.showLoading()
        binding.mainLoadingLayout.loadingLayout.isVisible = true
    }

    override fun hideLoading() {
        super.hideLoading()
        binding.mainLoadingLayout.loadingLayout.isVisible = false
    }

    override fun showRetry() {
        super.showRetry()
        binding.mainRetryLayout.retryLayout.isVisible = true
        binding.mainRetryLayout.retryLayout.setOnClickListener {
            viewModel.resume()
        }
    }

    override fun hideRetry() {
        super.hideRetry()
        binding.mainRetryLayout.retryLayout.isVisible = false
        binding.mainRetryLayout.retryLayout.setOnClickListener(null)
    }

    override fun onResume() {
        super.onResume()
        viewModel.resume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.destroy()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        viewModel.pause()
    }
}