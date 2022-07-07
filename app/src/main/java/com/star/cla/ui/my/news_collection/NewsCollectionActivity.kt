package com.star.cla.ui.my.news_collection

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.star.cla.BaseActivity
import com.star.cla.BuildConfig
import com.star.cla.R
import com.star.cla.databinding.ActivityNewsCollectionBinding
import com.star.cla.extension.setTextColor
import com.star.cla.ui.my.news_edit.NewsEditActivity
import com.star.extension.log.logStar

/**
 * 新聞收藏
 */
class NewsCollectionActivity : BaseActivity() {
    private val TAG = NewsCollectionActivity::class.java.simpleName
    private val DEBUG = true
    private var _binding: ActivityNewsCollectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (DEBUG) logStar(TAG, "onCreate")
        _binding = ActivityNewsCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.apply {
            back.setOnClickListener {
                finish()
            }
            title.text = "新聞收藏"
        }
        if (BuildConfig.DEBUG || (binding.newsList.adapter?.itemCount ?: 0) > 0) {
            binding.toolbar.actionOne.apply {
                text = "編輯"
                isVisible = true
                setTextColor(applicationContext, R.color.blue)
                setOnClickListener {
                    if (DEBUG) logStar(TAG, "edit")
                    startActivity(Intent(this@NewsCollectionActivity, NewsEditActivity::class.java))
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}