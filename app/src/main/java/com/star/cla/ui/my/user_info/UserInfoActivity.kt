package com.star.cla.ui.my.user_info

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.star.cla.R
import com.star.cla.base.BaseActivity
import com.star.cla.databinding.ActivityUserInfoBinding
import com.star.cla.extension.setTextColor
import com.star.cla.ui.my.user_info.adapter.MemberCardExpandableAdapter
import com.star.cla.ui.my.user_info.user_info_edit.UserInfoEditActivity
import com.star.extension.log.logStar
import com.star.extension.observe
import com.star.extension.toJson
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 檢視個人資料
 */
class UserInfoActivity: BaseActivity() {
    private val TAG = UserInfoActivity::class.java.simpleName
    private val DEBUG = true
    private var _binding: ActivityUserInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<UserInfoViewModel>()
    private var memberCardExpandableAdapter: MemberCardExpandableAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (DEBUG) logStar(TAG, "onCreate")
        _binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observe(viewModel.showMemberLiveData) { memberCardList ->
            memberCardExpandableAdapter = MemberCardExpandableAdapter({ tag, cardDetailInfoModel ->
                if (DEBUG) logStar(TAG, "tag: $tag, cardDetailInfoModel: $cardDetailInfoModel")
            }, memberCardList)
            memberCardExpandableAdapter?.let { memberCardExpandableAdapter ->
                val linearLayoutManager = LinearLayoutManager(this)
                binding.memberList.apply {
                    layoutManager = linearLayoutManager
                    adapter = memberCardExpandableAdapter
                    addItemDecoration(DividerItemDecoration(this@UserInfoActivity, DividerItemDecoration.VERTICAL))
                }
                memberCardExpandableAdapter.notifyDataSetChanged()
            }
        }
        binding.toolbar.apply {
            back.setOnClickListener {
                finish()
            }
            title.text = "會員基本資料"
            actionOne.apply {
                text = "編輯"
                isVisible = true
                setTextColor(applicationContext, R.color.blue)
                setOnClickListener {
                    if (DEBUG) logStar(TAG, "編輯")
                    memberCardExpandableAdapter?.getMemberCardList()?.getOrElse(0) { null }?.apply {
                        if (DEBUG) logStar(TAG, toJson())
                        val intent = Intent(this@UserInfoActivity, UserInfoEditActivity::class.java)
                        intent.putExtra(viewModel.getEditUserBasicInfoKey(), toJson())
                        startActivity(intent)
                    }
                }
            }
        }
        viewModel.init()
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