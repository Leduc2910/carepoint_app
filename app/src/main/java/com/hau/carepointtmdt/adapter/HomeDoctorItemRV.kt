package com.hau.carepointtmdt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hau.carepointtmdt.model.HomeDoctorItem
import com.hau.carepointtmdt.databinding.LayoutDoctorFrameBinding

class HomeDoctorItemRV (private val mContext: Context, private val homeDoctorItemLst: List<HomeDoctorItem>): RecyclerView.Adapter<HomeDoctorItemRV.HomeDoctorItemViewHolder>() {

    inner class HomeDoctorItemViewHolder(private val binding : LayoutDoctorFrameBinding) : RecyclerView.ViewHolder(binding.root)  {
        val imgDoctorItem = binding.imgDoctorItem
        val txtSpeDoc = binding.txtSpeDoc
        val txtDoctorName = binding.txtDoctorName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeDoctorItemViewHolder {
        val binding = LayoutDoctorFrameBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return HomeDoctorItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return homeDoctorItemLst.size
    }

    override fun onBindViewHolder(holder: HomeDoctorItemViewHolder, position: Int) {
        val currentItem = homeDoctorItemLst[position]
        holder.imgDoctorItem.setImageResource(currentItem.imgDoctorItem)
        holder.txtSpeDoc.text = currentItem.txtSpeDoc
        holder.txtDoctorName.text = currentItem.txtDoctorName
    }
}