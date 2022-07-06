package com.star.cla.ui.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.star.cla.databinding.FragmentMyBinding
import com.star.extension.observe

class MyFragment : Fragment() {

    private var _binding: FragmentMyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MyViewModel::class.java]

        _binding = FragmentMyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMy
        observe(myViewModel.text) {
            textView.text = it
        }
        return root
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