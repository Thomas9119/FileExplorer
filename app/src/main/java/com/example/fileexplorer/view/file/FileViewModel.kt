package com.example.fileexplorer.view.file

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fileexplorer.data.TypeSort
import com.example.fileexplorer.data.model.FileConfigs
import com.example.fileexplorer.data.model.FileItem
import com.example.fileexplorer.data.repository.FileRepository
import com.example.fileexplorer.data.repository.FileRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class FileViewModel : ViewModel() {

    private val fileRepository: FileRepository by lazy { FileRepositoryImpl() }
    private val _files = MutableLiveData<List<FileItem>>()
    val files: LiveData<List<FileItem>> = _files
    private val disposables = CompositeDisposable()

    private var mSort: TypeSort = TypeSort.TYPE_NAME
    private var currLoc: String = FileConfigs.defaultStorage()

    fun sortBy(sort: TypeSort) {
        mSort = sort
        getListData(currLoc)
    }

    fun getListData(currLoc: String) {
        this.currLoc = currLoc
        val data = fileRepository.getAllFile(currLoc)

        val output = when (mSort) {
            TypeSort.TYPE_KIND -> data.sortedBy { it.getExtension() }
            TypeSort.TYPE_DATE -> data.sortedBy { it.time }
            TypeSort.TYPE_SIZE -> data.sortedBy { it.space }
            else -> data.sortedBy { it.filename }
        }
        _files.value = output
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
