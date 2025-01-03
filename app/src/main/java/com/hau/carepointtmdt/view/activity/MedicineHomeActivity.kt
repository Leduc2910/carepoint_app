package com.hau.carepointtmdt.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.hau.carepointtmdt.R
import com.hau.carepointtmdt.databinding.ActivityMedicineHomeBinding
import com.hau.carepointtmdt.model.RecentMedSearch
import com.hau.carepointtmdt.validation.CustomHorizontalDecoration
import com.hau.carepointtmdt.validation.CustomVerticalDecoration
import com.hau.carepointtmdt.view.adapter.MedItem2RV
import com.hau.carepointtmdt.view.adapter.RecentMedSearchRV
import com.hau.carepointtmdt.viewmodel.GetMedicineState
import com.hau.carepointtmdt.viewmodel.MedicineHomeViewModel

class MedicineHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMedicineHomeBinding

    private val medHomeViewModel: MedicineHomeViewModel by viewModels()

    private lateinit var rvRecentSearh: RecentMedSearchRV
    private lateinit var rvDiscountMed: MedItem2RV
    private lateinit var rvNewStockMed: MedItem2RV
    private lateinit var rvAllMed: MedItem2RV

    private lateinit var recentMedSearchLst: ArrayList<RecentMedSearch>

    lateinit var imgRecentSearch: Array<Int>
    lateinit var txtRecentSearchName: Array<String>
    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMedicineHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataInitialize()

        rvRecentSearh = RecentMedSearchRV(this, recentMedSearchLst)
        binding.rvRecentSearch.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvRecentSearch.addItemDecoration(
            CustomVerticalDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.spacing_8dp
                ), resources.getDimensionPixelSize(R.dimen.spacing_0dp)
            )
        )
        binding.rvRecentSearch.adapter = rvRecentSearh

        binding.btnBackToHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        setupObserversGetAllMed()
        medHomeViewModel.getAllMedicine()

    }

    private fun setupObserversGetAllMed() {
        medHomeViewModel.medicineState.observe(this) { state ->
            when (state) {
                is GetMedicineState.Loading -> {
                    binding.prgBarDiscountMedicine.visibility = View.VISIBLE
                    binding.prgBarNewStockMedicine.visibility = View.VISIBLE
                    binding.prgBarAllMedicine.visibility = View.VISIBLE
                }
                is GetMedicineState.Success -> {
                    binding.prgBarDiscountMedicine.visibility = View.GONE
                    binding.prgBarNewStockMedicine.visibility = View.GONE
                    binding.prgBarAllMedicine.visibility = View.GONE

//                    var discountMedicineLst = state.medicineLst
//                    Log.d("discountMedicineLst", discountMedicineLst.toString())
//                    discountMedicineLst = discountMedicineLst.shuffled().filter { it.discount > 0 }
//                    Log.d("discountMedicineLst2", discountMedicineLst.toString())
//
//                    binding.rvDiscountMedicine.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//                    binding.rvDiscountMedicine.addItemDecoration(
//                        CustomHorizontalDecoration(
//                            resources.getDimensionPixelSize(
//                                R.dimen.spacing_16dp
//                            ), resources.getDimensionPixelSize(
//                                R.dimen.spacing_0dp
//                            )
//                        )
//                    )
//                    rvDiscountMed = MedItem2RV(this, discountMedicineLst)
//                    binding.rvDiscountMedicine.adapter = rvDiscountMed

                    var newStockMedicineLst = state.medicineLst
                    newStockMedicineLst = newStockMedicineLst.sortedByDescending { it.medicine_id }
                    Log.d("newStockMedicineLst", newStockMedicineLst.toString())

                    binding.rvNewStockMedicine.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    binding.rvNewStockMedicine.addItemDecoration(
                        CustomHorizontalDecoration(
                            resources.getDimensionPixelSize(
                                R.dimen.spacing_16dp
                            ), resources.getDimensionPixelSize(
                                R.dimen.spacing_0dp
                            )
                        )
                    )
                    rvNewStockMed = MedItem2RV(this, newStockMedicineLst)
                    binding.rvNewStockMedicine.adapter = rvNewStockMed


                    binding.btnNewMedLst.setOnClickListener {
                        val jsonMedicineList = gson.toJson(newStockMedicineLst)
                        val intent = Intent(this, MedicineListActivity::class.java)
                        intent.putExtra("medicineLst", jsonMedicineList)
                        startActivity(intent)
                        finish()
                    }

                    var allMedicineLst = state.medicineLst
                    allMedicineLst = allMedicineLst.shuffled()

                    binding.rvAllMedicine.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    binding.rvAllMedicine.addItemDecoration(
                        CustomHorizontalDecoration(
                            resources.getDimensionPixelSize(
                                R.dimen.spacing_16dp
                            ), resources.getDimensionPixelSize(
                                R.dimen.spacing_0dp
                            )
                        )
                    )
                    rvAllMed = MedItem2RV(this, allMedicineLst)
                    binding.rvAllMedicine.adapter = rvAllMed

                    binding.btnAllMedLst.setOnClickListener {
                        val jsonMedicineList = gson.toJson(allMedicineLst)
                        val intent = Intent(this, MedicineListActivity::class.java)
                        intent.putExtra("medicineLst", jsonMedicineList)
                        startActivity(intent)
                        finish()
                    }

                }
                is GetMedicineState.Error -> {
                    binding.prgBarDiscountMedicine.visibility = View.GONE
                    binding.prgBarNewStockMedicine.visibility = View.GONE
                    binding.prgBarAllMedicine.visibility = View.GONE
                    Log.d("Home Medicine Error", state.message)
                }
            }
        }
    }

    private fun dataInitialize() {
        recentMedSearchLst = arrayListOf<RecentMedSearch>()

        imgRecentSearch = arrayOf(
            R.drawable.img_recent_1,
            R.drawable.img_recent_2,
            R.drawable.img_recent_3
        )

        txtRecentSearchName = arrayOf(
            "Paracetamol",
            "Ibuprofen",
            "Amoxicillin"
        )

        for (i in imgRecentSearch.indices) {
            val recentMedSearch = RecentMedSearch(imgRecentSearch[i], txtRecentSearchName[i])
            recentMedSearchLst.add(recentMedSearch)
        }
    }
}
