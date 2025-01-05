package com.hau.carepointtmdt.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hau.carepointtmdt.databinding.FragmentProfileBinding
import com.hau.carepointtmdt.model.User
import com.hau.carepointtmdt.validation.SharedPreferencesManager
import com.hau.carepointtmdt.view.activity.ActivityPurchaseOrder
import com.hau.carepointtmdt.view.activity.ChangePassActivity
import com.hau.carepointtmdt.view.activity.EditInformationActivity
import com.hau.carepointtmdt.view.activity.LoginActivity
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var currentUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = SharedPreferencesManager(requireContext())
        currentUser = sharedPreferences.getUser()!!
        Log.d("user", currentUser.toString())

        dataInitialize()

        binding.btnEditInformation.setOnClickListener {
            val editInformationActivity =
                Intent(requireContext(), EditInformationActivity::class.java)
            startActivity(editInformationActivity)
        }

        binding.btnEditPass.setOnClickListener(View.OnClickListener {
            val editPassActivity = Intent(requireContext(), ChangePassActivity::class.java)
            startActivity(editPassActivity)
        })

        binding.btnLogout.setOnClickListener(View.OnClickListener {
            sharedPreferences.clearUser()
            val loginActivity = Intent(requireContext(), LoginActivity::class.java)
            startActivity(loginActivity)
            requireActivity().finish()
        })

        binding.btnGoToOrders.setOnClickListener(View.OnClickListener {
            val purchaseOrderActivit = Intent(requireContext(), ActivityPurchaseOrder::class.java)
            startActivity(purchaseOrderActivit)
        })
    }

    private fun dataInitialize() {
        val avatarUrl = currentUser.avatar
        Picasso.get().load(avatarUrl).into(binding.imgProAva)
        binding.txtNamePro.text = currentUser.name
        binding.txtPhonePro.text = currentUser.phoneNumber

    }
}