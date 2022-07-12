package com.star.cla.ui.my.user_info.user_info_edit

import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.star.cla.base.BaseViewHolderWithController
import com.star.cla.extension.focus
import com.star.data.api.response.MemberDetailInfoType
import com.star.domain.model.MemberCardInfoModel
import com.star.extension.log.logStar
import kotlinx.android.synthetic.main.include_member_card_edittext.view.*


class UserInfoEditViewHolder(itemView: View) :
    BaseViewHolderWithController<MemberCardInfoModel.MemberCardModel.CardDetailInfoModel,
            UserInfoController>(itemView) {
    private val TAG = UserInfoEditViewHolder::class.java.simpleName
    private val DEBUG = true

    override fun onBind(
        data: MemberCardInfoModel.MemberCardModel.CardDetailInfoModel,
        controller: UserInfoController
    ) {
        itemView.title.text = data.title
        if (data.type != MemberDetailInfoType.EditText) {
            itemView.content.text = data.content ?: ""
            itemView.content.isVisible = true
            itemView.content_input.isVisible = false
            itemView.setOnClickListener {
                itemView.focus()
                controller.onItemClick(data)
            }
        } else {
            itemView.content_input.setText(data.content ?: "")
            itemView.content.isVisible = false
            itemView.content_input.isVisible = true
            itemView.setOnClickListener(null)
            itemView.content_input.addTextChangedListener {
                if (DEBUG) logStar(TAG, "addTextChangedListener $it")
                controller.onEditTextViewChanged(data, it.toString())
            }
        }
        if (data.hint?.isEmpty() == true) {
            itemView.hint.isVisible = false
            itemView.hint.text = ""
        } else {
            itemView.hint.isVisible = true
            itemView.hint.text = data.hint
        }
    }
}