package com.hau.carepointtmdt.model

data class Medicine(
    val medicine_id: Int,
    val medicine_name: String,
    val medicine_price: Int,
    val medicine_unit: String,
    val medicine_img: String,
    val med_category: MedCategory,
    val dosage_form: String,
    val manufacturer: String,
    val origin: String,
    val ingredient: String,
    val usages: String,
    val quantity : Int,
    val order_quantity : Int
)