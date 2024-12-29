package com.hau.carepointtmdt.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hau.carepointtmdt.R
import com.hau.carepointtmdt.databinding.ActivityMainBinding
import com.hau.carepointtmdt.fragment.CartFragment
import com.hau.carepointtmdt.fragment.HomeFragment
import com.hau.carepointtmdt.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigateTo = intent.getStringExtra("navigateTo")
        if (navigateTo == "ProfileFragment") {
            binding.bottomNavMain.selectedItemId = R.id.profile
            replaceMainFragment(ProfileFragment())
        } else if (navigateTo == "CartFragment") {
            binding.bottomNavMain.selectedItemId = R.id.cart
            replaceMainFragment(CartFragment())
        } else {
            binding.bottomNavMain.selectedItemId = R.id.home
            replaceMainFragment(HomeFragment())
        }

        binding.bottomNavMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceMainFragment(HomeFragment())
                R.id.medicine -> {
                    val intent = Intent(this, MedicineHomeActivity::class.java)
                    startActivity(intent)
                }
                R.id.cart -> replaceMainFragment(CartFragment())
                R.id.profile -> replaceMainFragment(ProfileFragment())
                else -> {}
            }
            true
        }

    }

    private fun replaceMainFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFragment, fragment)
        fragmentTransaction.commit()

    }

}