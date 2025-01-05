package com.hau.carepointtmdt.view.activity

import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.hau.carepointtmdt.R
import com.hau.carepointtmdt.databinding.ActivityDetailMedicineBinding
import com.hau.carepointtmdt.model.Medicine
import com.hau.carepointtmdt.model.Order
import com.hau.carepointtmdt.validation.CustomHorizontalDecoration
import com.hau.carepointtmdt.validation.SharedPreferencesManager
import com.hau.carepointtmdt.view.adapter.MedItem2RV
import com.hau.carepointtmdt.viewmodel.AddToCartState
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
    private var currentBuyQuantity = 1
    private lateinit var order_user: Order

    lateinit var sharedPreferencesManager: SharedPreferencesManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailMedicineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesManager = SharedPreferencesManager(this)
        order_user = sharedPreferencesManager.getOrder()!!
        Log.d("order_user", order_user.toString())

        currentMedId = intent.getIntExtra("medicine_id", -1)
        binding.frameBuyMedicine.visibility = View.GONE

        binding.btnBackToPrevious.setOnClickListener {
            finish()
        }
        binding.btnCloseBuy.setOnClickListener {
            binding.frameBuyMedicine.visibility = View.GONE
        }
        binding.btnGotoCart.setOnClickListener {
            val cartActivity = Intent(this, CartActivity::class.java)
            startActivity(cartActivity)
        }

        setupObserversDetailMed()
        setupObserversMedicineLst()
        setupObserversAddToCart()
        detailMedViewModel.getMedicineById(currentMedId!!)
    }

    private fun setupObserversAddToCart() {
        detailMedViewModel.addToCartState.observe(this) { state ->
            when (state) {
                is AddToCartState.Loading -> {
                }

                is AddToCartState.Success -> {
                    binding.frameBuyMedicine.visibility = View.GONE
                    Toast.makeText(this, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show()
                }

                is AddToCartState.Error -> {
                    Log.d("AddToCart Error", state.message)
                }
            }
        }
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

                    detailMedViewModel.getProductByCatalogueId(medicine.med_category.medCatalogue.pCatalogue_id)

                    binding.btnAddtoCart.setOnClickListener {
                        dataInitModalBuyMed(medicine)
                        binding.frameBuyMedicine.visibility = View.VISIBLE
                        binding.btnAddtoCart2.visibility = View.VISIBLE
                        binding.btnBuyNow2.visibility = View.GONE
                    }

                    binding.btnBuyNow.setOnClickListener {
                        dataInitModalBuyMed(medicine)
                        binding.frameBuyMedicine.visibility = View.VISIBLE
                        binding.btnBuyNow2.visibility = View.VISIBLE
                        binding.btnAddtoCart2.visibility = View.GONE
                    }

                    binding.btnMinusQuantity.setOnClickListener {
                        if (currentBuyQuantity > 1) {
                            currentBuyQuantity--
                            updateQuantityUI(currentBuyQuantity, medicine.quantity)
                        }
                    }

                    binding.btnPlusQuantity.setOnClickListener {
                        if (currentBuyQuantity < 10) {
                            currentBuyQuantity++
                            updateQuantityUI(currentBuyQuantity, medicine.quantity)
                        }
                    }

                    updateQuantityUI(currentBuyQuantity, medicine.quantity)
                    binding.btnAddtoCart2.setOnClickListener {
                        binding.frameBuyMedicine.visibility = View.GONE
                        Toast.makeText(this, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT)
                            .show()
                        detailMedViewModel.addToCart(
                            medicine.medicine_id,
                            order_user.order_id,
                            currentBuyQuantity
                        )
                    }
                    binding.btnBuyNow2.setOnClickListener {

                    }

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

                    similarMedItemLst = similarMedItemLst?.shuffled()?.filter {
                        it.medicine_id != currentMedId
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
        binding.txtDetailPrice.text =
            DecimalFormat("#,###").format(medicine.medicine_price) + " đ"
        binding.txtDetailUnit.text = medicine.medicine_unit
        binding.txtDetailCate.text =
            medicine.med_category.medCatalogue.pCatalogue_name + " > " + medicine.med_category.pCate_name
        binding.txtDetailDosage.text = medicine.dosage_form
        binding.txtDetailManu.text = medicine.manufacturer
        binding.txtDetailOrigin.text = medicine.origin
        binding.txtDetailIngredient.text = medicine.ingredient
        binding.txtDetailUsage.text = medicine.usages

    }

    fun dataInitModalBuyMed(medicine: Medicine) {
        Picasso.get().load(medicine.medicine_img).into(binding.imgBuyMed)
        binding.txtBuyMedName.text = medicine.medicine_name
        binding.txtBuyMedPrice.text =
            DecimalFormat("#,###").format(medicine.medicine_price) + " đ"
        binding.txtBuyMedUnit.text = medicine.medicine_unit
        binding.txtStoreQuantity.text = medicine.quantity.toString()
        binding.txtBuyMedUnit.text = medicine.medicine_unit
        binding.btnMedUnit.text = medicine.medicine_unit
        binding.txtBuyMedQuantity.text = "1"
        currentBuyQuantity = 1
        updateQuantityUI(currentBuyQuantity, medicine.quantity)
    }

    fun overQuantity(img: ImageView, background: CardView) {
        img.setColorFilter(ContextCompat.getColor(this, R.color.bg_quaternary))
        background.setCardBackgroundColor(getResources().getColor(R.color.bg_secondary))
    }

    fun defaultQuantity(img: ImageView, background: CardView) {
        img.setColorFilter(ContextCompat.getColor(this, R.color.brand))
        background.setCardBackgroundColor(getResources().getColor(R.color.bg_brandLight2))
    }

    private fun updateQuantityUI(quantity: Int, store_quantity: Int) {
        binding.txtBuyMedQuantity.text = quantity.toString()

        if (quantity <= 1) {
            overQuantity(binding.imgMinus, binding.btnMinusQuantity)
        } else {
            defaultQuantity(binding.imgMinus, binding.btnMinusQuantity)
        }

        if (quantity >= store_quantity) {
            overQuantity(binding.imgPlus, binding.btnPlusQuantity)
        } else {
            defaultQuantity(binding.imgPlus, binding.btnPlusQuantity)
        }
    }
}