package com.star.domain.usecase

import com.star.data.api.response.MemberCardInfo
import com.star.data.repository.MemberCardRepo
import com.star.data.repository_cache.LocalMemberCardCache
import com.star.domain.model.MemberCardInfoModel
import com.star.domain.model.toMemberCardInfoModel
import com.star.extension.log.log
import com.star.extension.report
import com.star.extension.throwException
import com.star.extension.toDataBean
import com.star.extension.toJson
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface IMemberCardUseCase {
    fun getRemoteMemberCard(): Observable<MemberCardInfoModel>

    fun getLocalMemberCard(): Observable<MemberCardInfoModel>

    fun getEditUserBasicInfoKey(): String
}

class MemberCardUseCase : KoinComponent, IMemberCardUseCase {
    private val TAG = MemberCardUseCase::class.java.simpleName
    private val DEBUG = false
    private val memberCardRepo: MemberCardRepo by inject()
    private val localMemberCardCache: LocalMemberCardCache by inject()

    override fun getRemoteMemberCard(): Observable<MemberCardInfoModel> =
        memberCardRepo.getMemberCard()
            .map {
                localMemberCardCache.saveMemberCard(it.toJson())
            }
            .onErrorReturn {
                it.log(TAG, "remote member info error")
                localMemberCardCache.saveMemberCard("")
                MemberCardInfoModel()
            }
            .concatMap { getLocalMemberCard() }
            .subscribeOn(Schedulers.io())

    override fun getLocalMemberCard(): Observable<MemberCardInfoModel> =
        localMemberCardCache.getMemberCard()
            .map {
                val json = "{\n" +
                        "  \"cards\": [\n" +
                        "    {\n" +
                        "      \"cardName\": \"基本資料\",\n" +
                        "      \"cardDetailInfo\": [\n" +
                        "        {\"title\": \"姓名\", \"content\": \"劉宜鑫\", \"editable\": true, \"type\": \"edit_text\"},\n" +
                        "        {\"title\": \"手機號碼\", \"content\": \"0963000908\"},\n" +
                        "        {\"title\": \"身分證末四碼\", \"content\": \"2913\"},\n" +
                        "        {\"title\": \"鄉鎮市區\", \"content\": \"244新北市林口區\", \"editable\": true, \"type\": \"selection_list\", \"item_list\": { \"title\": \"請選擇鄉鎮市區\", \"list\": []}},\n" +
                        "        {\"title\": \"詳細地址\", \"content\": \"文化三路二段22號4樓之3\", \"editable\": true, \"type\": \"edit_text\", \"hint\": \"線上購物中獎發票寄送使用，敬請詳填\"},\n" +
                        "        {\"title\": \"E-mail\", \"content\": \"arshingxin@gmail.com\", \"editable\": true, \"type\": \"edit_text\", \"hint\": \"線上購物中獎發票寄送使用，敬請詳填\"},\n" +
                        "        {\"title\": \"性別\", \"content\": \"生理男性\", \"editable\": true, \"type\": \"bottom_sheet\", \"item_list\": { \"title\": \"選擇性別\", \"list\": [\"生理女性\",\"生理男性\",\"未設定\"]}},\n" +
                        "        {\"title\": \"生日\", \"content\": \"1985/02/03\"}\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"cardName\": \"會員卡 東森寵物雲\",\n" +
                        "      \"cardDetailInfo\": [\n" +
                        "        {\"title\": \"會員身分\", \"content\": \"集團員工\"},\n" +
                        "        {\"title\": \"卡號\", \"content\": \"ET00485013\"},\n" +
                        "        {\"title\": \"生效日\", \"content\": \"2022/06/28\"},\n" +
                        "        {\"title\": \"發卡店家\", \"content\": \"APP\"}\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"cardName\": \"會員卡 金王子\",\n" +
                        "      \"cardDetailInfo\": [\n" +
                        "        {\"title\": \"會員身分\", \"content\": \"集團員工\"},\n" +
                        "        {\"title\": \"卡號\", \"content\": \"GP11612146\"},\n" +
                        "        {\"title\": \"生效日\", \"content\": \"2022/06/28\"},\n" +
                        "        {\"title\": \"發卡店家\", \"content\": \"APP\"}\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"cardName\": \"會員卡 寵物王國\",\n" +
                        "      \"cardDetailInfo\": [\n" +
                        "        {\"title\": \"會員身分\", \"content\": \"集團員工\"},\n" +
                        "        {\"title\": \"卡號\", \"content\": \"PK11612146\"},\n" +
                        "        {\"title\": \"生效日\", \"content\": \"2022/06/28\"},\n" +
                        "        {\"title\": \"發卡店家\", \"content\": \"桃園巨蛋店\"}\n" +
                        "      ]\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n"
                json.toDataBean(MemberCardInfo::class.java)?.toMemberCardInfoModel()
                    ?: throwException(TAG, "getLocalMemberCard error")
            }
            .onErrorReturn {
                it.report(TAG)
                MemberCardInfoModel()
            }
            .subscribeOn(Schedulers.io())

    override fun getEditUserBasicInfoKey(): String = memberCardRepo.getEditUserBasicInfoKey()
}