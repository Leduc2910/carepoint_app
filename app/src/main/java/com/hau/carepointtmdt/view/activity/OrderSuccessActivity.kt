package com.hau.carepointtmdt.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hau.carepointtmdt.R
import com.hau.carepointtmdt.databinding.ActivityOrderSuccessBinding
import com.hau.carepointtmdt.model.Order_Detail

class OrderSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderSuccessBinding

    private lateinit var jsonOrderDetail: String
    private lateinit var orderDetail: Order_Detail
    val gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOrderSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jsonOrderDetail = intent.getStringExtra("orderDetail").toString()
        val type = object : TypeToken<Order_Detail>() {}.type
        orderDetail = gson.fromJson(jsonOrderDetail, type)

        if (orderDetail.status == 2) {
            binding.txtOrderStatusTitle.text = "Đang chờ thanh toán"
            binding.imgOrderStatus.setImageResource(R.drawable.img_pending)
        }
        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnDetialOrder.setOnClickListener {
            val intent = Intent(this, OrderDetailActivity::class.java)
            intent.putExtra("orderDetail", jsonOrderDetail)
            startActivity(intent)
            finish()
        }
    }
}