package com.hau.carepointtmdt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hau.carepointtmdt.activity.DetailMedicineActivity
import com.hau.carepointtmdt.databinding.LayoutMedicineItem2Binding
import com.hau.carepointtmdt.model.Medicine
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class MedItem2RV(
    private val context: Context,
    private val medItemLst: List<Medicine>
) : RecyclerView.Adapter<MedItem2RV.HomeMedItemViewHolder>() {
    inner class HomeMedItemViewHolder(private val binding: LayoutMedicineItem2Binding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgMedHome = binding.imgMedHome2
        val txtMedName = binding.txtMedName2
        val txtMedPrice = binding.txtMedPrice2
        val txtMedUnit = binding.txtMedUnit2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMedItemViewHolder {
        val binding = LayoutMedicineItem2Binding.inflate(LayoutInflater.from(context), parent, false)
        return HomeMedItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return medItemLst.size
    }

    override fun onBindViewHolder(holder: HomeMedItemViewHolder, position: Int) {
        val currentItem = medItemLst[position]
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