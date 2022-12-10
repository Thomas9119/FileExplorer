package com.example.fileexplorer.view.search

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

class SearchViewModel : ViewModel() {

    private val fileRepository: FileRepository by lazy { FileRepositoryImpl() }

    private val _files = MutableLiveData<List<FileItem>>()
    val files: LiveData<List<FileItem>> = _files
    private val disposables = CompositeDisposable()


    fun searchFile(text: String) {
        if (text.isEmpty()) {
            _files.value = ArrayList()
            return
        }
        val d = fileRepository.searchData(text)
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val data = it.map { file ->
                    FileItem().apply {
                        filename = file.name
                        directory = file.isDirectory
                        location = file.absolutePath
                        time = file.lastModified()
                        space = file.length()
                        if (file.isDirectory) {
                            quantity = file.listFiles()?.size ?: 0
                        }
                    }
                }
                _files.value = data
            }
        disposables.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}