package com.star.cla.ui.my.user_info.model

import com.star.domain.model.MemberCardInfoModel

class MemberCardUIModel {
    companion object {
        const val PARENT = 1
        const val CHILD = 2
    }

    var memberCardParent: MemberCardInfoModel.MemberCardModel? = MemberCardInfoModel.MemberCardModel()
    var type: Int? = -1
    var memberCardChild: MemberCardInfoModel.MemberCardModel.CardDetailInfoModel? = MemberCardInfoModel.MemberCardModel.CardDetailInfoModel()
    var isExpanded: Boolean? = false
    private var isCloseShown: Boolean? = false

    fun filter() = memberCardParent?.cardDetailInfo?.filter {
        it.editable == true
    } ?: mutableListOf()

    constructor(
        type: Int? = -1,
        memberCardParent: MemberCardInfoModel.MemberCardModel? = MemberCardInfoModel.MemberCardModel(),
        isExpanded: Boolean? = false,
        isCloseShown: Boolean? = false
    ) {
        this.type = type
        this.memberCardParent = memberCardParent
        this.isExpanded = isExpanded
        this.isCloseShown = isCloseShown
    }

    constructor(
        type: Int,
        memberCardChild: MemberCardInfoModel.MemberCardModel.CardDetailInfoModel,
        isExpanded: Boolean = false,
        isCloseShown: Boolean = false
    ) {
        this.type = type
        this.memberCardChild = memberCardChild
        this.isExpanded = isExpanded
        this.isCloseShown = isCloseShown
    }
}