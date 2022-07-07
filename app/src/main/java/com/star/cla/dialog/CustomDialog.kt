package com.star.cla.dialog

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.star.cla.R
import com.star.extension.log.logStar


open class CustomDialog(
    private val titleText: String? = "",
    private val contentText: String? = "",
    private val leftButtonText: String? = "",
    private val leftButtonAction: (() -> Unit?)? = null,
    private val rightButtonText: String? = "",
    private val rightAction: (() -> Unit?)? = null,
    private val widthRatio: Float? = 0.9f
) : DialogFragment() {
    private val TAG = CustomDialog::class.java.simpleName
    private val DEBUG = true
    lateinit var title: TextView
    lateinit var content: TextView
    private lateinit var leftButton: TextView
    private lateinit var rightButton: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_custom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = view.findViewById(R.id.title)
        if (titleText?.isEmpty() == true)
            title.isVisible = false
        else {
            title.isVisible = true
            title.text = titleText
        }

        content = view.findViewById(R.id.content)
        if (contentText?.isEmpty() == true)
            content.isVisible = false
        else {
            content.isVisible = true
            content.text = contentText
        }

        leftButton = view.findViewById(R.id.left_button)
        if (leftButtonText?.isEmpty() == false)
            leftButton.text = leftButtonText
        leftButton.isVisible = leftButtonText?.isEmpty() != true
        leftButton.setOnClickListener {
            leftButtonAction?.invoke()
        }

        rightButton = view.findViewById(R.id.right_button)
        if (rightButtonText?.isEmpty() == false)
            rightButton.text = rightButtonText
        rightButton.isVisible = rightButtonText?.isEmpty() != true
        rightButton.setOnClickListener {
            rightAction?.invoke()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            setCanceledOnTouchOutside(false)
            window?.apply {
                if (DEBUG)
                    logStar(
                        TAG,
                        "onStart w: ${Resources.getSystem().displayMetrics.widthPixels}, " +
                                "h: ${Resources.getSystem().displayMetrics.heightPixels}"
                    )
                val width = Resources.getSystem().displayMetrics.widthPixels * (widthRatio ?: 0.9f)
                setLayout(
                    width.toInt(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                decorView.setBackgroundResource(R.drawable.bg_user_top_layout)
            }
        }
    }
}