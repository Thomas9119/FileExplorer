package com.example.fileexplorer.data.model

import java.util.*

class FileItem : Comparable<FileItem> {
    var filename: String? = null
    var location: String? = null
    var directory = false
    var marked: Boolean = false
    var quantity: Int = 0
    var space: Long = 0
    var time: Long = 0

    fun getExtension(): String {
        return filename?.let { it.substring(it.lastIndexOf(".") + 1) } ?: ""
    }

    override fun compareTo(other: FileItem): Int {
        return if (other.directory && directory) {
            if (filename == null || other.filename == null) {
                return -1
            }
            filename!!.lowercase(Locale.getDefault()).compareTo(
                other.filename!!.lowercase(
                    Locale.getDefault()
                )
            )
        } else if (!other.directory && !directory) {
            if (filename == null || other.filename == null) {
                return -1
            }
            filename!!.lowercase(Locale.getDefault()).compareTo(
                other.filename!!.lowercase(
                    Locale.getDefault()
                )
            )
        } else if (other.directory && !directory) {
            1
        } else {
            -1
        }
    }


}