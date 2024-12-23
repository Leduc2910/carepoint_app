package com.hau.carepointtmdt.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.hau.carepointtmdt.databinding.ActivityLoginBinding
import com.hau.carepointtmdt.viewmodel.LoginState
import com.hau.carepointtmdt.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goToSignUp.setOnClickListener {
            val signupActivity = Intent(applicationContext, SignupActivity::class.java)
            startActivity(signupActivity)
            finish()
        }

        setupObservers()

        binding.btnLogin.setOnClickListener {
            val phoneNumber = binding.edtPhoneLogin.text.toString().trim()
            val password = binding.edtPassLogin.text.toString().trim()

            if (phoneNumber.isEmpty() || password.isEmpty()) {
                binding.txtErrorPhoneLogin.visibility = View.VISIBLE
                binding.txtErrorPassLogin.visibility = View.VISIBLE
                binding.txtErrorPhoneLogin.text = "Vui lòng nhập số điện thoại!"
                binding.txtErrorPassLogin.text = "Vui lòng nhập mật khẩu!"
                return@setOnClickListener
            }

            viewModel.login(phoneNumber, password)
        }
    }

    private fun setupObservers() {
        viewModel.loginState.observe(this) { state ->
            when (state) {
                is LoginState.Loading -> {
                    binding.progressBarLogin.visibility = View.VISIBLE
                    binding.viewWhiteOverlay.visibility = View.VISIBLE
                    binding.btnLogin.isEnabled = false
                }
                is LoginState.Success -> {
                    binding.progressBarLogin.visibility = View.GONE
                    binding.viewWhiteOverlay.visibility = View.GONE
                    binding.btnLogin.isEnabled = true

                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                    val mainActivity = Intent(this, MainActivity::class.java)
                    startActivity(mainActivity)
                    finish()
                }
                is LoginState.Error -> {
                    binding.progressBarLogin.visibility = View.GONE
                    binding.viewWhiteOverlay.visibility = View.GONE
                    binding.txtErrorPhoneLogin.visibility = View.VISIBLE
                    binding.txtErrorPassLogin.visibility = View.VISIBLE
                    binding.btnLogin.isEnabled = true
                    binding.txtErrorPhoneLogin.text = state.message
                    binding.txtErrorPassLogin.text = state.message
                }
            }
        }
    }
}
