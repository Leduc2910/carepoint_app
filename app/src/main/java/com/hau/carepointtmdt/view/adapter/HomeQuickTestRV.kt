package com.hau.carepointtmdt.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hau.carepointtmdt.model.HomeQuickTestItem
import com.hau.carepointtmdt.databinding.LayoutQuicktestBinding

class HomeQuickTestRV (private val mContext : Context, private val homeQuickTestLst : List<HomeQuickTestItem>) : RecyclerView.Adapter<HomeQuickTestRV.HomeQuickTestViewHolder>() {

    inner class HomeQuickTestViewHolder(private val binding : LayoutQuicktestBinding) : RecyclerView.ViewHolder(binding.root) {
        var imgTestHome = binding.imgTestHome
        var txtTestHome = binding.txtTestHome
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeQuickTestViewHolder {
        val binding = LayoutQuicktestBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return HomeQuickTestViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return homeQuickTestLst.size
    }

    override fun onBindViewHolder(holder: HomeQuickTestViewHolder, position: Int) {
        val currentItem : HomeQuickTestItem = homeQuickTestLst[position]
        holder.imgTestHome.setImageResource(currentItem.image)
        holder.txtTestHome.text = currentItem.title

    }
}