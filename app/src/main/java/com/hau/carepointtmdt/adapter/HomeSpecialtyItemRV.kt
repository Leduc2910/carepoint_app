package com.hau.carepointtmdt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hau.carepointtmdt.model.HomeSpecialtyItem
import com.hau.carepointtmdt.databinding.LayoutItemSpecialtyBinding

class HomeSpecialtyItemRV(
    private val mContext: Context,
    private val mSpecialtyItemLst: List<HomeSpecialtyItem>
) :
    RecyclerView.Adapter<HomeSpecialtyItemRV.HomeSpecialtyItemViewHolder>() {

    inner class HomeSpecialtyItemViewHolder(private val binding: LayoutItemSpecialtyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var imgSpeItem = binding.imgSpeItem
        var desSpeItem = binding.desSpeItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSpecialtyItemViewHolder {
        val binding =
            LayoutItemSpecialtyBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return HomeSpecialtyItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mSpecialtyItemLst.size
    }

    override fun onBindViewHolder(holder: HomeSpecialtyItemViewHolder, position: Int) {
        val currentItem: HomeSpecialtyItem = mSpecialtyItemLst[position]
        holder.imgSpeItem.setImageResource(currentItem.img)
        holder.desSpeItem.text = currentItem.des
    }

}