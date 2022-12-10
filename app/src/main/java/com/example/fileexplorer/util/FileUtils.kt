package com.example.fileexplorer.util

import com.example.fileexplorer.data.model.FileItem
import io.reactivex.Observable
import java.io.File
import java.util.*

object FileUtils {
    private val mSearchFiles = ArrayList<File>()

    fun getFileListEntries(
        inter: File,
        filter: ExtFilter?
    ): ArrayList<FileItem> {
        val internalList: ArrayList<FileItem> = ArrayList()
        try {
            for (name in inter.listFiles(filter)!!) {
//                if (name.canRead()) {
                if (!name.name.startsWith(".")) {
                    val item = FileItem()
                    item.filename = name.name
                    item.directory = name.isDirectory
                    item.location = name.absolutePath
                    item.time = name.lastModified()
                    item.space = name.length()
                    if (name.isDirectory) {
                        item.quantity =
                            name.listFiles(filter)?.filter { !it.name.startsWith(".") }?.size ?: 0
                    }
                    internalList.add(item)
                }
            }
            internalList.sort()
//            Collections.sort(internalList.toMutableList())
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
        return internalList
    }

    fun searchFile(dir: File, text: String): Observable<ArrayList<File>> {
        return Observable.create { emitter ->
            mSearchFiles.clear()
            emitter.onNext(search(dir, text))
            emitter.onComplete()
        }
    }

    private fun search(dir: File, text: String): ArrayList<File> {
        val listFile = dir.listFiles()
        if (listFile != null && listFile.isNotEmpty()) {
            for (i in listFile.indices) {
                if (listFile[i].isDirectory) {
                    search(listFile[i], text.lowercase(Locale.getDefault()))
                } else {
                    if (listFile[i].name.lowercase(Locale.getDefault())
                            .contains(text.lowercase(Locale.getDefault()))
                    ) {
                        mSearchFiles.add(listFile[i])
                    }
                }
            }
        }
        return mSearchFiles
    }

    fun fileType(file: FileItem): Int {
        return if (file.directory) {
            1
        } else {
            file.filename?.let {
                val type: Int = when (it.substring(it.lastIndexOf(".") + 1)) {
                    "mp3" -> 2
                    "wav" -> 2
                    "flac" -> 2
                    "ape" -> 2
                    "java" -> 4
                    "html" -> 4
                    "js" -> 4
                    "css" -> 4
                    "json" -> 4
                    "xml" -> 4
                    "xlsx" -> 5
                    "xls" -> 5
                    "png" -> 6
                    "jpg" -> 6
                    "gif" -> 6
                    "bmp" -> 6
                    "pdf" -> 7
                    "ppt" -> 8
                    "pptx" -> 8
                    "txt" -> 9
                    "mp4" -> 10
                    "flv" -> 10
                    "avi" -> 10
                    "3gp" -> 10
                    "mkv" -> 10
                    "rmvb" -> 10
                    "wmv" -> 10
                    "doc" -> 11
                    "docx" -> 11
                    "rar" -> 12
                    "zip" -> 12
                    else -> 3
                }
                type
            } ?: 3
        }
    }

    fun convertFileSize(size: Long): String {
        val kb: Long = 1024
        val mb = kb * 1024
        val gb = mb * 1024
        return if (size >= gb) {
            String.format("%.2f GB", size.toFloat() / gb)
        } else if (size >= mb) {
            val f = size.toFloat() / mb
            String.format(if (f > 100) "%.0f MB" else "%.2f MB", f)
        } else if (size >= kb) {
            val f = size.toFloat() / kb
            String.format(if (f > 100) "%.0f KB" else "%.1f KB", f)
        } else String.format("%d B", size)
    }
}