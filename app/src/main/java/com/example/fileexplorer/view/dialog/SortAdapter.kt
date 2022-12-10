package com.example.fileexplorer.view.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fileexplorer.databinding.ItemSortBinding

class SortAdapter(private val types: List<String>, val func: (String) -> Unit) :
    RecyclerView.Adapter<SortAdapter.VideoHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): VideoHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemSortBinding.inflate(inflater, viewGroup, false)
        return VideoHolder(binding)
    }

    override fun onBindViewHolder(videoHolder: VideoHolder, i: Int) {
        videoHolder.onBind(types[i])
    }


    override fun getItemCount(): Int {
        return types.size
    }

    inner class VideoHolder(private val binding: ItemSortBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                func.invoke(types[adapterPosition])
            }
        }

        fun onBind(type: String) {
            binding.tvType.text = type
        }
    }
}