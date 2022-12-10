package com.example.fileexplorer.data.repository

import com.example.fileexplorer.data.TypeSort
import com.example.fileexplorer.data.model.FileItem
import io.reactivex.Observable
import java.io.File

interface FileRepository {

    fun getAllFile(dir : String) : List<FileItem>

    fun sortData(typeSort: TypeSort) : List<FileItem>

    fun searchData(query : String) : Observable<ArrayList<File>>

}