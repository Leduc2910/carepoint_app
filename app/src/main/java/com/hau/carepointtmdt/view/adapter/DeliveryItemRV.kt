package com.hau.carepointtmdt.view.adapter

import android.content.Context
import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hau.carepointtmdt.databinding.LayoutDeliveryItemBinding
import com.hau.carepointtmdt.model.Delivery
import com.hau.carepointtmdt.view.activity.CheckoutActivity

class DeliveryItemRV(
    private val mContext: Context,
    private val deliveryLst: List<Delivery>,
    private val onDeliverySelected: (Delivery) -> Unit
) : RecyclerView.Adapter<DeliveryItemRV.DeliveryItemRVViewHolder>() {

    inner class DeliveryItemRVViewHolder(val binding: LayoutDeliveryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val rdSelectDelivery = binding.rdSelectDelivery
        val txtDeliveryName = binding.txtDeliveryName
        val txtDeliveryDescribe = binding.txtDeliveryDescribe
        val txtDeliveryPrice = binding.txtDeliveryPrice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryItemRVViewHolder {
        val binding =
            LayoutDeliveryItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return DeliveryItemRVViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return deliveryLst.size
    }

    override fun onBindViewHolder(holder: DeliveryItemRVViewHolder, position: Int) {
        val delivery = deliveryLst[position]

        holder.txtDeliveryName.text = delivery.delivery_name
        holder.txtDeliveryDescribe.text = delivery.delivery_describe
        holder.txtDeliveryPrice.text = DecimalFormat("#,###").format(delivery.delivery_price)

        val isSelected = (mContext as CheckoutActivity).selectedDelivery == delivery
        holder.rdSelectDelivery.isChecked = isSelected

        holder.rdSelectDelivery.setOnClickListener {
            onDeliverySelected(delivery)
            // (mContext as CheckoutActivity).selectedDelivery = delivery

            notifyDataSetChanged()
        }
    }
}
