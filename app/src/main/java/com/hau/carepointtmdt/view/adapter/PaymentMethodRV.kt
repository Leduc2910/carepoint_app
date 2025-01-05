package com.hau.carepointtmdt.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hau.carepointtmdt.databinding.LayoutPaymentMethodBinding
import com.hau.carepointtmdt.model.PaymentMethod
import com.hau.carepointtmdt.view.activity.CheckoutActivity
import com.squareup.picasso.Picasso

class PaymentMethodRV(
    private val mContext: Context,
    private val paymentMethodLst: List<PaymentMethod>,
    private val onPaymentSelected: (PaymentMethod) -> Unit
) : RecyclerView.Adapter<PaymentMethodRV.PaymentMethodRVViewHolder>() {

    inner class PaymentMethodRVViewHolder(val binding: LayoutPaymentMethodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val rdSelectPayment = binding.rdSlPayment
        val imgPayment = binding.imgPayment
        val txtPaymentName = binding.txtPaymentName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentMethodRVViewHolder {
        val binding = LayoutPaymentMethodBinding.inflate(
            LayoutInflater.from(mContext), parent, false
        )
        return PaymentMethodRVViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return paymentMethodLst.size
    }

    override fun onBindViewHolder(holder: PaymentMethodRVViewHolder, position: Int) {
        val paymentMethod = paymentMethodLst[position]

        holder.txtPaymentName.text = paymentMethod.method_name
        Picasso.get().load(paymentMethod.method_img).into(holder.imgPayment)

        val isSelected = (mContext as CheckoutActivity).selectedPaymentMethod == paymentMethod
        holder.rdSelectPayment.isChecked = isSelected

        holder.rdSelectPayment.setOnClickListener {
            onPaymentSelected(paymentMethod)

            notifyDataSetChanged()
        }
    }
}
