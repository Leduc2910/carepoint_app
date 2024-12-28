package com.hau.carepointtmdt

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hau.carepointtmdt.databinding.ActivityMedicineHomeBinding

class MedicineHomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMedicineHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMedicineHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}