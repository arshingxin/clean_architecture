package com.star.cla.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.star.cla.BaseFragment
import com.star.cla.databinding.FragmentHomeBinding
import com.star.extension.observe
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val viewModel by viewModel<HomeViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        observe(viewModel.text) {
            textView.text = it
        }
        observe(viewModel.deviceInfoModelLiveData) {
            when (it) {
                is HomeViewModel.ResponseStatus.NetFail -> {
                    showToast("網路未連線, 請檢查網路!")
                }

                is HomeViewModel.ResponseStatus.Loading -> {
                    showLoading()
                }

                is HomeViewModel.ResponseStatus.Success -> {
                    hideAllView()
                }

                is HomeViewModel.ResponseStatus.Retry -> {
                    showRetry()
                }

                is HomeViewModel.ResponseStatus.ShowError -> {
                    showToast(it.error)
                }

                is HomeViewModel.ResponseStatus.Error -> {
                    showRetry()
                }
            }
        }
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
        (activity as AppCompatActivity).supportActionBar?.hide()
        viewModel.resume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.show()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        viewModel.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroy()
    }
}