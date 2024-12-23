package com.hau.carepointtmdt.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hau.carepointtmdt.adapter.PurchaseOrderRV
import com.hau.carepointtmdt.model.Order
import com.hau.carepointtmdt.model.OrderStatus
import com.hau.carepointtmdt.R
import com.hau.carepointtmdt.validation.CustomVerticalDecoration
import com.hau.carepointtmdt.databinding.ActivityPurchaseOrderBinding

class ActivityPurchaseOrder : AppCompatActivity() {
    private lateinit var binding: ActivityPurchaseOrderBinding

    private lateinit var purchaseOrderRV: PurchaseOrderRV

    private lateinit var orderStatusLst: ArrayList<OrderStatus>
    private lateinit var orderLst: ArrayList<Order>

    lateinit var orderStatusName: Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPurchaseOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataInitialize()

        orderStatusLst.forEach { status ->
            val tab = binding.tblOrderStatus.newTab()
            tab.text = status.statusName
            binding.tblOrderStatus.addTab(tab)
        }

        for (i in 0 until binding.tblOrderStatus.tabCount) {
            val tab = (binding.tblOrderStatus.getChildAt(0) as ViewGroup).getChildAt(i)
            val layoutParams = tab.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.marginEnd = 20
            tab.layoutParams = layoutParams
        }

        val orderLayoutManager = LinearLayoutManager(this)
        binding.rcvOrderLst.layoutManager = orderLayoutManager
        binding.rcvOrderLst.addItemDecoration(CustomVerticalDecoration(resources.getDimensionPixelSize(R.dimen.spacing_16dp), resources.getDimensionPixelSize(R.dimen.spacing_24dp)))
        purchaseOrderRV = PurchaseOrderRV(this, orderLst)
        binding.rcvOrderLst.adapter = purchaseOrderRV
    }

    fun dataInitialize() {

        orderStatusLst = arrayListOf<OrderStatus>()

        orderStatusName = arrayOf(
            "Tất cả",
            "Chờ xác nhận",
            "Chờ giao hàng",
            "Đã giao"
        )

        for (i in orderStatusName.indices) {
            val orderStatus = OrderStatus(orderStatusName[i])
            orderStatusLst.add(orderStatus)
        }

        orderLst = arrayListOf(
            Order(
                orderId = "ORD001",
                orderProductImg = R.drawable.img_product,
                orderProductName = "Augmentin (Amoxicillin/Clavulanate)",
                orderProductPrice = "100.000 đ",
                orderProductQuantity = "x1",
                orderProductUnit = "hộp",
                orderTotalProduct = 1,
                orderTotalPrice = 100000,
                orderStatusId = 1,
                orderQuantity = 1
            ),
            Order(
                orderId = "ORD002",
                orderProductImg = R.drawable.img_product,
                orderProductName = "Paracetamol 500mg",
                orderProductPrice = "50.000 đ",
                orderProductQuantity = "x2",
                orderProductUnit = "hộp",
                orderTotalProduct = 2,
                orderTotalPrice = 100000,
                orderStatusId = 2,
                orderQuantity = 2
            ),
            Order(
                orderId = "ORD003",
                orderProductImg = R.drawable.img_product,
                orderProductName = "Vitamin C",
                orderProductPrice = "30.000 đ",
                orderProductQuantity = "x3",
                orderProductUnit = "chai",
                orderTotalProduct = 3,
                orderTotalPrice = 90000,
                orderStatusId = 3,
                orderQuantity = 3
            ),
            Order(
                orderId = "ORD004",
                orderProductImg = R.drawable.img_product,
                orderProductName = "Vitamin A",
                orderProductPrice = "50.000 đ",
                orderProductQuantity = "x1",
                orderProductUnit = "gói",
                orderTotalProduct = 3,
                orderTotalPrice = 90000,
                orderStatusId = 3,
                orderQuantity = 3
            )
        )
    }
}