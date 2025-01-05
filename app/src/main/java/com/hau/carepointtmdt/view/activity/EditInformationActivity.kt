package com.hau.carepointtmdt.view.activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.hau.carepointtmdt.R
import com.hau.carepointtmdt.databinding.ActivityEditInformationBinding
import com.hau.carepointtmdt.model.User
import com.hau.carepointtmdt.validation.SharedPreferencesManager
import com.hau.carepointtmdt.viewmodel.UpdateInfoUserState
import com.hau.carepointtmdt.viewmodel.UpdateInfoUserViewModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class EditInformationActivity : AppCompatActivity() {

    private val SELECT_PICTURE = 200
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private lateinit var binding: ActivityEditInformationBinding
    private val editInfoViewModel: UpdateInfoUserViewModel by viewModels()

    private lateinit var currentUser: User
    private lateinit var imgPath: Uri
    lateinit var imgUrlCloud: String

    private var isEditMode = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataInitialize()
        disableField()

        binding.btnBackToProfile2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("navigateTo", "ProfileFragment")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        binding.btnEdit.setOnClickListener {
            if (isEditMode) {
                disableField()
                dataInitialize()
                isEditMode = false
            } else {
                enableField()
                isEditMode = true
            }
        }


        binding.imgAvatarUser.setOnClickListener {
            if (isEditMode) {
                imageChooser()
            }
        }

        binding.slBirthday.setOnClickListener {
            if (isEditMode) {
                val calendar = Calendar.getInstance()

                val today = calendar.timeInMillis

                calendar.add(Calendar.YEAR, -18)
                val maxDate = calendar.timeInMillis

                calendar.add(Calendar.YEAR, -82)
                val minDate = calendar.timeInMillis

                val constraintsBuilder = CalendarConstraints.Builder()
                    .setStart(minDate)
                    .setEnd(today)
                    .setValidator(DateValidatorPointBackward.now())

                val datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Chọn ngày sinh")
                    .setCalendarConstraints(constraintsBuilder.build())
                    .setSelection(maxDate)
                    .build()

                datePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")

                datePicker.addOnPositiveButtonClickListener { selection ->
                    val date = dateFormat.format(Date(selection))
                    binding.txtBirthday.text = date
                }
            }
        }


        binding.edtUserName.setOnFocusChangeListener(OnFocusChangeListener { view, b ->
            if (!b) {
                hideKeyboard(view)
            }
        })
        setupObservers()

        binding.btnEditSave.setOnClickListener {
            hideTextError()

            val name = binding.edtUserName.text.toString()
            val gender = when {
                binding.rdMale.isChecked -> 1
                binding.rdFemale.isChecked -> 2
                binding.rdOther.isChecked -> 3
                else -> 0
            }
            val birthdayTxt = binding.txtBirthday.text.toString()

            var hasError = false

            if (name.isEmpty()) {
                binding.txtErrorNameInfo.visibility = View.VISIBLE
                binding.txtErrorNameInfo.text = "Vui lòng không bỏ trống trường này!"
                hasError = true
            }
            if (gender == 0) {
                binding.txtErrorGenderInfo.visibility = View.VISIBLE
                binding.txtErrorGenderInfo.text = "Vui lòng chọn giới tính của bạn!"
                hasError = true
            }
            if (birthdayTxt == "--- Chọn ngày sinh ---") {
                binding.txtErrorBirthdayInfo.visibility = View.VISIBLE
                binding.txtErrorBirthdayInfo.text = "Vui lòng chọn ngày sinh của bạn!"
                hasError = true
            } else {
                val birthdayDate = dateFormat.parse(birthdayTxt)
                val currentDate = Calendar.getInstance().time
                val diff = currentDate.time - birthdayDate.time
                val age = (diff / (1000L * 60 * 60 * 24 * 365)).toInt()

                if (age < 18) {
                    binding.txtErrorBirthdayInfo.visibility = View.VISIBLE
                    binding.txtErrorBirthdayInfo.text = "Bạn phải từ 18 tuổi trở lên để cập nhật thông tin!"
                    hasError = true
                }
            }

            if (hasError)
                return@setOnClickListener

            if (::imgPath.isInitialized) {
                MediaManager.get().upload(imgPath).callback(object : UploadCallback {
                    override fun onStart(requestId: String) {
                        Log.d(TAG, "onStart: started")
                    }

                    override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
                        binding.viewWhiteOverlay3.visibility = View.VISIBLE
                        binding.prgBarUpdateInfo.visibility = View.VISIBLE
                        Log.d(TAG, "onProgress: uploading")
                    }

                    override fun onSuccess(requestId: String, resultData: Map<*, *>?) {
                        binding.viewWhiteOverlay3.visibility = View.GONE
                        binding.prgBarUpdateInfo.visibility = View.GONE
                        Log.d(TAG, "onSuccess: successful upload")
                        imgUrlCloud = resultData?.get("secure_url") as String

                        editInfoViewModel.updateInfoUser(
                            name,
                            gender,
                            birthdayTxt,
                            imgUrlCloud,
                            currentUser.user_id
                        )
                    }

                    override fun onError(requestId: String, error: ErrorInfo) {
                        binding.viewWhiteOverlay3.visibility = View.GONE
                        binding.prgBarUpdateInfo.visibility = View.GONE
                        Log.d(TAG, "onError: $error")
                    }

                    override fun onReschedule(requestId: String, error: ErrorInfo) {
                        binding.viewWhiteOverlay3.visibility = View.GONE
                        binding.prgBarUpdateInfo.visibility = View.GONE
                        Log.d(TAG, "onReschedule: $error")
                    }
                }).dispatch()
            } else {
                editInfoViewModel.updateInfoUser(
                    name,
                    gender,
                    birthdayTxt,
                    currentUser.avatar,
                    currentUser.user_id
                )
            }
        }

    }

    private fun setupObservers() {
        editInfoViewModel.updateInfoUserState.observe(this) { state ->
            when (state) {
                is UpdateInfoUserState.Loading -> {
                    binding.viewWhiteOverlay3.visibility = View.VISIBLE
                    binding.prgBarUpdateInfo.visibility = View.VISIBLE
                    binding.btnEditSave.isEnabled = false
                }

                is UpdateInfoUserState.Success -> {
                    binding.viewWhiteOverlay3.visibility = View.GONE
                    binding.prgBarUpdateInfo.visibility = View.GONE
                    binding.btnEditSave.isEnabled = true

                    state.user?.let { SharedPreferencesManager(this).saveUser(it) }
                    Toast.makeText(this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT)
                        .show()
                    dataInitialize()
                    disableField()

                }

                is UpdateInfoUserState.Error -> {
                    binding.viewWhiteOverlay3.visibility = View.GONE
                    binding.prgBarUpdateInfo.visibility = View.GONE
                    binding.btnEditSave.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun dataInitialize() {
        currentUser = SharedPreferencesManager(this).getUser()!!
        val avatarUrl = currentUser.avatar
        Picasso.get().load(avatarUrl).into(binding.imgAvatarUser)
        binding.edtUserName.setText(currentUser.name)
        binding.edtUserPhone.setText(currentUser.phoneNumber)
        when (currentUser.gender) {
            1 -> binding.rdMale.isChecked = true
            2 -> binding.rdFemale.isChecked = true
            3 -> binding.rdOther.isChecked = true
            else -> {
                binding.rdMale.isChecked = false
                binding.rdFemale.isChecked = false
                binding.rdOther.isChecked = false
            }
        }
        if (currentUser.birthday != null)
            binding.txtBirthday.text = currentUser.birthday
    }


    private fun imageChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                imgPath = data?.data!!
                if (imgPath != null) {
                    Picasso.get().load(imgPath).into(binding.imgAvatarUser)
                }
            }
        }
    }

    private fun disableField() {
        isEditMode= false
        binding.edtUserName.isEnabled = false
        binding.edtUserName.setTextColor(ContextCompat.getColor(this, R.color.text_secondary))
        binding.rdMale.isEnabled = false
        binding.rdFemale.isEnabled = false
        binding.rdOther.isEnabled = false
        binding.btnEditSave.isEnabled = false
        binding.btnEditSave.setBackgroundResource(R.drawable.disbale_primary_btn)
        binding.btnEditSave.setTextColor(ContextCompat.getColor(this, R.color.spe_text_lightGray))
    }

    private fun enableField() {
        isEditMode= true
        binding.edtUserName.isEnabled = true
        binding.edtUserName.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
        binding.rdMale.isEnabled = true
        binding.rdFemale.isEnabled = true
        binding.rdOther.isEnabled = true
        binding.btnEditSave.isEnabled = true
        binding.btnEditSave.setBackgroundResource(R.drawable.default_primary_btn)
        binding.btnEditSave.setTextColor(ContextCompat.getColor(this, R.color.spe_text_white))
    }

    private fun hideTextError() {
        binding.txtErrorNameInfo.visibility = View.GONE
        binding.txtErrorGenderInfo.visibility = View.GONE
        binding.txtErrorBirthdayInfo.visibility = View.GONE
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}