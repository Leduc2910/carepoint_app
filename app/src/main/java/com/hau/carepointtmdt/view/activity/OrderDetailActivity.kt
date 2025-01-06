package com.hau.carepointtmdt.view.activity

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hau.carepointtmdt.R
import com.hau.carepointtmdt.databinding.ActivityOrderDetailBinding
import com.hau.carepointtmdt.model.Address
import com.hau.carepointtmdt.model.Delivery
import com.hau.carepointtmdt.model.Medicine
import com.hau.carepointtmdt.model.Order_Detail
import com.hau.carepointtmdt.model.Order_Item
import com.hau.carepointtmdt.model.PaymentMethod
import com.hau.carepointtmdt.model.User
import com.hau.carepointtmdt.validation.CustomVerticalDecoration
import com.hau.carepointtmdt.validation.SharedPreferencesManager
import com.hau.carepointtmdt.view.adapter.MedItem3RV
import com.hau.carepointtmdt.viewmodel.GetAddressByUserIdState
import com.hau.carepointtmdt.viewmodel.GetDeliveryState
import com.hau.carepointtmdt.viewmodel.GetMedicineState
import com.hau.carepointtmdt.viewmodel.GetOrderItemByOrderIdState
import com.hau.carepointtmdt.viewmodel.GetPaymentMethodState
import com.hau.carepointtmdt.viewmodel.OrderDetailViewModel
import com.squareup.picasso.Picasso

class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    private val orderDetailViewModel: OrderDetailViewModel by viewModels()

    private lateinit var medItem3RV: MedItem3RV

    private lateinit var orderDetail: Order_Detail
    private lateinit var currentUser: User

    private var orderItemLst: List<Order_Item>? = null
    private var paymentMethodLst: List<PaymentMethod>? = null
    private var addressLst: List<Address>? = null
    private var deliveryLst: List<Delivery>? = null
    private var medicineLst: List<Medicine>? = null

    private val gson = Gson()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesManager = SharedPreferencesManager(this)
        currentUser = sharedPreferencesManager.getUser()!!

        val jsonOrderDetail = intent.getStringExtra("orderDetail")
        val type = object : TypeToken<Order_Detail>() {}.type
        orderDetail = gson.fromJson(jsonOrderDetail, type)
        Log.d("Current Order Detail", orderDetail.toString())

        addressObservers()
        deliveryObservers()
        paymentMethodObservesr()
        medicineObservers()
        orderItemOberservers()
        orderDetailViewModel.getAddressByUserId(currentUser.user_id)
        orderDetailViewModel.getDelivery()
        orderDetailViewModel.getPaymentMethod()
        orderDetailViewModel.getAllMedicine()

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.txtOrderDetailStatus.text  = when (orderDetail.status) {
            1 -> "Đơn hàng đang chờ giao cho đơn vị vận chuyển."
            2 -> "Đơn hàng đang trên đường giao đến bạn."
            3-> "Đơn hàng giao thành công."
            else -> ""
        }
    }

    fun addressObservers() {
        orderDetailViewModel.getAddressState.observe(this) { state ->
            when (state) {
                is GetAddressByUserIdState.Error -> {
                    Log.d("Address Error", state.message)
                }

                GetAddressByUserIdState.Loading -> {

                }

                is GetAddressByUserIdState.Success -> {
                    addressLst = state.addressLst

                    val address = addressLst!!.find { it.address_id == orderDetail.address_id }
                    if (address != null) {
                        binding.txtAddressName.text = address.user_name
                        binding.txtAddressPhone.text = address.user_phone
                        binding.txtAddress.text = address.address
                    }
                }
            }
        }
    }

    fun deliveryObservers() {
        orderDetailViewModel.getDeliveryState.observe(this) { state ->
            when (state) {
                is GetDeliveryState.Error -> {
                    Log.d("Delivery Error", state.message)
                }

                GetDeliveryState.Loading -> {

                }

                is GetDeliveryState.Success -> {
                    deliveryLst = state.deliveryLst

                    val delivery = deliveryLst!!.find { it.delivery_id == orderDetail.delivery_id }
                    if (delivery != null) {
                        binding.txtDeliveryName.text = delivery.delivery_name
                        binding.txtDeliveryPrice.text =
                            DecimalFormat("#,###").format(delivery.delivery_price)
                        binding.txtTotalPrice.text =
                            DecimalFormat("#,###").format((orderDetail.totalPrice - delivery.delivery_price))
                        binding.txtPrice.text =
                            DecimalFormat("#,###").format(orderDetail.totalPrice)
                    }
                }
            }
        }
    }

    fun paymentMethodObservesr() {
        orderDetailViewModel.getPaymentMethod.observe(this) { state ->
            when (state) {
                is GetPaymentMethodState.Error -> {
                    Log.d("PaymentMethod Error", state.message)
                }

                GetPaymentMethodState.Loading -> {

                }

                is GetPaymentMethodState.Success -> {
                    paymentMethodLst = state.paymentMethodLst

                    val paymentMethod =
                        paymentMethodLst!!.find { it.method_id == orderDetail.method_id }
                    if (paymentMethod != null) {
                        Picasso.get().load(paymentMethod.method_img).into(binding.imgPayment)
                        binding.txtPaymentName.text = paymentMethod.method_name
                    }

                }
            }
        }
    }

    fun medicineObservers() {
        orderDetailViewModel.getAllMedicine.observe(this) { state ->
            when (state) {
                is GetMedicineState.Error -> {
                    Log.d("Medicine Error", state.message)
                }

                GetMedicineState.Loading -> {

                }

                is GetMedicineState.Success -> {
                    medicineLst = state.medicineLst

                    orderDetailViewModel.getOrderItemByOrderId(orderDetail.order_id)
                }
            }
        }
    }

    fun orderItemOberservers() {
        orderDetailViewModel.getOrderItemState.observe(this) { state ->
            when (state) {
                is GetOrderItemByOrderIdState.Error -> {
                    Log.d("OrderItem Error", state.message)

                }

                GetOrderItemByOrderIdState.Loading -> {

                }

                is GetOrderItemByOrderIdState.Success -> {
                    orderItemLst = state.orderItemLst

                    binding.rvOrderItemLst.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    while (binding.rvOrderItemLst.itemDecorationCount > 0) {
                        binding.rvOrderItemLst.removeItemDecorationAt(0)
                    }
                    binding.rvOrderItemLst.addItemDecoration(
                        CustomVerticalDecoration(
                            resources.getDimensionPixelSize(
                                R.dimen.spacing_12dp
                            ), resources.getDimensionPixelSize(
                                R.dimen.spacing_0dp
                            )
                        )
                    )
                    medItem3RV = MedItem3RV(this, orderItemLst!!, medicineLst!!)
                    binding.rvOrderItemLst.adapter = medItem3RV
                }
            }
        }
    }
}