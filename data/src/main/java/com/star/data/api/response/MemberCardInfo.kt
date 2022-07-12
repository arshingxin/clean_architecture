package com.star.data.api.response

data class MemberCardInfo(val cards: MutableList<MemberCard>? = mutableListOf()) {
    data class MemberCard(
        val cardName: String? = "",
        val cardDetailInfo: MutableList<CardDetailInfo>? = mutableListOf()
    ) {
        data class CardDetailInfo(
            val title: String? = "",
            val content: String? = "",
            val editable: Boolean? = false,
            val hint: String? = "",
            val type: String? = MemberDetailInfoType.EditText,
            val item_list: ItemList? = null
        )
    }
}

data class ItemList(val title: String? = "", val list: List<String>? = listOf())

object MemberDetailInfoType {
    val EditText = "edit_text"
    val BottomSheet = "bottom_sheet"
    val SelectionList = "selection_list"
}