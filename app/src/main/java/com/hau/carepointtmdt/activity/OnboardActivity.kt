package com.hau.carepointtmdt.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.projectthuctap2.PagerAdapter.OnboardViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hau.carepointtmdt.model.OnboardItem
import com.hau.carepointtmdt.R
import com.hau.carepointtmdt.databinding.ActivityOnboardBinding

class OnboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardBinding
    private lateinit var onboardViewPagerAdapter: OnboardViewPagerAdapter
    private lateinit var onboardItemList: MutableList<OnboardItem>

    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onboardItemList = ArrayList<OnboardItem>()
        onboardItemList.add(
            OnboardItem(
                "Đặt lịch khám với bác sĩ phù hợp",
                "Dễ dàng đặt lịch khám và lưu trữ thông tin\nsức khỏe của bạn.",
                R.drawable.img_onboard_1
            )
        )
        onboardItemList.add(
            OnboardItem(
                "Giao thuốc đến tận nơi",
                "Đặt thuộc đơn giản và nhanh chóng không phải đến hiệu thuốc.",
                R.drawable.img_onboard_2
            )
        )
        onboardItemList.add(
            OnboardItem(
                "Quên uống thuốc? Chúng tôi sẽ nhắc bạn mỗi ngày",
                "Không còn sợ quên uống thuốc - nhận lời nhắc để duy trì việc uống thuốc đều đặn.",
                R.drawable.img_onboard_3
            )
        )

        onboardViewPagerAdapter = OnboardViewPagerAdapter(this, onboardItemList)

        binding.screenViewpager.setAdapter(onboardViewPagerAdapter)

        binding.screenViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                this@OnboardActivity.position = position

                if (position == onboardItemList.size - 1) {
                    loadLastScreen()
                } else {
                    restoreDefaultScreen()
                }
            }
        })


        TabLayoutMediator(
            binding.onboardIndicator, binding.screenViewpager
        ) { tab: TabLayout.Tab?, position: Int -> }.attach()

        binding.btnNext.setOnClickListener(View.OnClickListener {
            position =  binding.screenViewpager.currentItem
            if (position < onboardItemList.size) {
                position++
                binding.screenViewpager.currentItem = position
            }
            if (position == onboardItemList.size - 1) {
                loadLastScreen()
            }
        })

        binding.btnSkip.setOnClickListener(View.OnClickListener {
            binding.screenViewpager.currentItem = onboardItemList.size - 1
            loadLastScreen()
        })

        binding.btnGoLogin.setOnClickListener(View.OnClickListener {
            val loginActivity = Intent(applicationContext, LoginActivity::class.java)
            startActivity(loginActivity)
            savePrefsData()
            finish()
        })
        binding.btnGetStarted.setOnClickListener(View.OnClickListener {
            val signupActivity = Intent(applicationContext, SignupActivity::class.java)
            startActivity(signupActivity)
            savePrefsData()
            finish()
        })
    }
    private fun restorePrefData(): Boolean {
        val preferences = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        val isOnboardActivityOpenedBefore = preferences.getBoolean("isOnboardOpened", false)
        return isOnboardActivityOpenedBefore
    }

    private fun savePrefsData() {
        val pref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("isOnboardOpened", true)
        editor.commit()
    }

    private fun loadLastScreen() {
        binding.btnNext.setVisibility(View.INVISIBLE)
        binding.btnSkip.setVisibility(View.INVISIBLE)
        binding.btnGetStarted.setVisibility(View.VISIBLE)
        binding.btnGoLogin.setVisibility(View.VISIBLE)
    }

    private fun restoreDefaultScreen() {
        binding.btnNext.visibility = View.VISIBLE
        binding.btnSkip.visibility = View.VISIBLE
        binding.btnGetStarted.visibility = View.INVISIBLE
        binding.btnGoLogin.visibility = View.INVISIBLE
    }


}