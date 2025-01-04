package com.hau.carepointtmdt.view.adapter


import android.content.Context
import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hau.carepointtmdt.databinding.LayoutMedItemBinding
import com.hau.carepointtmdt.model.Medicine
import com.hau.carepointtmdt.model.Order_Item
import com.squareup.picasso.Picasso

class MedItem3RV(
    private val context: Context,
    private val orderItemLst: List<Order_Item>,
    private val medicineLst: List<Medicine>
) : RecyclerView.Adapter<MedItem3RV.MedItem3RVViewHolder>() {

    inner class MedItem3RVViewHolder(val binding: LayoutMedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgMedCheck = binding.imgMedCheck
        val txtMedNameCheck = binding.txtMedNameCheck
        val txtMedQuantityUnit = binding.txtMedQuantityUnit
        val txtMedPriceCheck = binding.txtMedPriceCheck
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedItem3RVViewHolder {
        val binding = LayoutMedItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return MedItem3RVViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedItem3RVViewHolder, position: Int) {
        val order_item = orderItemLst[position]

        val medicine = medicineLst.find { it.medicine_id == order_item.medicine_id }

        Picasso.get().load(medicine?.medicine_img).into(holder.imgMedCheck)
        holder.txtMedNameCheck.text = medicine?.medicine_name
        holder.txtMedQuantityUnit.text = "x" + order_item.quantity + " " + (medicine?.medicine_unit)
        val price = medicine?.medicine_price!! * order_item.quantity
        holder.txtMedPriceCheck.text =
            DecimalFormat("#,###").format(price) + " đ"
    }

    override fun getItemCount(): Int {
        return orderItemLst.size
    }
}
