package com.hau.carepointtmdt.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hau.carepointtmdt.databinding.ActivityDetailMedicineBinding
import com.hau.carepointtmdt.viewmodel.DetailMedicineViewModel

class DetailMedicineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMedicineBinding

    private val detailMedViewModel : DetailMedicineViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailMedicineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val medicine_id = intent.getIntExtra("medicine_id", -1)
        Log.d("medicine_id", medicine_id.toString())

        binding.btnBackToPrevious.setOnClickListener {
            finish()
        }

        detailMedViewModel.
    }
}