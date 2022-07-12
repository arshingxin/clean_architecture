package com.star.cla.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.star.cla.R
import com.star.cla.base.BaseViewHolderWithController
import com.star.cla.base.Controller
import com.star.cla.ui.my.user_info.user_info_edit.UserInfoEditViewHolder
import kotlinx.android.synthetic.main.include_bottom_sheet_item.view.*

class CustomBottomSheetAdapter(
    private val controller: CustomBottomSheetController
) : ListAdapter<String, BaseViewHolderWithController<String, CustomBottomSheetController>>(
    diffAboutV2Right
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolderWithController<String, CustomBottomSheetController> {
        return CustomBottomSheetViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.include_bottom_sheet_item, parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: BaseViewHolderWithController<String, CustomBottomSheetController>,
        position: Int
    ) {
        holder.onBind(getItem(position), controller)
    }
}

class CustomBottomSheetViewHolder(itemView: View) :
    BaseViewHolderWithController<String, CustomBottomSheetController>(itemView) {
    private val TAG = UserInfoEditViewHolder::class.java.simpleName
    private val DEBUG = true

    override fun onBind(
        data: String,
        controller: CustomBottomSheetController
    ) {
        itemView.name.text = data
        itemView.setOnClickListener {
            controller.onItemClick(data, bindingAdapterPosition)
        }
    }
}

val diffAboutV2Right = object : DiffUtil.ItemCallback<String>() {
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

interface CustomBottomSheetController : Controller {
    fun onItemClick(data: String, position: Int)
}