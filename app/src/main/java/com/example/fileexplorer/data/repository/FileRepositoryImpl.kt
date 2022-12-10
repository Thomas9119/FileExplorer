package com.example.fileexplorer.data.repository

import com.example.fileexplorer.data.TypeSort
import com.example.fileexplorer.data.model.FileConfigs
import com.example.fileexplorer.data.model.FileItem
import com.example.fileexplorer.data.model.FileProperties
import com.example.fileexplorer.util.ExtFilter
import com.example.fileexplorer.util.FileUtils
import io.reactivex.Observable
import java.io.File

class FileRepositoryImpl : FileRepository {

    private var fileProperties: FileProperties = FileProperties()
    private var mExtensionFilter: ExtFilter? = null
    private var currLoc: File = File(FileConfigs.defaultStorage())

    override fun getAllFile(dir: String): List<FileItem> {
        return FileUtils.getFileListEntries(File(dir), mExtensionFilter)
    }

    override fun sortData(typeSort: TypeSort): List<FileItem> {
//        TODO("Not yet implemented")
        return ArrayList<FileItem>()
    }

    override fun searchData(query: String): Observable<ArrayList<File>> {
        return FileUtils.searchFile(File(FileConfigs.defaultStorage()), query)
    }
}