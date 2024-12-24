package com.hau.carepointtmdt.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hau.carepointtmdt.databinding.ActivitySignupBinding
import com.hau.carepointtmdt.validation.ValidateData
import com.hau.carepointtmdt.viewmodel.RegisterState
import com.hau.carepointtmdt.viewmodel.RegisterViewModel

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edtNameRegis.setOnFocusChangeListener(View.OnFocusChangeListener { view, b ->
            if (!b) {
                hideKeyboard(view)
            }
        })
        binding.edtPhoneRegis.setOnFocusChangeListener(View.OnFocusChangeListener { view, b ->
            if (!b) {
                hideKeyboard(view)
            }
        })
        binding.edtPassRegis.setOnFocusChangeListener(View.OnFocusChangeListener { view, b ->
            if (!b) {
                hideKeyboard(view)
            }
        })
        binding.edtConfirmPassRegis.setOnFocusChangeListener(View.OnFocusChangeListener { view, b ->
            if (!b) {
                hideKeyboard(view)
            }
        })

        setupObservers()

        binding.btnSignup.setOnClickListener {
            hideTextError()

            val name = binding.edtNameRegis.text.toString().trim()
            val phoneNumber = binding.edtPhoneRegis.text.toString().trim()
            val password = binding.edtPassRegis.text.toString().trim()
            val confirmPassword = binding.edtConfirmPassRegis.text.toString().trim()

            var hasError = false;

            if (name.isEmpty()) {
                binding.txtErrorNameRes.visibility = View.VISIBLE
                binding.txtErrorNameRes.text = "Vui lòng nhập tên!"
                hasError = true
            }
            if (phoneNumber.isEmpty()) {
                binding.txtErrorPhoneRegis.visibility = View.VISIBLE
                binding.txtErrorPhoneRegis.text = "Vui lòng nhập số điện thoại!"
                hasError = true
            } else if (!ValidateData.isValidPhoneNumber(phoneNumber)) {
                binding.txtErrorPhoneRegis.visibility = View.VISIBLE
                binding.txtErrorPhoneRegis.text = "Số điện thoại không hợp lệ!"
                hasError = true
            }

            if (password.isEmpty()) {
                binding.txtErrorPassRegis.visibility = View.VISIBLE
                binding.txtErrorPassRegis.text = "Vui lòng nhập mật khẩu!"
                hasError = true
            } else if (!ValidateData.isValidPassword(password)) {
                binding.txtErrorPassRegis.visibility = View.VISIBLE
                binding.txtErrorPassRegis.text = "Mật khẩu có độ dài 8-32 ký tự, bắt đầu bằng ký tự hoa, chứa ít nhất 1 ký tự số, 1 ký tự thường, và một ký tự đặc biệt trong các ký tự sau: !@#$%&.?_"
                hasError = true
            }

            if (confirmPassword.isEmpty()) {
                binding.txtErrorConPassRegis.visibility = View.VISIBLE
                binding.txtErrorConPassRegis.text = "Vui lòng nhập lại mật khẩu!"
                hasError = true
            } else if (password != confirmPassword && password.trim().isNotEmpty()) {
                binding.txtErrorConPassRegis.visibility = View.VISIBLE
                binding.txtErrorConPassRegis.text = "Mật khẩu không khớp!"
                hasError = true
            }

            if (hasError)
                return@setOnClickListener

            viewModel.register(name, phoneNumber, password)
        }

        binding.goToLogin.setOnClickListener(View.OnClickListener {
            val loginActivity = Intent(applicationContext, LoginActivity::class.java)
            startActivity(loginActivity)
            finish()
        })
    }

    private fun setupObservers() {
        viewModel.registerState.observe(this) { state ->
            when (state) {
                is RegisterState.Loading -> {
                    binding.viewWhiteOverlay2.visibility = View.VISIBLE
                    binding.progressBarRegister.visibility = View.VISIBLE
                    binding.btnSignup.isEnabled = false
                }
                is RegisterState.Success -> {
                    binding.viewWhiteOverlay2.visibility = View.GONE
                    binding.progressBarRegister.visibility = View.GONE
                    binding.btnSignup.isEnabled = true

                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                    val loginActivity = Intent(this, LoginActivity::class.java)
                    startActivity(loginActivity)
                    finish()
                }
                is RegisterState.Error -> {
                    binding.viewWhiteOverlay2.visibility = View.GONE
                    binding.progressBarRegister.visibility = View.GONE
                    binding.btnSignup.isEnabled = true
                    binding.txtErrorPhoneRegis.visibility = View.VISIBLE
                    binding.txtErrorPhoneRegis.text = state.message
                }
            }
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun hideTextError() {
        binding.txtErrorNameRes.visibility = View.GONE
        binding.txtErrorPhoneRegis.visibility = View.GONE
        binding.txtErrorPassRegis.visibility = View.GONE
        binding.txtErrorConPassRegis.visibility = View.GONE

    }
}