package com.example.projectthuctap2.PagerAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hau.carepointtmdt.model.OnboardItem
import com.hau.carepointtmdt.databinding.ActivityOnboardAdapterBinding

class OnboardViewPagerAdapter(private val mContext: Context, onboardItemList: List<OnboardItem>) :
    RecyclerView.Adapter<OnboardViewPagerAdapter.ViewHolder>() {
    class ViewHolder(binding : ActivityOnboardAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        var imgSlide: ImageView = binding.onboardImg
        var title: TextView = binding.onboardTitle
        var describe: TextView = binding.onboardDescribe
    }

    private val onboardItemList: List<OnboardItem> = onboardItemList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding = ActivityOnboardAdapterBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem: OnboardItem = onboardItemList[position]
        holder.title.text = currentItem.title
        holder.describe.text = currentItem.description
        holder.imgSlide.setImageResource(currentItem.onboardImg)
    }

    override fun getItemCount(): Int {
        return onboardItemList.size
    }


}
