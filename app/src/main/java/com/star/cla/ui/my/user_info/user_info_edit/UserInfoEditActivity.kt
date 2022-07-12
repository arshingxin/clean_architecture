package com.star.cla.ui.my.user_info.user_info_edit

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamsa.twosteppickerdialog.OnStepPickListener
import com.hamsa.twosteppickerdialog.TwoStepPickerDialog
import com.star.cla.MainApplication
import com.star.cla.R
import com.star.cla.base.BaseActivity
import com.star.cla.databinding.ActivityUserInfoEditBinding
import com.star.cla.dialog.CustomBottomSheetDialog
import com.star.cla.extension.getAssetJson
import com.star.cla.extension.hideSoftKeyboard
import com.star.cla.extension.setTextColor
import com.star.data.api.response.MemberDetailInfoType
import com.star.domain.model.MemberCardInfoModel
import com.star.extension.log.logStar
import com.star.extension.observe
import com.star.extension.toJson
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 會員基本資輛
 */
class UserInfoEditActivity : BaseActivity() {
    private val TAG = UserInfoEditActivity::class.java.simpleName
    private val DEBUG = true
    private var _binding: ActivityUserInfoEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<UserInfoEditViewModel>()
    private var userInfoEditAdapter: UserInfoEditAdapter? = null
    private var twoStepPickerDialog: TwoStepPickerDialog? = null
    private var customBottomSheetDialog: CustomBottomSheetDialog? = null
    private var userInfoController: UserInfoController? = object : UserInfoController {
        override fun onItemClick(cardDetailInfoModel: MemberCardInfoModel.MemberCardModel.CardDetailInfoModel) {
            if (DEBUG) logStar(TAG, "onItemClick: [${cardDetailInfoModel.position}]$cardDetailInfoModel")
            MainApplication.uiThread { hideSoftKeyboard() }
            when (cardDetailInfoModel.type) {
                MemberDetailInfoType.BottomSheet -> {
                    customBottomSheetDialog?.dismiss()
                    customBottomSheetDialog = CustomBottomSheetDialog(this@UserInfoEditActivity)
                        .setList(cardDetailInfoModel.item_list?.list?.toMutableList() ?: mutableListOf())
                        .setTitle(cardDetailInfoModel.item_list?.title ?: "")
                        .setListener(object : CustomBottomSheetDialog.CustomBottomSheetDialogListener{
                            override fun onItemClick(data: String) {
                                customBottomSheetDialog = null
                                userInfoEditAdapter?.apply {
                                    val refreshPosition = cardDetailInfoModel.position
                                    currentList.getOrElse(refreshPosition) { null }?.content = data
                                    if (DEBUG) logStar(TAG, "[${refreshPosition}]${currentList.getOrElse(refreshPosition) { null }?.content}")
                                    notifyItemChanged(refreshPosition)
                                }
                            }
                        })
                    customBottomSheetDialog?.show()
                }

                MemberDetailInfoType.SelectionList -> {
                    if (cardDetailInfoModel.item_list?.title == "請選擇鄉鎮市區") {
                        viewModel.parserTwZipCode(getAssetJson("tw_zipcodes.json"), cardDetailInfoModel.position)
                    } else {

                    }
                }
            }
        }

        override fun onEditTextViewChanged(
            cardDetailInfoModel: MemberCardInfoModel.MemberCardModel.CardDetailInfoModel,
            changedText: String
        ) {
            userInfoEditAdapter?.currentList?.find { it.position == cardDetailInfoModel.position }?.content = changedText
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (DEBUG) logStar(TAG, "onCreate")
        _binding = ActivityUserInfoEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observe(viewModel.editDataFailLiveData) {
            showToast("無法取得基本資料!")
        }
        observe(viewModel.showLiveData) {
            userInfoEditAdapter = UserInfoEditAdapter(userInfoController!!)
            userInfoEditAdapter?.submitList(it)
            binding.memberEditList.apply {
                adapter = userInfoEditAdapter
                layoutManager = LinearLayoutManager(this@UserInfoEditActivity)
            }
        }
        observe(viewModel.saveSuccessLiveData) {
            if (DEBUG) logStar(TAG, "saveSuccessLiveData: $it")
            when (it) {
                is UserInfoEditViewModel.SaveStatus.Loading -> {
                    showLoading()
                }

                is UserInfoEditViewModel.SaveStatus.Success -> {
                    hideAllView()
                    showToast("基本資料變更成功")
                    finish()
                }

                is UserInfoEditViewModel.SaveStatus.Fail -> {
                    showToast(it.msg)
                }
            }
        }
        observe(viewModel.showZipCodeLiveData) {
            // TODO 選擇鄉鎮市區的ui
            if (DEBUG) {
                logStar(TAG, "${it.first.base}")
                logStar(TAG, "${it.first.step}")
            }
            twoStepPickerDialog = TwoStepPickerDialog.Builder(this)
                .withBaseData(it.first.base ?: mutableListOf())
                .withStepData(it.first.step?.nameList ?: mutableListOf())
                .withOkButton("確定")
                .withCancelButton("取消")
                .withBaseOnLeft(true)
                .withInitialBaseSelected(0)
                .withInitialStepSelected(0)
                .withDialogListener(object : OnStepPickListener {
                    override fun onStepPicked(step: Int, pos: Int) {
                        val city = "${it.first.step?.codeList?.getOrElse(step){ null }?.getOrElse(pos){ null }}" +
                                "${it.first.base?.getOrElse(step) { null }}" +
                                "${it.first.step?.nameList?.getOrElse(step){ null }?.getOrElse(pos){ null }}"
                        if (DEBUG) logStar(TAG, city)
                        userInfoEditAdapter?.currentList?.filter { cardDetailInfoModel ->
                            cardDetailInfoModel.title == "鄉鎮市區"
                        }?.getOrElse(0) { null }?.content = city
                        userInfoEditAdapter?.notifyItemChanged(it.second)
                    }

                    override fun onDismissed() {
                        twoStepPickerDialog = null
                    }
                })
                .build()
            twoStepPickerDialog?.show()

        }
        observe(viewModel.showErrorToastLiveData) {
            showToast(it)
        }
        observe(viewModel.isChangedLiveData) {
            viewModel.postSaveUserInfo()
        }
        binding.toolbar.apply {
            back.setOnClickListener {
                finish()
            }
            title.text = "編輯基本資料"
            actionOne.apply {
                text = "儲存"
                isVisible = true
                setTextColor(applicationContext, R.color.blue)
                setOnClickListener {
                    if (DEBUG) logStar(TAG, "儲存")
                    viewModel.isChanged(userInfoEditAdapter?.currentList?.toJson())
                }
            }
        }
        initData()
    }

    private fun initData() {
        val json = intent.extras?.getString(viewModel.getEditUserBasicInfoKey())
        if (DEBUG) logStar(TAG, json)
        viewModel.init(json)
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
        userInfoEditAdapter = null
        userInfoController = null
        _binding = null
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

    override fun onBackPressed() {
        super.onBackPressed()
        if (DEBUG) logStar(TAG, "onBackPressed")
    }
}