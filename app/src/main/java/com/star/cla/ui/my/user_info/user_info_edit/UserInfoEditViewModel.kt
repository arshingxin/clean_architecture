package com.star.cla.ui.my.user_info.user_info_edit

import androidx.lifecycle.MutableLiveData
import com.star.cla.AutoDisposeViewModel
import com.star.cla.ui.my.user_info.model.MemberCardUIModel
import com.star.data.api.response.TwZipCode
import com.star.domain.model.MemberCardInfoModel
import com.star.domain.usecase.MemberCardUseCase
import com.star.extension.isJson
import com.star.extension.log.logStar
import com.star.extension.throwException
import com.star.extension.toDataBean
import com.star.extension.toJson
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers.io
import org.koin.core.component.inject
import kotlin.collections.set

class UserInfoEditViewModel : AutoDisposeViewModel() {
    private val TAG = UserInfoEditViewModel::class.java.simpleName
    private val DEBUG = true
    private val memberCardUseCase: MemberCardUseCase by inject()
    private var initEditJson = ""
    private var _editDataFailLiveData = MutableLiveData<Boolean>()
    val editDataFailLiveData = _editDataFailLiveData
    private var _showLiveData = MutableLiveData<MutableList<MemberCardInfoModel.MemberCardModel.CardDetailInfoModel>>()
    val showLiveData = _showLiveData
    private var _saveSuccessLiveData = MutableLiveData<SaveStatus>()
    val saveSuccessLiveData = _saveSuccessLiveData
    data class ZipCodeUIModel(var base: MutableList<String>? = mutableListOf(), var step: StepUIModel? = StepUIModel())
    data class StepUIModel(var codeList: MutableList<MutableList<Int>>? = mutableListOf(), var nameList: MutableList<MutableList<String>>? = mutableListOf())
    private var _showZipCodeLiveData = MutableLiveData<Pair<ZipCodeUIModel, Int>>()
    val showZipCodeLiveData = _showZipCodeLiveData
    private var _showErrorToastLiveData = MutableLiveData<String>()
    val showErrorToastLiveData = _showErrorToastLiveData
    private var _isChangedLiveData = MutableLiveData<Boolean>()
    val isChangedLiveData = _isChangedLiveData
    private var zipCodeUIModel: ZipCodeUIModel? = null
    private var _showExitDialogLiveData = MutableLiveData<Boolean>()
    val showExitDialogLiveData = _showExitDialogLiveData

    override fun init(data: Any?) {
        if (data == null || data !is String || !data.isJson()) {
            postEditError()
        } else {
            val key = "init"
            disposableMap[key] = Observable
                .just(true)
                .map {
                    if (DEBUG) logStar(TAG, "$key")
                    data.toDataBean(MemberCardUIModel::class.java) ?: MemberCardUIModel()
                }
                .map { memberCardUIModel ->
                    if (memberCardUIModel.type == -1) {
                        postEditError()
                    } else {
                        val list = memberCardUIModel.filter().toMutableList()
                        list.forEachIndexed { index, cardDetailInfoModel ->
                            cardDetailInfoModel.position = index
                        }
                        initEditJson = list.toJson()
                        _showLiveData.postValue(list)
                    }
                }
                .subscribeOn(io())
                .add(key, TAG)
        }
    }

    override fun resume() {
        if (DEBUG) logStar(TAG, "resume")
    }

    private fun postEditError() {
        initEditJson = ""
        _editDataFailLiveData.postValue(false)
        val msg = "資本資料錯誤!"
        if (DEBUG) logStar(TAG, msg)
        throwException(TAG, msg)
    }

    override fun pause() {
    }

    override fun destroy() {
    }

    sealed class SaveStatus {
        object Loading: SaveStatus()
        object Success: SaveStatus()
        data class Fail(val msg: String): SaveStatus()
    }
    fun postSaveUserInfo() {
        val key = "postSaveUserInfo"
        disposableMap[key] = Observable
            .just(true)
            .map { if (DEBUG) logStar(TAG, key) }
            .map { _saveSuccessLiveData.postValue(SaveStatus.Loading) }
            .map {
                _saveSuccessLiveData.postValue(SaveStatus.Success)
            }
            .subscribeOn(io())
            .add(key, TAG) {
                _saveSuccessLiveData.postValue(SaveStatus.Fail("無法儲存變更基本資料!"))
            }
    }

    fun getEditUserBasicInfoKey() = memberCardUseCase.getEditUserBasicInfoKey()

    fun parserTwZipCode(twZipCodeJson: String, position: Int) {
        if (twZipCodeJson.isNotEmpty() && twZipCodeJson.isJson()) {
            val key = "parserTwZipCode"
            disposableMap[key] = Observable
                .just(true)
                .map {
                    val data = twZipCodeJson.toDataBean(TwZipCode::class.java)
                    if (DEBUG) logStar(TAG, "parserTwZipCode data: $data")
                    if (zipCodeUIModel == null) {
                        if (DEBUG) logStar(TAG, "create tw zip code data")
                        zipCodeUIModel = ZipCodeUIModel()
                        zipCodeUIModel?.base = mutableListOf()
                        zipCodeUIModel?.step = StepUIModel()
                        val stepUIModel = StepUIModel()
                        stepUIModel.codeList = mutableListOf()
                        stepUIModel.nameList = mutableListOf()
                        data?.cities?.forEachIndexed { index, city ->
                            zipCodeUIModel?.base?.add(city.name ?: "")
                            val codes = mutableListOf<Int>()
                            val names = mutableListOf<String>()
                            city.region?.forEach { region ->
                                codes.add(region.code ?: -1)
                                names.add(region.name ?: "")
                            }
                            stepUIModel.codeList?.add(index, codes)
                            stepUIModel.nameList?.add(index, names)
                        }
                        zipCodeUIModel?.step = stepUIModel
                    }
                    _showZipCodeLiveData.postValue(Pair(zipCodeUIModel!!, position))
                }
                .subscribeOn(io())
                .add(key, TAG)
        } else {
            _showErrorToastLiveData.postValue("無法編輯鄉鎮市區!")
        }
    }

    fun isChanged(currentEditJson: String?) {
        val key = "isChanged"
        disposableMap[key] = Observable
            .just(true)
            .map { if (DEBUG) logStar(TAG, key) }
            .map {
                if (DEBUG) {
                    logStar(TAG, "currentEditJson:$currentEditJson")
                    logStar(TAG, "editJson:$initEditJson")
                }
                if (currentEditJson.isNullOrEmpty()) _showErrorToastLiveData.postValue("基本資料錯誤!")
                else if (initEditJson == currentEditJson) {
                    if (DEBUG) logStar(TAG, "基本資料未變更")
                    _saveSuccessLiveData.postValue(SaveStatus.Success)
                } else {
                    if (DEBUG) logStar(TAG, "基本資已變更, 需打API")
                    _isChangedLiveData.postValue(true)
                }
            }
            .subscribeOn(io())
            .add(key, TAG)
    }

    fun showExitDialog(currentEditJson: String?) {
        val key = "showExitDialog"
        disposableMap[key] = Observable
            .just(true)
            .map {
                if (initEditJson == currentEditJson) {
                    if (DEBUG) logStar(TAG, "基本資料未變更")
                    _showExitDialogLiveData.postValue(false)
                } else {
                    _showExitDialogLiveData.postValue(true)
                }
            }
            .subscribeOn(io())
            .add(key, TAG)
    }
}