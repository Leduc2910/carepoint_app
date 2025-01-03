package com.hau.carepointtmdt.view.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hau.carepointtmdt.R
import com.hau.carepointtmdt.databinding.ActivityMedicineListBinding
import com.hau.carepointtmdt.model.Medicine
import com.hau.carepointtmdt.validation.GridSpacingItemDecoration
import com.hau.carepointtmdt.view.adapter.MedItem2RV
import com.hau.carepointtmdt.view.adapter.MedItemRV

class MedicineListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMedicineListBinding

    private lateinit var rvMedicineList: MedItemRV

    private lateinit var medicineLst: ArrayList<Medicine>

    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMedicineListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jsonMedicineList = intent.getStringExtra("medicineLst")
        val type = object : TypeToken<ArrayList<Medicine>>() {}.type
        medicineLst = gson.fromJson(jsonMedicineList, type)

        binding.rvMedListByTitle.layoutManager = GridLayoutManager(this, 2)
        while (binding.rvMedListByTitle.itemDecorationCount > 0) {
            binding.rvMedListByTitle.removeItemDecorationAt(0)
        }
        binding.rvMedListByTitle.addItemDecoration(
            GridSpacingItemDecoration(
                2,
                resources.getDimensionPixelSize(R.dimen.spacing_16dp)
            )
        )
        rvMedicineList = MedItemRV(this, medicineLst)
        binding.rvMedListByTitle.adapter = rvMedicineList

        binding.btnBackToMedHome.setOnClickListener {
            finish()
        }
    }
}