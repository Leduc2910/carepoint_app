package com.hau.carepointtmdt.view.adapter

import android.content.Context
import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hau.carepointtmdt.databinding.LayoutCartItemBinding
import com.hau.carepointtmdt.model.Medicine
import com.hau.carepointtmdt.model.Order_Item
import com.hau.carepointtmdt.viewmodel.CartViewModel
import com.squareup.picasso.Picasso

class CartItemRV(
    private val mContext: Context,
    private val orderItemLst: List<Order_Item>,
    private val medicineLst: List<Medicine>, val cartViewModel: CartViewModel
) : RecyclerView.Adapter<CartItemRV.CartItemViewHolder>() {


    inner class CartItemViewHolder(binding: LayoutCartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgCartMed = binding.imgCartMed
        val txtCartMedName = binding.txtCartMedName
        val txtCartMedQuantity = binding.txtCartQuantity
        val txtCartMedTotal = binding.txtCartPrice
        val cbSelectBuy = binding.cbSelectBuy
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val binding =
            LayoutCartItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CartItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return orderItemLst.size
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val orderItem = orderItemLst[position]
        holder.cbSelectBuy.isChecked = orderItem.isSelected == 2

        for (medicine: Medicine in medicineLst) {
            if (orderItem.medicine_id == medicine.medicine_id) {
                holder.txtCartMedName.text = medicine.medicine_name
                holder.txtCartMedQuantity.text = orderItem.quantity.toString()
                holder.txtCartMedTotal.text =
                    DecimalFormat("#,###").format(orderItem.totalPrice) + " đ"
                Picasso.get().load(medicine.medicine_img).into(holder.imgCartMed)
                break
            }
        }

        holder.cbSelectBuy.setOnClickListener {
            if (holder.cbSelectBuy.isChecked) {
                orderItem.isSelected = 2
                cartViewModel.selectOrderItem(orderItem.orderItem_id, 2)
            } else {
                orderItem.isSelected = 1
                cartViewModel.selectOrderItem(orderItem.orderItem_id, 1)
            }
        }
    }
}

