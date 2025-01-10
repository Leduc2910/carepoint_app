package com.hau.carepointtmdt.view.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.hau.carepointtmdt.databinding.LayoutOrderBinding
import com.hau.carepointtmdt.model.Medicine
import com.hau.carepointtmdt.model.Order_Detail
import com.hau.carepointtmdt.model.Order_Item
import com.hau.carepointtmdt.view.activity.ActivityPurchaseOrder
import com.hau.carepointtmdt.view.activity.OrderDetailActivity
import com.squareup.picasso.Picasso

class PurchaseOrderRV(
    private val mContext: Context,
    private val medicineLst: List<Medicine>,
    private val orderItemLst: List<Order_Item>,
    private var orderDetailLst: List<Order_Detail>
) : RecyclerView.Adapter<PurchaseOrderRV.PurchaseOrderViewHolder>() {

    val gson = Gson()

    inner class PurchaseOrderViewHolder(val binding: LayoutOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val txtOrderId = binding.txtOrderId
        val txtProductName = binding.txtProductName
        val txtProductPrice = binding.txtProductPrice
        val txtProductUnit = binding.txtProductUnit
        val txtTotalProduct = binding.txtProductQuantity
        val txtTotalPrice = binding.txtOrderPrice
        val txtOrderQuantity = binding.txtOrderQuantity
        val imgOrderProduct = binding.imgOrderProduct
        val txtOrderStatus = binding.txtOrderStatus
        val goToOrderDetail = binding.GoToOrderDetail
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseOrderViewHolder {
        val binding = LayoutOrderBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return PurchaseOrderViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return orderDetailLst.size
    }

    override fun onBindViewHolder(holder: PurchaseOrderViewHolder, position: Int) {
        val orderDetail = orderDetailLst[position]

        val orderItems = orderItemLst.filter { it.order_id == orderDetail.order_id }

        holder.txtOrderId.text = "#${orderDetail.orderDetail_id}"
        holder.txtTotalPrice.text = DecimalFormat("#,###").format(orderDetail.totalPrice) + " đ"
        holder.txtOrderQuantity.text = "${orderItems.sumOf { it.quantity }} sản phẩm"

        val firstOrderItem = orderItems[0]
        val medicine = medicineLst.find { it.medicine_id == firstOrderItem.medicine_id }

        holder.txtProductName.text = medicine!!.medicine_name
        holder.txtProductPrice.text = DecimalFormat("#,###").format(firstOrderItem.totalPrice) + " đ"
        holder.txtTotalProduct.text = "x${firstOrderItem.quantity}"
        holder.txtProductUnit.text = "${medicine.medicine_unit}"
        Picasso.get().load(medicine.medicine_img).into(holder.imgOrderProduct)
        holder.txtOrderStatus.text = when (orderDetail.status) {
            1 -> "Chờ xác nhận"
            2 -> "Chờ thanh toán"
            3 -> "Chờ lấy hàng"
            4 -> "Đang giao hàng"
            5 -> "Đã giao"
            else -> ""
        }
        holder.goToOrderDetail.setOnClickListener {
            val jSonOrderDetail = gson.toJson(orderDetail)
            val intent = Intent(mContext, OrderDetailActivity::class.java)
            intent.putExtra("orderDetail", jSonOrderDetail)
            mContext.startActivity(intent)
            if (mContext is ActivityPurchaseOrder) {
                (mContext as ActivityPurchaseOrder).finish()
            }
        }
    }

    fun updateOrderList(newOrderDetailLst: List<Order_Detail>?) {
        this.orderDetailLst = newOrderDetailLst ?: emptyList()
        notifyDataSetChanged()
    }
}