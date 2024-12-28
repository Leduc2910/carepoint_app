package com.hau.carepointtmdt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hau.carepointtmdt.activity.DetailMedicineActivity
import com.hau.carepointtmdt.model.Medicine
import com.hau.carepointtmdt.databinding.LayoutMedicineItemBinding
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class MedItemRV(
    private val context: Context,
    private val homeMedItemLst: List<Medicine>,
) : RecyclerView.Adapter<MedItemRV.HomeMedItemViewHolder>() {
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
        Picasso.get().load(currentItem.medicine_img).into(holder.imgMedHome)
        holder.txtMedName.text = currentItem.medicine_name
        val formattedPrice = DecimalFormat("#,###").format(currentItem.medicine_price)
        holder.txtMedPrice.text = "$formattedPrice đ"
        holder.txtMedUnit.text = currentItem.medicine_unit

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailMedicineActivity::class.java)
            intent.putExtra("medicine_id", currentItem.medicine_id)
            context.startActivity(intent)
        }
    }
}