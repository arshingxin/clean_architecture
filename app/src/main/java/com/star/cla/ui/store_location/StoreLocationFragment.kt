package com.star.cla.ui.store_location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.star.cla.databinding.FragmentStoreLocationBinding
import com.star.extension.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 全台據點
 */
class StoreLocationFragment : Fragment() {
    private var _binding: FragmentStoreLocationBinding? = null
    private val viewModel by viewModel<StoreLocationViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreLocationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        observe(viewModel.text) {
            textView.text = it
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        viewModel.resume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.destroy()
        _binding = null
    }
}