package com.hau.carepointtmdt.view.activity

import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
import com.hau.carepointtmdt.validation.CustomVerticalDecoration
import com.hau.carepointtmdt.validation.SharedPreferencesManager
import com.hau.carepointtmdt.view.adapter.CartItemRV
import com.hau.carepointtmdt.viewmodel.CartViewModel
import com.hau.carepointtmdt.viewmodel.DeleteOrderItemState
import com.hau.carepointtmdt.viewmodel.GetMedicineState
import com.hau.carepointtmdt.viewmodel.GetOrderItemByOrderIdState
import com.hau.carepointtmdt.viewmodel.UpdateOrderItemState
import com.hau.carepointtmdt.viewmodel.UpdateOrderUserState

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding

    private val cartViewModel: CartViewModel by viewModels()

    private lateinit var currentUser: User
    private lateinit var order_user: Order

    private var orderItemLst: List<Order_Item>? = null
    private var medicineLst: List<Medicine>? = null

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
        updateOrderItemObservers()
        updateOrderUser()
        deleteOrderItemObservers()
        cartViewModel.getAllMedicine()

        var isEnableDelete = false
        binding.btnDeleteMed.setOnClickListener {
            isEnableDelete = !isEnableDelete
            if (isEnableDelete) {
                binding.btnOrder.visibility = View.GONE
                binding.btnDelete.visibility = View.VISIBLE
            } else {
                binding.btnOrder.visibility = View.VISIBLE
                binding.btnDelete.visibility = View.GONE
            }
        }

        binding.btnDelete.setOnClickListener {
            if  (orderItemLst?.isNotEmpty() == true) {
                val itemsToDelete = orderItemLst!!.filter { it.isSelected == 2 }

                for (item in itemsToDelete) {
                    cartViewModel.deleteOrderItem(item.orderItem_id)
                }
            }
        }
        binding.btnOrder.setOnClickListener {
            if (order_user.totalPrice != 0 ) {
                val intent = Intent(this, CheckoutActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Chọn sản phẩm để tiến hành thanh toán!", Toast.LENGTH_SHORT).show()
            }

        }
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
                    cartItemRV = medicineLst?.let {
                        CartItemRV(
                            this,
                            orderItemLst!!.toMutableList(), it, cartViewModel
                        )
                    }!!
                    binding.rvCartItem.adapter = cartItemRV

                    val isAnyItemSelected = orderItemLst!!.any { it.isSelected == 2 }
                    updateEnableButton(isAnyItemSelected)
                }

                is GetOrderItemByOrderIdState.Error -> {
                    binding.prgBarProductOrder.visibility = View.GONE

                    Log.d("orderItemLst Error", state.message)
                }

                else -> {}
            }
        }
    }

    private fun updateOrderItemObservers() {
        cartViewModel.updateOrderItem.observe(this) { state ->
            when (state) {
                is UpdateOrderItemState.Loading -> {

                }

                is UpdateOrderItemState.Success -> {
                    var totalPrice = 0
                    val newOrderItemLst = orderItemLst!!.filter { it.isSelected == 2 }

                    for (orderItem: Order_Item in newOrderItemLst) {
                        totalPrice += orderItem.totalPrice
                    }

                    order_user.totalPrice = totalPrice
                    cartViewModel.updateOrderUser(order_user)

                    updateEnableButton(newOrderItemLst.isNotEmpty())
                }

                is UpdateOrderItemState.Error -> {
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

    private fun deleteOrderItemObservers() {
        cartViewModel.deleteOrderItem.observe(this) { state ->
            when (state) {
                is DeleteOrderItemState.Success -> {
                    orderItemLst = orderItemLst!!.filter { it.isSelected != 2 }
                    cartItemRV.updateData(orderItemLst!!)
                    var totalPrice = 0
                    val newOrderItemLst = orderItemLst!!.filter { it.isSelected == 2 }

                    for (orderItem: Order_Item in newOrderItemLst) {
                        totalPrice += orderItem.totalPrice
                    }

                    order_user.totalPrice = totalPrice
                    cartViewModel.updateOrderUser(order_user)
                    binding.btnOrder.visibility = View.VISIBLE
                    binding.btnDelete.visibility = View.GONE
                    updateEnableButton(newOrderItemLst.isNotEmpty())
                }

                else -> {}
            }
        }
    }

    private fun updateEnableButton(isSelected: Boolean) {
        if (isSelected) {
            binding.btnOrder.setTextColor(resources.getColor(R.color.text_invert))
            binding.btnOrder.setBackgroundResource(R.drawable.default_primary_btn)
            binding.btnOrder.isEnabled = true
        } else {
            binding.btnOrder.setTextColor(resources.getColor(R.color.spe_text_lightGray))
            binding.btnOrder.setBackgroundResource(R.drawable.disbale_primary_btn)
            binding.btnOrder.isEnabled = false
        }
    }

}