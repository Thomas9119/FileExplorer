package com.example.fileexplorer.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle

abstract class BaseDialog<T> constructor(context: Context) : Dialog(context) {
    var mContext: Context = context
    var onCallBack: CallbackDismiss<T>? = null

    init {
        this.mContext = context
    }

    protected abstract fun initView()

    protected abstract fun initData()

    open fun setCallBackDismiss(callBack: CallbackDismiss<T>?) {
        onCallBack = callBack
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    interface CallbackDismiss<T> {
        fun onCallBack(data: T)
    }
}