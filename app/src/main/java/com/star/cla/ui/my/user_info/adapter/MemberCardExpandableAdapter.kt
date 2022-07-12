package com.star.cla.ui.my.user_info.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.star.cla.R
import com.star.cla.ui.my.user_info.model.MemberCardUIModel
import com.star.domain.model.MemberCardInfoModel
import kotlinx.android.synthetic.main.include_member_card.view.*
import kotlinx.android.synthetic.main.include_member_card_detail_info.view.*

class MemberCardExpandableAdapter(
    var clickAction: (String, MemberCardInfoModel.MemberCardModel.CardDetailInfoModel) -> Unit,
    var memberCardModelList: MutableList<MemberCardUIModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var cardName: String? = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MemberCardUIModel.PARENT -> {
                MemberCardViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.include_member_card, parent, false
                    )
                )
            }

            MemberCardUIModel.CHILD -> {
                MemberCardDetailViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.include_member_card_detail_info, parent, false
                    )
                )
            }

            else -> {
                MemberCardViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.include_member_card, parent, false
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int = memberCardModelList.size

    fun getMemberCardList() = memberCardModelList.toMutableList()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val row = memberCardModelList[position]
        when (row.type) {
            MemberCardUIModel.PARENT -> {
                val name = row.memberCardParent?.cardName ?: ""
                (holder as MemberCardViewHolder).cardName.text = name
                cardName = name
                // 基本資料 always Expand
                if (position == 0) {
                    holder.upArrowImg.visibility = View.GONE
                    holder.downArrowImg.visibility = View.GONE
                } else {
                    if (row.isExpanded == true)
                        expandViewAndColor(holder)
                    else
                        collapseViewAndColor(holder)
                    holder.layout.setOnClickListener {
                        if (row.isExpanded == true) {
                            row.isExpanded = false
                            collapseRow(position)
                            collapseViewAndColor(holder)
                        } else {
                            row.isExpanded = true
                            expandRow(position)
                            expandViewAndColor(holder)
                        }
                    }
                }
            }

            MemberCardUIModel.CHILD -> {
                (holder as MemberCardDetailViewHolder).title.text = row.memberCardChild?.title
                holder.content.text = row.memberCardChild?.content
                holder.layout.tag = cardName
                holder.title.tag = row.memberCardChild
                holder.layout.setOnClickListener {
                    var cardInfo = holder.title.tag
                    clickAction.invoke(
                        holder.layout.tag.toString(),
                        cardInfo as MemberCardInfoModel.MemberCardModel.CardDetailInfoModel
                    )
                }
            }
        }
    }

    private fun collapseViewAndColor(holder: MemberCardViewHolder) {
        holder.upArrowImg.visibility = View.GONE
        holder.downArrowImg.visibility = View.VISIBLE
        holder.layout.setBackgroundColor(Color.WHITE)
    }

    private fun expandViewAndColor(holder: MemberCardViewHolder) {
        holder.upArrowImg.visibility = View.VISIBLE
        holder.downArrowImg.visibility = View.GONE
        holder.layout.setBackgroundColor(Color.WHITE)
    }

    override fun getItemViewType(position: Int): Int = memberCardModelList[position].type ?: -1

    private fun expandRow(position: Int) {
        val row = memberCardModelList[position]
        var nextPosition = position
        when (row.type) {
            MemberCardUIModel.PARENT -> {
                for (child in row.memberCardParent?.cardDetailInfo ?: mutableListOf()) {
                    memberCardModelList.add(
                        ++nextPosition,
                        MemberCardUIModel(MemberCardUIModel.CHILD, child)
                    )
                    refreshExpandView(true, nextPosition)
                }
            }
            MemberCardUIModel.CHILD -> {
                notifyDataSetChanged()
            }
        }
    }

    private fun refreshExpandView(stepByStepExpand: Boolean = false, nextPosition: Int = -1) {
        if (stepByStepExpand) {
            // 一個一個展開
            notifyItemInserted(nextPosition + 1)
        } else {
            // 一次全部展開
            notifyDataSetChanged()
        }
    }

    private fun collapseRow(position: Int) {
        val row = memberCardModelList[position]
        var nextPosition = position + 1
        when (row.type) {
            MemberCardUIModel.PARENT -> {
                outer_loop@ while (true) {
                    //  println("Next Position during Collapse $nextPosition size is ${shelfModelList.size} and parent is ${shelfModelList[nextPosition].type}")
                    if (nextPosition == memberCardModelList.size || memberCardModelList[nextPosition].type == MemberCardUIModel.PARENT) {
                        /* println("Inside break $nextPosition and size is ${closedShelfModelList.size}")
                         closedShelfModelList[closedShelfModelList.size-1].isExpanded = false
                         println("Modified closedShelfModelList ${closedShelfModelList.size}")*/
                        break@outer_loop
                    }
                    memberCardModelList.removeAt(nextPosition)
                }
                notifyDataSetChanged()
            }
        }
    }

    class MemberCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var layout = itemView.container_parent
        internal var cardName: TextView = itemView.card_name
        internal var downArrowImg = itemView.down_arrow
        internal var upArrowImg = itemView.up_arrow
    }

    class MemberCardDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var layout = itemView.container_child
        internal var title: TextView = itemView.title
        internal var content = itemView.content_input
    }
}