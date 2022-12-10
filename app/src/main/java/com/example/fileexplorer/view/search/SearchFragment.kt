package com.example.fileexplorer.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fileexplorer.data.model.FileItem
import com.example.fileexplorer.databinding.FragmentSearchBinding
import com.example.fileexplorer.util.extension.hideKeyboard
import com.example.fileexplorer.view.file.FileActivity
import com.example.fileexplorer.view.file.FileItemAdapter

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var fileAdapter: FileItemAdapter? = null

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }

        fileAdapter = FileItemAdapter(
            requireContext()
        ) {
            handleClickItem(it)
        }

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = fileAdapter
        }

        binding.edtSearch.addTextChangedListener(afterTextChanged = { editable ->
            editable?.toString()?.let {
                searchViewModel.searchFile(it)
            }
        })
    }


    private fun setupObserver() {
        searchViewModel.files.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.recycler.visibility = View.GONE
            } else {
                binding.recycler.visibility = View.VISIBLE
                fileAdapter?.updateData(it)
            }
        }
    }

    private fun handleClickItem(fileItem: FileItem) {
        if (activity is FileActivity) {
            (activity as FileActivity).handleClickItem(fileItem)
            (activity as FileActivity).onBackPressed()
        }
    }

    override fun onDestroyView() {
        activity?.hideKeyboard()
        super.onDestroyView()
        _binding = null
    }
}
