package com.example.fileexplorer.view.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fileexplorer.R
import com.example.fileexplorer.base.BaseDialog

class SortFileDialog(context: Context) : BaseDialog<String>(context) {
    private var recyclerView: RecyclerView? = null

    override fun initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(true)
        setContentView(R.layout.dialog_sort_by)
        funcStyle()
        findViews()
    }

    private fun funcStyle() {
        val window = window
        window!!.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        setCancelable(true)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun initData() {}

    private fun findViews() {
        recyclerView = findViewById(R.id.recycler)
        val sortAdapter = SortAdapter(
            context.resources.getStringArray(R.array.list_type).toList()
        ) { language ->
            if (onCallBack != null) {
                onCallBack!!.onCallBack(language)
            }
            dismiss()
        }
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = sortAdapter
        }
    }
}
