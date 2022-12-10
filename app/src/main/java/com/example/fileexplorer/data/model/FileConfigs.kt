package com.example.fileexplorer.data.model

import android.os.Environment

object FileConfigs {
    const val SINGLE_MODE = 0

    const val MULTI_MODE = 1

    const val FILE_SELECT = 0

    const val DIR_SELECT = 1

    const val FILE_AND_DIR_SELECT = 2

    const val DIRECTORY_SEPERATOR = "/"

    const val STORAGE_DIR = "mnt"



//    const val DEFAULT_DIR = Environment.getExternalStorageDirectory().absolutePath
    fun defaultStorage() = Environment.getExternalStorageDirectory().absolutePath
}