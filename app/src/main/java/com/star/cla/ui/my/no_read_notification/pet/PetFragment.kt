package com.star.cla.ui.my.no_read_notification.pet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.star.cla.BaseFragment
import com.star.cla.databinding.FragmentPetBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 寵物報報
 */
class PetFragment: BaseFragment() {
    private val TAG = PetFragment::class.java.simpleName
    private val DEBUG = true
    private val viewModel by viewModel<PetViewModel>()
    private var _binding: FragmentPetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPetBinding.inflate(inflater, container, false)
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