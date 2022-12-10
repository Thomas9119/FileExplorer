package com.example.fileexplorer.view.file

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fileexplorer.R
import com.example.fileexplorer.base.BaseActivity
import com.example.fileexplorer.base.BaseDialog
import com.example.fileexplorer.data.TypeSort
import com.example.fileexplorer.data.model.FileConfigs
import com.example.fileexplorer.data.model.FileItem
import com.example.fileexplorer.databinding.ActivityFileBinding
import com.example.fileexplorer.view.dialog.SortFileDialog
import com.example.fileexplorer.view.search.SearchFragment
import java.io.File
import java.util.*

class FileActivity : BaseActivity() {
    private lateinit var binding: ActivityFileBinding

    private val homeViewModel: FileViewModel by viewModels()

    private var fileAdapter: FileItemAdapter? = null
    private var currLoc: String = FileConfigs.defaultStorage()
    private var listDirectory = Stack<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        updateUISort()
        setupObserver()
    }

    private fun setupObserver() {
        homeViewModel.files.observe(this) {
            if (it.isEmpty()) {
                binding.recycler.visibility = View.GONE
                binding.emptyView.visibility = View.VISIBLE
            } else {
                binding.recycler.visibility = View.VISIBLE
                binding.emptyView.visibility = View.GONE
                fileAdapter?.updateData(it)
            }
        }
    }


    private fun setupUI() {
        fileAdapter = FileItemAdapter(
            this
        ) {
            handleClickItem(it)
        }

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(this@FileActivity)
            adapter = fileAdapter
        }

        homeViewModel.getListData(currLoc)
    }

    private fun updateUISort() {
        binding.ivMenu.setOnClickListener {
            val dialog = SortFileDialog(this)
            dialog.onCallBack = object : BaseDialog.CallbackDismiss<String> {
                override fun onCallBack(data: String) {
                    when (data.uppercase()) {
                        TypeSort.TYPE_DATE.code -> homeViewModel.sortBy(TypeSort.TYPE_DATE)
                        TypeSort.TYPE_SIZE.code -> homeViewModel.sortBy(TypeSort.TYPE_SIZE)
                        TypeSort.TYPE_KIND.code -> homeViewModel.sortBy(TypeSort.TYPE_KIND)
                        else -> homeViewModel.sortBy(TypeSort.TYPE_NAME)
                    }
                }
            }
            dialog.show()
        }

        binding.ivSearch.setOnClickListener {
            val fragment = SearchFragment()
            replaceFragment(R.id.frSearch, fragment, true)
        }
    }


    fun handleClickItem(fileItem: FileItem) {
        if (fileItem.directory) {
            fileItem.location?.let {
                currLoc = it
                listDirectory.push(currLoc)
                if (File(it).canRead()) {
                    homeViewModel.getListData(it)
                } else {
                    Toast.makeText(this, R.string.error_dir_access, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } else {
            try {
                fileItem.location?.let {
                    Toast.makeText(this, "Open file $it", Toast.LENGTH_SHORT).show()
                }
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Cant Find Your File", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onBackPressed() {
        when {
            supportFragmentManager.backStackEntryCount > 0 -> supportFragmentManager.popBackStack()
            !listDirectory.empty() -> {
                val dir = listDirectory.pop()
                val file = File(dir).parentFile?.absolutePath ?: ""
                homeViewModel.getListData(file)
            }
            else -> super.onBackPressed()
        }
    }
}
