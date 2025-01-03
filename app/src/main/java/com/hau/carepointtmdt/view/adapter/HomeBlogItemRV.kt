package com.hau.carepointtmdt.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hau.carepointtmdt.model.HomeBlogItem
import com.hau.carepointtmdt.databinding.LayoutBlogItemBinding

class HomeBlogItemRV (private val mContext: Context, private val homeBlogItemLst: ArrayList<HomeBlogItem>) : RecyclerView.Adapter<HomeBlogItemRV.HomeBlogItemViewHolder>() {
    inner class HomeBlogItemViewHolder(binding: LayoutBlogItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgBlogHome = binding.imgBlogHome
        val txtBlogCate = binding.txtBlogCate
        val imgBlogTitle = binding.imgBlogTitle
        val imgBlogDes = binding.imgBlogDes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBlogItemViewHolder {
        val binding = LayoutBlogItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeBlogItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return homeBlogItemLst.size
    }

    override fun onBindViewHolder(holder: HomeBlogItemViewHolder, position: Int) {
        val currentItem = homeBlogItemLst[position]
        holder.imgBlogHome.setImageResource(currentItem.imgID)
        holder.txtBlogCate.text = currentItem.cate
        holder.imgBlogTitle.text = currentItem.title
        holder.imgBlogDes.text = currentItem.describe
    }
}