package com.hau.carepointtmdt.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hau.carepointtmdt.model.HomeFuncItem
import com.hau.carepointtmdt.databinding.LayoutItemFuncBinding
import com.hau.carepointtmdt.view.activity.MedicineHomeActivity

class HomeFunctionItemRecycleView(
    private val mContext: Context,
    private val homeFuncItemLst: List<HomeFuncItem>
) : RecyclerView.Adapter<HomeFunctionItemRecycleView.HomeFunctionItemViewHolder>() {
    inner class HomeFunctionItemViewHolder(private val binding: LayoutItemFuncBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var imgFuncItem: ImageView = binding.imgFuncItem
        var titleFuncItem: TextView = binding.titleFuncItem
        var desFuncItem: TextView = binding.desFuncItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFunctionItemViewHolder {
        val binding = LayoutItemFuncBinding.inflate(
            LayoutInflater.from(mContext),
            parent,
            false
        )
        return HomeFunctionItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return homeFuncItemLst.size
    }

    override fun onBindViewHolder(holder: HomeFunctionItemViewHolder, position: Int) {
        val currentItem: HomeFuncItem = homeFuncItemLst[position]
        holder.imgFuncItem.setImageResource(currentItem.img)
        holder.titleFuncItem.text = currentItem.title
        holder.desFuncItem.text = currentItem.des

        holder.itemView.setOnClickListener {
            if (position == 1) {
                val intent = Intent(mContext, MedicineHomeActivity::class.java)
                mContext.startActivity(intent)
            }
        }
    }
}