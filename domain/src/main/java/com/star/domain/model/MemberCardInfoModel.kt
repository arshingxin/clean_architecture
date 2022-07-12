package com.star.domain.model

import com.star.data.api.response.ItemList
import com.star.data.api.response.MemberCardInfo
import com.star.data.api.response.MemberDetailInfoType

data class MemberCardInfoModel(val cards: MutableList<MemberCardModel>? = mutableListOf()) {
    data class MemberCardModel(
        val cardName: String? = "",
        val cardDetailInfo: MutableList<CardDetailInfoModel>? = mutableListOf()
    ) {
        data class CardDetailInfoModel(
            var id: Int? = -1,
            var title: String? = "",
            var content: String? = "",
            var editable: Boolean? = false,
            var hint: String? = "",
            var type: String? = MemberDetailInfoType.EditText,
            var item_list: ItemList? = null
        ) {
            var position: Int = -1
        }
    }
}

fun MemberCardInfo.toMemberCardInfoModel(): MemberCardInfoModel {
    val cardList = mutableListOf<MemberCardInfoModel.MemberCardModel>()
    cards?.forEach { memberCard ->
        val detailList = mutableListOf<MemberCardInfoModel.MemberCardModel.CardDetailInfoModel>()
        memberCard.cardDetailInfo?.forEachIndexed { index, cardDetailInfo ->
            detailList.add(
                MemberCardInfoModel.MemberCardModel.CardDetailInfoModel(
                    index,
                    cardDetailInfo.title,
                    cardDetailInfo.content,
                    cardDetailInfo.editable,
                    cardDetailInfo.hint,
                    cardDetailInfo.type,
                    cardDetailInfo.item_list
                )
            )
        }
        cardList.add(
            MemberCardInfoModel.MemberCardModel(
                memberCard.cardName, detailList
            )
        )
    }
    return MemberCardInfoModel(cardList)
}
