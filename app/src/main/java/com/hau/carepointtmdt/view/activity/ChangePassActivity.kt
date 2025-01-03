package com.hau.carepointtmdt.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hau.carepointtmdt.databinding.ActivityChangePassBinding
import com.hau.carepointtmdt.model.User
import com.hau.carepointtmdt.validation.SharedPreferencesManager
import com.hau.carepointtmdt.validation.ValidateData
import com.hau.carepointtmdt.viewmodel.ChangePassState
import com.hau.carepointtmdt.viewmodel.ChangePassViewModel

class ChangePassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePassBinding
    private val viewModel: ChangePassViewModel by viewModels()
    private lateinit var currentUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChangePassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentUser = SharedPreferencesManager(this).getUser()!!

        binding.btnBackToProfile.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("navigateTo", "ProfileFragment")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        binding.edtOldPass.setOnFocusChangeListener(OnFocusChangeListener { view, b ->
            if (!b) {
                hideKeyboard(view)
            }
        })
        binding.edtNewPass.setOnFocusChangeListener(View.OnFocusChangeListener { view, b ->
            if (!b) {
                hideKeyboard(view)
            }
        })

        binding.edtConfirmNewPass.setOnFocusChangeListener(OnFocusChangeListener { view, b ->
            if (!b) {
                hideKeyboard(view)
            }
        })
        setupObservers()

        binding.btnChangePass.setOnClickListener {
            hideTextError()

            val oldPass = binding.edtOldPass.text.toString()
            val newPass = binding.edtNewPass.text.toString()
            val confirmNewPass = binding.edtConfirmNewPass.text.toString()

            var hasError = false;

            if (oldPass.isEmpty()) {
                binding.txtErrorOldPass.visibility = View.VISIBLE
                binding.txtErrorOldPass.text = "Vui lòng nhập đầy đủ thông tin!"
                hasError = true
            }
            if (newPass.isEmpty()) {
                binding.txtErrorNewPass.visibility = View.VISIBLE
                binding.txtErrorNewPass.text = "Vui lòng nhập đầy đủ thông tin!"
                hasError = true
            } else if (!ValidateData.isValidPassword(newPass)) {
                binding.txtErrorNewPass.visibility = View.VISIBLE
                binding.txtErrorNewPass.text =
                    "Mật khẩu có độ dài 8-32 ký tự, bắt đầu bằng ký tự hoa, chứa ít nhất 1 ký tự số, 1 ký tự thường, và một ký tự đặc biệt trong các ký tự sau: !@#$%&.?_"
                hasError = true
            }
            if (confirmNewPass.isEmpty()) {
                binding.txtConfirmNewPass.visibility = View.VISIBLE
                binding.txtConfirmNewPass.text = "Vui lòng nhập đầy đủ thông tin!"
                hasError = true
            } else if (confirmNewPass != newPass && newPass.trim().isNotEmpty()) {
                binding.txtConfirmNewPass.visibility = View.VISIBLE
                binding.txtConfirmNewPass.text = "Mật khẩu không trùng khớp!"
                hasError = true
            }

            if (hasError)
                return@setOnClickListener

            viewModel.changePass(currentUser.user_id, oldPass, newPass)

        }
    }

    private fun setupObservers() {
        viewModel.changePassState.observe(this) { state ->
            when (state) {
                is ChangePassState.Loading -> {
                    binding.prgBarChangePass.visibility = View.VISIBLE
                    binding.viewWhiteOverlay4.visibility = View.VISIBLE
                    binding.btnChangePass.isEnabled = false
                }

                is ChangePassState.Success -> {
                    binding.prgBarChangePass.visibility = View.GONE
                    binding.viewWhiteOverlay4.visibility = View.GONE
                    binding.btnChangePass.isEnabled = true

                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()

                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }

                is ChangePassState.Error -> {
                    binding.prgBarChangePass.visibility = View.GONE
                    binding.viewWhiteOverlay4.visibility = View.GONE
                    binding.btnChangePass.isEnabled = true
                    if (state.error_code == 2) {
                        binding.txtErrorOldPass.visibility = View.VISIBLE
                        binding.txtErrorOldPass.text = state.message
                    } else if (state.error_code == 6) {
                        binding.txtErrorNewPass.visibility = View.VISIBLE
                        binding.txtErrorNewPass.text = state.message
                        binding.txtErrorOldPass.visibility = View.VISIBLE
                        binding.txtErrorOldPass.text = state.message
                    }
                }
            }
        }
    }

    private fun hideTextError() {
        binding.txtErrorOldPass.visibility = View.GONE
        binding.txtErrorNewPass.visibility = View.GONE
        binding.txtConfirmNewPass.visibility = View.GONE
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}