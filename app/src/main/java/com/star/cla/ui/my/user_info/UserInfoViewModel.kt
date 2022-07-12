package com.star.cla.ui.my.user_info

import androidx.lifecycle.MutableLiveData
import com.star.cla.AutoDisposeViewModel
import com.star.cla.ui.my.user_info.model.MemberCardUIModel
import com.star.domain.usecase.MemberCardUseCase
import com.star.extension.log.logStar
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers.io
import org.koin.core.component.inject

class UserInfoViewModel: AutoDisposeViewModel() {
    private val TAG = UserInfoViewModel::class.java.simpleName
    private val DEBUG = true
    private val memberCardUseCase: MemberCardUseCase by inject()
    private val _showMemberLiveData = MutableLiveData<MutableList<MemberCardUIModel>>()
    val showMemberLiveData = _showMemberLiveData

    override fun init(data: Any?) {
        val key = "init"
        disposableMap[key] = Observable
            .just(true)
            .map {
                if (DEBUG) logStar(TAG, key)
            }
            .concatMap { memberCardUseCase.getLocalMemberCard() }
            .map { memberCardInfoModel ->
                if (DEBUG) logStar(TAG, "$memberCardInfoModel")
                var uiModels = mutableListOf<MemberCardUIModel>()
                memberCardInfoModel.cards?.forEachIndexed loop@ { index, memberCardModel ->
                    if (index == 0) {
                        uiModels.add(MemberCardUIModel(MemberCardUIModel.PARENT, memberCardModel, true))
                        for(detail in memberCardModel.cardDetailInfo ?: mutableListOf()){
                            uiModels.add(MemberCardUIModel(MemberCardUIModel.CHILD, detail))
                        }
                    } else uiModels.add(MemberCardUIModel(MemberCardUIModel.PARENT, memberCardModel))
                }
                _showMemberLiveData.postValue(uiModels)
            }
            .subscribeOn(io())
            .add(key, TAG)
    }

    override fun resume() {
        val key = "resume"
        disposableMap[key] = Observable
            .just(true)
            .map {
                if (DEBUG) logStar(TAG, key)
            }
            .add(key, TAG)
    }

    override fun pause() {
    }

    override fun destroy() {
    }

    fun getEditUserBasicInfoKey() = memberCardUseCase.getEditUserBasicInfoKey()

    fun isChanged() {
        val key = "isChanged"
        disposableMap[key] = Observable
            .just(true)
            .map { if (DEBUG) logStar(TAG, key) }
            .concatMap { memberCardUseCase.getLocalMemberCard() }
            .subscribeOn(io())
            .add(key, TAG)
    }
}