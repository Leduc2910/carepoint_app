package com.hau.carepointtmdt.activity

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hau.carepointtmdt.R
import com.hau.carepointtmdt.adapter.MedItem2RV
import com.hau.carepointtmdt.adapter.MedItemRV
import com.hau.carepointtmdt.databinding.ActivityDetailMedicineBinding
import com.hau.carepointtmdt.model.Medicine
import com.hau.carepointtmdt.validation.CustomHorizontalDecoration
import com.hau.carepointtmdt.viewmodel.DetailMedicineState
import com.hau.carepointtmdt.viewmodel.DetailMedicineViewModel
import com.hau.carepointtmdt.viewmodel.GetProductByCatalogueIdState
import com.squareup.picasso.Picasso
import kotlin.math.log

class DetailMedicineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMedicineBinding

    private val detailMedViewModel: DetailMedicineViewModel by viewModels()

    private lateinit var medItemRV: MedItem2RV

    private var currentMedId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailMedicineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentMedId = intent.getIntExtra("medicine_id", -1)

        binding.btnBackToPrevious.setOnClickListener {
            finish()
        }

        setupObserversDetailMed()
        setupObserversMedicineLst()
        detailMedViewModel.getMedicineById(currentMedId!!)
    }

    private fun setupObserversDetailMed() {
        detailMedViewModel.detailMedicineState.observe(this) { state ->
            when (state) {
                is DetailMedicineState.Loading -> {
                    binding.prgBarDetailMed.visibility = View.VISIBLE
                    binding.viewWhiteOverlay5.visibility = View.VISIBLE
                }

                is DetailMedicineState.Success -> {
                    binding.prgBarDetailMed.visibility = View.GONE
                    binding.viewWhiteOverlay5.visibility = View.GONE

                    val medicine = state.medicine
                    dataInitialize(medicine)

                    Log.d(
                        "catalogue_id",
                        medicine.med_category.medCatalogue.pCatalogue_id.toString()
                    )

                    detailMedViewModel.getProductByCatalogueId(medicine.med_category.medCatalogue.pCatalogue_id)

                }

                is DetailMedicineState.Error -> {
                    binding.prgBarDetailMed.visibility = View.GONE
                    binding.viewWhiteOverlay5.visibility = View.GONE
                    Log.d("Error detail medicine", state.message)
                }
            }
        }
    }

    private fun setupObserversMedicineLst() {
        detailMedViewModel.getProductByCatalogueIdState.observe(this) { state ->
            when (state) {
                is GetProductByCatalogueIdState.Loading -> {
                    binding.prgBarSimilarMedicine.visibility = View.VISIBLE
                }

                is GetProductByCatalogueIdState.Success -> {
                    binding.prgBarSimilarMedicine.visibility = View.GONE
                    var similarMedItemLst = state.medicineLst

                    similarMedItemLst = similarMedItemLst?.filter { it.medicine_id != currentMedId
                    }

                    val medItemLayoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    binding.rvSimilarMedicine.layoutManager = medItemLayoutManager
                    binding.rvSimilarMedicine.addItemDecoration(
                        CustomHorizontalDecoration(
                            resources.getDimensionPixelSize(
                                R.dimen.spacing_16dp
                            ), resources.getDimensionPixelSize(
                                R.dimen.spacing_0dp
                            )
                        )
                    )
                    medItemRV = similarMedItemLst?.let { MedItem2RV(this, it) }!!
                    binding.rvSimilarMedicine.adapter = medItemRV

                }

                is GetProductByCatalogueIdState.Error -> {
                    binding.prgBarSimilarMedicine.visibility = View.GONE
                    Log.d("Home Medicine Error", state.message)
                }
            }
        }
    }

    fun dataInitialize(medicine: Medicine) {
        Picasso.get().load(medicine.medicine_img).into(binding.imgDetailMed)
        binding.txtDetailName.text = medicine.medicine_name
        binding.txtDetailPrice.text = DecimalFormat("#,###").format(medicine.medicine_price) + " đ"
        binding.txtDetailUnit.text = medicine.medicine_unit
        binding.txtDetailCate.text =
            medicine.med_category.medCatalogue.pCatalogue_name + " > " + medicine.med_category.pCate_name
        binding.txtDetailDosage.text = medicine.dosage_form
        binding.txtDetailManu.text = medicine.manufacturer
        binding.txtDetailOrigin.text = medicine.origin
        binding.txtDetailIngredient.text = medicine.ingredient
        binding.txtDetailUsage.text = medicine.usages

    }
}