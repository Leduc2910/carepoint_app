package com.hau.carepointtmdt.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hau.carepointtmdt.R
import com.hau.carepointtmdt.databinding.ActivityChangePassBinding

class ChangePassActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChangePassBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChangePassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackToProfile.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("navigateTo", "ProfileFragment")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

}