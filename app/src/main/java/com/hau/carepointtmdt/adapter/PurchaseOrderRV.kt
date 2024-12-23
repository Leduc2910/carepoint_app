package com.hau.carepointtmdt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hau.carepointtmdt.model.Order
import com.hau.carepointtmdt.databinding.LayoutOrderBinding

class PurchaseOrderRV (private val mContext: Context, private val purchaseOrderLst: ArrayList<Order>) : RecyclerView.Adapter<PurchaseOrderRV.PurchaseOrderViewHolder>() {

    inner class PurchaseOrderViewHolder(val binding: LayoutOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        val txtOrderId = binding.txtOrderId
        val txtProductName = binding.txtProductName
        val txtProductPrice = binding.txtProductPrice
        val txtProductQuantity = binding.txtProductQuantity
        val txtProductUnit = binding.txtProductUnit
        val txtTotalProduct = binding.txtProductQuantity
        val txtTotalPrice = binding.txtOrderPrice
        val txtOrderQuantity = binding.txtOrderQuantity
        val imgOrderProduct = binding.imgOrderProduct
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseOrderViewHolder {
        val binding = LayoutOrderBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return PurchaseOrderViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return purchaseOrderLst.size
    }

    override fun onBindViewHolder(holder: PurchaseOrderViewHolder, position: Int) {
        val currentItem: Order = purchaseOrderLst[position]
        holder.txtOrderId.text = currentItem.orderId
        holder.txtProductName.text = currentItem.orderProductName
        holder.txtProductPrice.text = currentItem.orderProductPrice
        holder.txtProductQuantity.text = currentItem.orderProductQuantity
        holder.txtProductUnit.text = currentItem.orderProductUnit
        holder.txtTotalProduct.text = currentItem.orderTotalProduct.toString()
        holder.txtTotalPrice.text = currentItem.orderTotalPrice.toString()
        holder.txtOrderQuantity.text = currentItem.orderQuantity.toString()
        holder.imgOrderProduct.setImageResource(currentItem.orderProductImg)
    }
}