package com.example.fileexplorer.data.model

import java.io.File

data class FileProperties(
    var selectionMode: Int = FileConfigs.SINGLE_MODE,
    var selectionType: Int = FileConfigs.FILE_SELECT,
    var root: File = File(FileConfigs.defaultStorage()),
    var errorDir: File = File(FileConfigs.defaultStorage()),
    var offset: File = File(FileConfigs.defaultStorage()),
    var extensions: Array<String>? = null
)