package com.hau.carepointtmdt.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hau.carepointtmdt.databinding.LayoutAddressItemBinding
import com.hau.carepointtmdt.model.Address
import com.hau.carepointtmdt.view.activity.CheckoutActivity

class AddressItemRV(private val mContext: Context, private val addressLst: List<Address>) :
    RecyclerView.Adapter<AddressItemRV.AddressItemRVViewHolder>() {
    inner class AddressItemRVViewHolder(val binding: LayoutAddressItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val rdSelectAddress = binding.rdSelectAddress
        val txtAddressName = binding.txtAddressName
        val txtDefaultAddress = binding.txtDefaultAddress
        val btnEditAddress = binding.btnEditAddress
        val txtAddressPhone = binding.txtAddressPhone
        val txtAddress = binding.txtAddress
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressItemRVViewHolder {
        val binding = LayoutAddressItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return AddressItemRVViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return addressLst.size
    }

    override fun onBindViewHolder(holder: AddressItemRVViewHolder, position: Int) {
        val address = addressLst[position]

        val isSelected = (mContext as CheckoutActivity).selectedAddress == address

        holder.rdSelectAddress.isChecked = isSelected
        holder.txtAddressName.text = address.user_name
        holder.txtAddressPhone.text = address.user_phone
        holder.txtAddress.text = address.address
        holder.txtDefaultAddress.visibility = if (address.is_default == 1) View.VISIBLE else View.GONE

        holder.rdSelectAddress.setOnClickListener {

            (mContext as CheckoutActivity).selectedAddress = address
            notifyDataSetChanged()
        }

        holder.btnEditAddress.setOnClickListener {
            (mContext as CheckoutActivity).openEditAddressModal(address)
        }
    }
}