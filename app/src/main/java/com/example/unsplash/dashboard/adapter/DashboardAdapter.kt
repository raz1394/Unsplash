package com.example.unsplash.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplash.R
import com.example.unsplash.dashboard.datamodel.UnsplashDataItemsModel
import com.example.unsplash.databinding.UnsplashRowListBinding
import com.example.unsplash.utils.ItemListener


class DashboardAdapter(
    private val context: Context,
    private val imageList: ArrayList<UnsplashDataItemsModel>,
    private val itemListener: ItemListener
) : RecyclerView.Adapter<DashboardAdapter.RecyclerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            UnsplashRowListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.setDataToViews(position)
    }

    inner class RecyclerViewHolder(private val binding: UnsplashRowListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setDataToViews(position: Int) {
            binding.idAuthorTv.text = "Image ID: ${imageList[position].id}"
            binding.imageAuthorTv.text = "Author Name: ${imageList[position].author}"
            Glide.with(context)
                .load(imageList[position].downloadUrl)
                .disallowHardwareConfig()
                .placeholder(R.drawable.splash_icon)
                .into(binding.imageView)

            binding.downloadImage.setOnClickListener {
                itemListener.onItemClick(
                    position,
                    imageList,
                    "download"
                )
            }

            binding.linearLayout.setOnClickListener {
                itemListener.onItemClick(
                    position,
                    imageList,
                    "view"
                )
            }
        }
    }
}