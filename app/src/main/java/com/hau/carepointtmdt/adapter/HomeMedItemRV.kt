package com.hau.carepointtmdt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hau.carepointtmdt.model.HomeMedItem
import com.hau.carepointtmdt.databinding.LayoutMedicineItemBinding

class HomeMedItemRV(
    private val context: Context,
    private val homeMedItemLst: ArrayList<HomeMedItem>
) : RecyclerView.Adapter<HomeMedItemRV.HomeMedItemViewHolder>() {
    inner class HomeMedItemViewHolder(private val binding: LayoutMedicineItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgMedHome = binding.imgMedHome
        val txtMedName = binding.txtMedName
        val txtMedPrice = binding.txtMedPrice
        val txtMedUnit = binding.txtMedUnit
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMedItemViewHolder {
        val binding = LayoutMedicineItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return HomeMedItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return homeMedItemLst.size
    }

    override fun onBindViewHolder(holder: HomeMedItemViewHolder, position: Int) {
        val currentItem = homeMedItemLst[position]
        holder.imgMedHome.setImageResource(currentItem.imgMedID)
        holder.txtMedName.text = currentItem.txtMedName
        holder.txtMedPrice.text = currentItem.txtMedPrice
        holder.txtMedUnit.text = currentItem.txtMedUnit
    }
}