package com.example.fileexplorer.util

import com.example.fileexplorer.data.model.FileConfigs
import com.example.fileexplorer.data.model.FileProperties
import java.io.File
import java.io.FileFilter
import java.util.*

class ExtFilter(private val properties: FileProperties) : FileFilter {

    private val validExtensions: Array<String> = properties.extensions ?: arrayOf("")

    override fun accept(file: File?): Boolean {
        file?.let {
            if (it.isDirectory && it.canRead()) {
                return true
            } else if (properties.selectionType == FileConfigs.DIR_SELECT) {
                return false
            } else {
                val name = file.name.lowercase(Locale.getDefault())
                for (ext in validExtensions) {
                    if (name.endsWith(ext)) {
                        return true
                    }
                }
            }
        }

        return false
    }
}