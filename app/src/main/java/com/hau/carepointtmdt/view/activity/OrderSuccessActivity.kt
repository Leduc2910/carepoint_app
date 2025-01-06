package com.hau.carepointtmdt.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.hau.carepointtmdt.databinding.ActivityOrderSuccessBinding

class OrderSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderSuccessBinding

    private lateinit var jsonOrderDetail: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOrderSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        jsonOrderDetail = intent.getStringExtra("orderDetail").toString()

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