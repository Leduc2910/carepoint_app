package com.hau.carepointtmdt.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hau.carepointtmdt.databinding.LayoutRecentMedBinding
import com.hau.carepointtmdt.model.RecentMedSearch

class RecentMedSearchRV(
    private val mContext: Context,
    private val recentMedSearchLst: ArrayList<RecentMedSearch>
) : RecyclerView.Adapter<RecentMedSearchRV.RecentMedSearchViewHolder>() {
    inner class RecentMedSearchViewHolder(private val binding : LayoutRecentMedBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgRecentMed = binding.imgRecentMed
        val txtRecentMedName = binding.txtRecentMedName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentMedSearchViewHolder {
        val binding = LayoutRecentMedBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return RecentMedSearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recentMedSearchLst.size
    }

    override fun onBindViewHolder(holder: RecentMedSearchViewHolder, position: Int) {
        val currentItem: RecentMedSearch = recentMedSearchLst[position]
        holder.imgRecentMed.setImageResource(currentItem.recent_img)
        holder.txtRecentMedName.text = currentItem.recent_name
    }
}