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
    private var orderItemLst: MutableList<Order_Item>,
    private val medicineLst: List<Medicine>,
    private val cartViewModel: CartViewModel
) : RecyclerView.Adapter<CartItemRV.CartItemViewHolder>() {

    inner class CartItemViewHolder(binding: LayoutCartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgCartMed = binding.imgCartMed
        val txtCartMedName = binding.txtCartMedName
        val txtCartMedQuantity = binding.txtCartQuantity
        val txtCartMedTotal = binding.txtCartPrice
        val cbSelectBuy = binding.cbSelectBuy
        val btnMinusQuantity = binding.btnMinusQuantity
        val btnPlusQuantity = binding.btnPlusQuantity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val binding = LayoutCartItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CartItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return orderItemLst.size
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val orderItem = orderItemLst[position]

        val medicine = medicineLst.firstOrNull { it.medicine_id == orderItem.medicine_id }

        medicine?.let {
            holder.txtCartMedName.text = it.medicine_name
            holder.txtCartMedQuantity.text = orderItem.quantity.toString()
            holder.txtCartMedTotal.text = DecimalFormat("#,###").format(orderItem.totalPrice) + " đ"
            Picasso.get().load(it.medicine_img).into(holder.imgCartMed)
        }

        holder.cbSelectBuy.isChecked = orderItem.isSelected == 2

        holder.cbSelectBuy.setOnCheckedChangeListener { _, isChecked ->
            orderItem.isSelected = if (isChecked) 2 else 1
            orderItemLst[position] = orderItem
            cartViewModel.updateOrderItem(orderItem)
        }

        holder.btnPlusQuantity.setOnClickListener {
            medicine?.let { med ->
                orderItem.quantity++
                orderItem.totalPrice = med.medicine_price * orderItem.quantity
                notifyItemChanged(position)
                cartViewModel.updateOrderItem(orderItem)
            }
        }


        holder.btnMinusQuantity.setOnClickListener {
            medicine?.let { med ->
                if (orderItem.quantity > 1) {
                    orderItem.quantity--
                    orderItem.totalPrice = med.medicine_price * orderItem.quantity
                    notifyItemChanged(position)
                    cartViewModel.updateOrderItem(orderItem)
                }
            }
        }
    }
    fun updateData(newOrderItemList: List<Order_Item>) {
        this.orderItemLst = newOrderItemList.toMutableList()
        notifyDataSetChanged()
    }
}
