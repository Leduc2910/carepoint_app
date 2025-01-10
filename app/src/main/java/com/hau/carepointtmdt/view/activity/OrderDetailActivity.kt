package com.hau.carepointtmdt.view.activity

import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.View
import android.widget.Toast
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
import com.hau.carepointtmdt.model.CreateOrder
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
import com.hau.carepointtmdt.viewmodel.UpdateOrderDetailState
import com.squareup.picasso.Picasso
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener

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

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        ZaloPaySDK.init(2554, Environment.SANDBOX)

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
        updateOrderDetailObservers()
        orderDetailViewModel.getAddressByUserId(currentUser.user_id)
        orderDetailViewModel.getDelivery()
        orderDetailViewModel.getPaymentMethod()
        orderDetailViewModel.getAllMedicine()

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, ActivityPurchaseOrder::class.java)
            startActivity(intent)
            finish()
        }

        binding.txtOrderDetailStatus.text = when (orderDetail.status) {
            1 -> "Đang chờ người bán xác nhận."
            2 -> "Vui lòng hoàn tất thanh toán."
            3 -> "Đơn hàng đang chờ giao cho đơn vị vận chuyển."
            4 -> "Đơn hàng đang trên đường giao đến bạn."
            5 -> "Đơn hàng giao thành công."
            else -> ""
        }

        if (orderDetail.status == 2) {
            binding.frameHoverBtn.visibility = View.VISIBLE
        } else {
            binding.frameHoverBtn.visibility = View.GONE
        }

        binding.btnPay.setOnClickListener {
            try {
                val orderApi = CreateOrder()
                val totalPrice = orderDetail.totalPrice
                val data = orderApi.createOrder(totalPrice.toString())

                val code = data?.getString("return_code")
                if (code == "1") {

                    val zpTransToken = data.getString("zp_trans_token")

                    ZaloPaySDK.getInstance()
                        .payOrder(this, zpTransToken, "orderdetail://app", object :
                            PayOrderListener {
                            override fun onPaymentSucceeded(
                                transactionId: String,
                                transToken: String,
                                appTransID: String
                            ) {
                                orderDetailViewModel.updateOrderDetail(
                                    orderDetail.orderDetail_id,
                                    3,
                                    transToken
                                )
                                Log.d("tokkensucess", transToken)
                            }

                            override fun onPaymentCanceled(
                                zpTransToken: String,
                                appTransID: String
                            ) {
                                orderDetailViewModel.updateOrderDetail(
                                    orderDetail.orderDetail_id,
                                    2,
                                    zpTransToken
                                )
                                Log.d("tokkenfailed", zpTransToken)
                            }

                            override fun onPaymentError(
                                zaloPayError: ZaloPayError,
                                zpTransToken: String,
                                appTransID: String
                            ) {

                            }
                        })
                } else {
                    Toast.makeText(
                        this,
                        "Thanh toán thất bại, vui lòng thử lại!",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Đã xảy ra lỗi, vui lòng thử lại sau!", Toast.LENGTH_LONG)
                    .show()
            }
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

    fun updateOrderDetailObservers() {
        orderDetailViewModel.updateOrderDetailState.observe(this) { state ->
            when (state) {
                is UpdateOrderDetailState.Error -> {
                    Log.d("UpdateOrderDetail Error", state.message)
                }

                UpdateOrderDetailState.Loading -> {

                }

                is UpdateOrderDetailState.Success -> {
                    orderDetail = state.order_detail
                    Log.d("orderDetail", orderDetail.toString())
                    val jSonOrderDetail = gson.toJson(state.order_detail)
                    val intent = Intent(this, OrderSuccessActivity::class.java)
                    intent.putExtra("orderDetail", jSonOrderDetail)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        ZaloPaySDK.getInstance().onResult(intent)
    }
}