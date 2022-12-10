package com.example.fileexplorer.view.file

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fileexplorer.R
import com.example.fileexplorer.data.model.FileItem
import com.example.fileexplorer.util.FileUtils
import java.text.SimpleDateFormat
import java.util.*

class FileItemAdapter(
    val context: Context,
    val func: (FileItem) -> Unit
) :
    RecyclerView.Adapter<FileItemAdapter.VideoHolder>() {

    var fileItems: ArrayList<FileItem> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_file_manager_list, parent, false)
        return VideoHolder(view)
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        holder.onBind(fileItems[position])
    }

    override fun getItemCount(): Int {
        return fileItems.size
    }

    fun updateData(fileListItems: List<FileItem>) {
        fileItems.clear()
        fileItems.addAll(fileListItems)
        notifyDataSetChanged()
    }

    inner class VideoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imvItemFileManagerFile: ImageView
        private val txvItemFileManagerTitle: TextView
        private val txtItemFileManagerDate: TextView
        private val txvItemFileManagerDetail: TextView

        init {
            itemView.setOnClickListener { view: View? ->
                val position = layoutPosition
                func.invoke(fileItems[position])
            }
            imvItemFileManagerFile = itemView.findViewById(R.id.iv_item_file)
            txvItemFileManagerTitle = itemView.findViewById(R.id.tv_file_title)
            txtItemFileManagerDate = itemView.findViewById(R.id.tv_file_date)
            txvItemFileManagerDetail = itemView.findViewById(R.id.tv_item_file_detail)
        }

        fun onBind(fileItem: FileItem) {
            if (fileItem.directory) {
                imvItemFileManagerFile.setImageResource(R.drawable.ic_folder_item)
            } else {
                when (FileUtils.fileType(fileItem)) {
                    6 -> Glide.with(context).load(fileItem.location)
                        .placeholder(R.drawable.ic_file_basic)
                        .into(imvItemFileManagerFile)
                    7 -> imvItemFileManagerFile.setImageResource(R.drawable.menu_pdf)
                    8 -> imvItemFileManagerFile.setImageResource(R.drawable.menu_ppt)
                    11 -> imvItemFileManagerFile.setImageResource(R.drawable.menu_word)
                    5 -> imvItemFileManagerFile.setImageResource(R.drawable.menu_excel)
                    else -> imvItemFileManagerFile.setImageResource(R.drawable.ic_file_basic)
                }
            }

            imvItemFileManagerFile.contentDescription = fileItem.filename
            txvItemFileManagerTitle.text = fileItem.filename
            val simpleDate = SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.getDefault())
            val date = Date(fileItem.time)
            fileItem.filename?.let {
                txtItemFileManagerDate.text = simpleDate.format(date)
                if (fileItem.directory) {
                    txvItemFileManagerDetail.text = context.getString(
                        R.string.x_items,
                        fileItem.quantity.toString()
                    )
                } else {
                    txvItemFileManagerDetail.text = FileUtils.convertFileSize(
                        fileItem.space
                    )
                }
            }
        }
    }
}