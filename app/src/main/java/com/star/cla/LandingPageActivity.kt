package com.star.cla

import android.content.Intent
import android.os.Bundle
import com.star.cla.databinding.ActivityLandingPageBinding
import com.star.cla.extension.toImage
import com.star.extension.observe

class LandingPageActivity: BaseActivity() {
    private val TAG = LandingPageActivity::class.java.simpleName
    private val DEBUG = true
    private val viewModel: LandingPageViewModel by lazy { LandingPageViewModel() }

    private var _binding: ActivityLandingPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLandingPageBinding.inflate(layoutInflater)
        hideAllBars()
        setContentView(binding.root)
        observe(viewModel.switchToMainActivity) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.landingImage.toImage(R.drawable.bg_landing_page, fill = true)
    }

    override fun onResume() {
        super.onResume()
        viewModel.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}