package com.hau.carepointtmdt.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.hau.carepointtmdt.model.Order
import com.hau.carepointtmdt.R
import com.hau.carepointtmdt.validation.CustomVerticalDecoration
import com.hau.carepointtmdt.databinding.ActivityPurchaseOrderBinding
import com.hau.carepointtmdt.model.Medicine
import com.hau.carepointtmdt.model.Order_Detail
import com.hau.carepointtmdt.model.Order_Item
import com.hau.carepointtmdt.model.User
import com.hau.carepointtmdt.validation.SharedPreferencesManager
import com.hau.carepointtmdt.view.adapter.PurchaseOrderRV
import com.hau.carepointtmdt.viewmodel.GetAllOrderItemState
import com.hau.carepointtmdt.viewmodel.GetMedicineState
import com.hau.carepointtmdt.viewmodel.GetOrderDetailByUserIdState
import com.hau.carepointtmdt.viewmodel.GetOrderItemByOrderIdState
import com.hau.carepointtmdt.viewmodel.PurchaseOrderViewModel

class ActivityPurchaseOrder : AppCompatActivity() {
    private lateinit var binding: ActivityPurchaseOrderBinding

    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    private val purchaseOrderViewModel: PurchaseOrderViewModel by viewModels()

    private lateinit var purchaseOrderRV: PurchaseOrderRV

    private var orderDetailLst: List<Order_Detail>? = null
    private var orderItemLst: List<Order_Item>? = null
    private var medicineLst: List<Medicine>? = null

    private lateinit var currentUser: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPurchaseOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesManager = SharedPreferencesManager(this)
        currentUser = sharedPreferencesManager.getUser()!!

        addTabsToTabLayout()
        getOrderDetailObservers()
        getOrderItemObservers()
        getAllMedicineObservers()
        purchaseOrderViewModel.getOrderDetailByUserId(currentUser.user_id)

        binding.btnBackToProfile.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("navigateTo", "ProfileFragment")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        binding.tblOrderStatus.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> filterOrdersByStatus("Tất cả")
                    1 -> filterOrdersByStatus("Chờ lấy hàng")
                    2 -> filterOrdersByStatus("Đang giao hàng")
                    3 -> filterOrdersByStatus("Giao thành công")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }


    fun getAllMedicineObservers() {
        purchaseOrderViewModel.getAllMedicine.observe(this) { state ->
            when (state) {
                is GetMedicineState.Error -> {
                    Log.d("Get All medicine error", state.message)
                }

                GetMedicineState.Loading -> {

                }

                is GetMedicineState.Success -> {
                    medicineLst = state.medicineLst
                    Log.d("MedicineLst", medicineLst.toString())
                    purchaseOrderViewModel.getAllOrderItem()
                }
            }
        }
    }

    fun getOrderDetailObservers() {
        purchaseOrderViewModel.getOrderDetailState.observe(this) { state ->
            when (state) {
                is GetOrderDetailByUserIdState.Error -> {
                    Log.d("Get Order Detail error", state.message)
                }

                GetOrderDetailByUserIdState.Loading -> {
                }

                is GetOrderDetailByUserIdState.Success -> {
                    orderDetailLst = state.orderDetailLst.sortedByDescending { it.orderDetail_id }
                    Log.d("orderDetailLst", orderDetailLst.toString())
                    purchaseOrderViewModel.getAllMedicine()
                }
            }
        }
    }

    fun getOrderItemObservers() {
        purchaseOrderViewModel.getAlLOrderItemState.observe(this) { state ->
            when (state) {
                is GetAllOrderItemState.Error -> {
                    Log.d("Get Order Item error", state.message)
                }

                GetAllOrderItemState.Loading -> {
                }

                is GetAllOrderItemState.Success -> {
                    orderItemLst = state.orderItemLst
                    val orderLayoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    binding.rcvOrderLst.layoutManager = orderLayoutManager
                    while (binding.rcvOrderLst.itemDecorationCount > 0) {
                        binding.rcvOrderLst.removeItemDecorationAt(0)
                    }
                    binding.rcvOrderLst.addItemDecoration(
                        CustomVerticalDecoration(
                            resources.getDimensionPixelSize(
                                R.dimen.spacing_16dp
                            ), resources.getDimensionPixelSize(R.dimen.spacing_24dp)
                        )
                    )
                    purchaseOrderRV = PurchaseOrderRV(
                        this,
                        medicineLst!!, orderItemLst!!, orderDetailLst!!
                    )
                    binding.rcvOrderLst.adapter = purchaseOrderRV
                }
            }

        }
    }

    private fun addTabsToTabLayout() {
        val tabTitles = listOf("Tất cả", "Chờ lấy hàng", "Đang giao hàng", "Giao thành công")
        for (title in tabTitles) {
            val tab = binding.tblOrderStatus.newTab()
            tab.text = title
            binding.tblOrderStatus.addTab(tab)
        }

        for (i in 0 until binding.tblOrderStatus.tabCount) {
            val tabView = (binding.tblOrderStatus.getChildAt(0) as ViewGroup).getChildAt(i)
            val layoutParams = tabView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.marginEnd = 20
            tabView.layoutParams = layoutParams
        }
    }
    private fun filterOrdersByStatus(status: String) {
        val filteredOrderDetailLst = when (status) {
            "Tất cả" -> orderDetailLst?.sortedByDescending { it.orderDetail_id }
            "Chờ lấy hàng" -> orderDetailLst?.filter { it.status == 1 }?.sortedByDescending { it.orderDetail_id }
            "Đang giao hàng" -> orderDetailLst?.filter { it.status == 2 }?.sortedByDescending { it.orderDetail_id }
            "Giao thành công" -> orderDetailLst?.filter { it.status == 3 }?.sortedByDescending { it.orderDetail_id }
            else -> orderDetailLst
        }

        // Cập nhật RecyclerView
        purchaseOrderRV.updateOrderList(filteredOrderDetailLst)
    }
}

