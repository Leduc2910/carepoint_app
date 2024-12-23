package com.hau.carepointtmdt.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hau.carepointtmdt.activity.ActivityPurchaseOrder
import com.hau.carepointtmdt.activity.ChangePassActivity
import com.hau.carepointtmdt.activity.LoginActivity
import com.hau.carepointtmdt.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

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

        binding.btnEditPass.setOnClickListener(View.OnClickListener {
            val editPassActivity = Intent(requireContext(), ChangePassActivity::class.java)
            startActivity(editPassActivity)
        })

        binding.btnLogout.setOnClickListener(View.OnClickListener {
            val loginActivity = Intent(requireContext(), LoginActivity::class.java)
            startActivity(loginActivity)
            requireActivity().finish()
        })

        binding.btnGoToOrders.setOnClickListener(View.OnClickListener {
            val purchaseOrderActivit = Intent(requireContext(), ActivityPurchaseOrder::class.java)
            startActivity(purchaseOrderActivit)
        })
    }
}