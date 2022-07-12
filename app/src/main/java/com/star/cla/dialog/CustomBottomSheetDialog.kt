package com.star.cla.dialog

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.star.cla.databinding.DialogBottomsheetCustomBinding
import com.star.extension.log.logStar

open class CustomBottomSheetDialog constructor(context: Context) :
    BottomSheetDialog(context), CustomBottomSheetController {
    private val DEBUG = true
    private val TAG = CustomBottomSheetDialog::class.java.simpleName
    private var _binding: DialogBottomsheetCustomBinding? = null
    private val binding get() = _binding!!
    private var customBottomSheetAdapter: CustomBottomSheetAdapter? = null
    private var title: String = ""
    private var dataList: MutableList<String> = mutableListOf()
    private var canCancel = true
    private var listener: CustomBottomSheetDialogListener? = null

    interface CustomBottomSheetDialogListener {
        fun onItemClick(data: String)
    }

    init {
        setCanCancel(canCancel)
    }

    fun setCanCancel(b: Boolean): CustomBottomSheetDialog {
        setCancelable(b)
        return this
    }

    fun setListener(listener: CustomBottomSheetDialogListener): CustomBottomSheetDialog {
        this.listener = listener
        return this
    }

    fun setTitle(title: String): CustomBottomSheetDialog {
        this.title = title
        return this
    }

    fun setList(data: MutableList<String>): CustomBottomSheetDialog {
        dataList.clear()
        dataList = data
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DialogBottomsheetCustomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setLayout(MATCH_PARENT, WRAP_CONTENT)
        binding.list.apply {
            customBottomSheetAdapter = CustomBottomSheetAdapter(this@CustomBottomSheetDialog)
            adapter = customBottomSheetAdapter
            layoutManager = LinearLayoutManager(context)
        }
        customBottomSheetAdapter?.submitList(dataList)
    }

    override fun onItemClick(data: String, position: Int) {
        if (DEBUG) logStar(TAG, "onItemClick: [$position]$data")
        listener?.onItemClick(data)
        dismiss()
    }
}