package com.star.cla.ui.my.user_info.user_info_edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.star.cla.R
import com.star.cla.base.BaseViewHolderWithController
import com.star.cla.base.Controller
import com.star.domain.model.MemberCardInfoModel

class UserInfoEditAdapter(
    private val controller: UserInfoController
) : ListAdapter<MemberCardInfoModel.MemberCardModel.CardDetailInfoModel, BaseViewHolderWithController<MemberCardInfoModel.MemberCardModel.CardDetailInfoModel, UserInfoController>>(
    diffAboutV2Right
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolderWithController<MemberCardInfoModel.MemberCardModel.CardDetailInfoModel, UserInfoController> {
        return UserInfoEditViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.include_member_card_edittext, parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: BaseViewHolderWithController<MemberCardInfoModel.MemberCardModel.CardDetailInfoModel, UserInfoController>,
        position: Int
    ) {
        holder.onBind(getItem(position), controller)
    }
}

val diffAboutV2Right = object : DiffUtil.ItemCallback<MemberCardInfoModel.MemberCardModel.CardDetailInfoModel>() {
    override fun areContentsTheSame(oldItem: MemberCardInfoModel.MemberCardModel.CardDetailInfoModel, newItem: MemberCardInfoModel.MemberCardModel.CardDetailInfoModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: MemberCardInfoModel.MemberCardModel.CardDetailInfoModel, newItem: MemberCardInfoModel.MemberCardModel.CardDetailInfoModel): Boolean {
        return oldItem.id == newItem.id
    }
}

interface UserInfoController : Controller {
    fun onItemClick(cardDetailInfoModel: MemberCardInfoModel.MemberCardModel.CardDetailInfoModel)
    fun onEditTextViewChanged(
        cardDetailInfoModel: MemberCardInfoModel.MemberCardModel.CardDetailInfoModel,
        changedText: String
    )
}