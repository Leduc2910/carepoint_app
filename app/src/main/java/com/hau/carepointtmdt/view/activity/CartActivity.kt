package com.hau.carepointtmdt.view.activity

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hau.carepointtmdt.R
import com.hau.carepointtmdt.databinding.ActivityCartBinding
import com.hau.carepointtmdt.model.Medicine
import com.hau.carepointtmdt.model.Order
import com.hau.carepointtmdt.model.Order_Item
import com.hau.carepointtmdt.model.User
import com.hau.carepointtmdt.network.response.UpdateOrderUserResponse
import com.hau.carepointtmdt.validation.CustomVerticalDecoration
import com.hau.carepointtmdt.validation.SharedPreferencesManager
import com.hau.carepointtmdt.view.adapter.CartItemRV
import com.hau.carepointtmdt.viewmodel.CartViewModel
import com.hau.carepointtmdt.viewmodel.GetMedicineState
import com.hau.carepointtmdt.viewmodel.GetOrderItemByOrderIdState
import com.hau.carepointtmdt.viewmodel.SelectOrderItemState
import com.hau.carepointtmdt.viewmodel.UpdateOrderUserState

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding

    private val cartViewModel: CartViewModel by viewModels()

    private lateinit var currentUser: User
    private lateinit var order_user: Order

    private lateinit var orderItemLst: List<Order_Item>
    private lateinit var medicineLst: List<Medicine>

    lateinit var sharedPreferencesManager: SharedPreferencesManager

    private lateinit var cartItemRV: CartItemRV


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesManager = SharedPreferencesManager(this)


        currentUser = sharedPreferencesManager.getUser()!!
        order_user = sharedPreferencesManager.getOrder()!!

        binding.btnBack.setOnClickListener {
            finish()
        }

        if (order_user.totalPrice != 0) {
            binding.btnOrder.text =
                "Mua hàng (" + DecimalFormat("#,###").format(order_user.totalPrice) + " đ)"
        } else {
            binding.btnOrder.text = "Mua hàng"
        }

        getAllMedicineObservers()
        getOrderItemByOrderIdObservers()
        selectOrderItemObservers()
        updateOrderUser()
        cartViewModel.getAllMedicine()
    }

    private fun getAllMedicineObservers() {
        cartViewModel.getAllMedicine.observe(this) { state ->
            when (state) {
                is GetMedicineState.Loading -> {
                }

                is GetMedicineState.Success -> {
                    medicineLst = state.medicineLst
                    cartViewModel.getOrderItemByOrderId(order_user.order_id)
                }

                is GetMedicineState.Error -> {
                    Log.d("MedicineLst Error", state.message)
                }

                else -> {}
            }
        }
    }

    private fun getOrderItemByOrderIdObservers() {
        cartViewModel.orderItemByOrderId.observe(this) { state ->
            when (state) {
                is GetOrderItemByOrderIdState.Loading -> {
                    binding.prgBarProductOrder.visibility = View.VISIBLE
                }

                is GetOrderItemByOrderIdState.Success -> {
                    binding.prgBarProductOrder.visibility = View.GONE
                    orderItemLst = state.orderItemLst

                    binding.rvCartItem.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    binding.rvCartItem.addItemDecoration(
                        CustomVerticalDecoration(
                            resources.getDimensionPixelSize(
                                R.dimen.spacing_16dp
                            ), resources.getDimensionPixelSize(
                                R.dimen.spacing_0dp
                            )
                        )
                    )
                    cartItemRV = CartItemRV(this, orderItemLst, medicineLst, cartViewModel)
                    binding.rvCartItem.adapter = cartItemRV
                }

                is GetOrderItemByOrderIdState.Error -> {
                    binding.prgBarProductOrder.visibility = View.GONE

                    Log.d("orderItemLst Error", state.message)
                }

                else -> {}
            }
        }
    }

    private fun selectOrderItemObservers() {
        cartViewModel.selectOrderItem.observe(this) { state ->
            when (state) {
                is SelectOrderItemState.Loading -> {

                }

                is SelectOrderItemState.Success -> {
                    var totalPrice = 0
                    val newOrderItemLst = orderItemLst.filter { it.isSelected == 2 }
                    for (orderItem: Order_Item in newOrderItemLst) {
                        totalPrice += orderItem.totalPrice
                    }
                    order_user.totalPrice = totalPrice
                    cartViewModel.updateOrderUser(
                        order_user.order_id,
                        order_user.user_id,
                        order_user.totalPrice,
                        order_user.order_status
                    )
                }

                is SelectOrderItemState.Error -> {
                    Log.d("Select OrderItem Error", state.message)
                }

                else -> {}
            }
        }
    }

    private fun updateOrderUser() {
        cartViewModel.updateOrderUser.observe(this) { state ->
            when (state) {
                is UpdateOrderUserState.Loading -> {

                }
                is UpdateOrderUserState.Success -> {
                    order_user = state.order_user
                    sharedPreferencesManager.saveOrder(state.order_user)
                    if (order_user.totalPrice != 0) {
                        binding.btnOrder.text =
                            "Mua hàng (" + DecimalFormat("#,###").format(order_user.totalPrice) + " đ)"
                    } else {
                        binding.btnOrder.text = "Mua hàng"
                    }
                }
                is UpdateOrderUserState.Error -> {

                }
            }
        }
    }
}