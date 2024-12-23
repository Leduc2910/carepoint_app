package com.hau.carepointtmdt.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.hau.carepointtmdt.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener(View.OnClickListener {
            val loginActivity = Intent(applicationContext, LoginActivity::class.java)
            startActivity(loginActivity)
            finish()
        })

        binding.goToLogin.setOnClickListener(View.OnClickListener {
            val loginActivity = Intent(applicationContext, LoginActivity::class.java)
            startActivity(loginActivity)
            finish()
        })
    }
}