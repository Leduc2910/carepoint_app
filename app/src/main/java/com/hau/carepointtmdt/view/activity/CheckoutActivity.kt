package com.hau.carepointtmdt.view.activity

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.hau.carepointtmdt.R
import com.hau.carepointtmdt.databinding.ActivityCheckoutBinding
import com.hau.carepointtmdt.model.Address
import com.hau.carepointtmdt.model.Delivery
import com.hau.carepointtmdt.model.Medicine
import com.hau.carepointtmdt.model.Order
import com.hau.carepointtmdt.model.Order_Item
import com.hau.carepointtmdt.model.User
import com.hau.carepointtmdt.validation.CustomVerticalDecoration
import com.hau.carepointtmdt.validation.SharedPreferencesManager
import com.hau.carepointtmdt.validation.ValidateData
import com.hau.carepointtmdt.view.adapter.AddressItemRV
import com.hau.carepointtmdt.view.adapter.DeliveryItemRV
import com.hau.carepointtmdt.view.adapter.MedItem3RV
import com.hau.carepointtmdt.viewmodel.CheckOutViewModel
import com.hau.carepointtmdt.viewmodel.CreateAddressState
import com.hau.carepointtmdt.viewmodel.GetAddressByUserIdState
import com.hau.carepointtmdt.viewmodel.GetDeliveryState
import com.hau.carepointtmdt.viewmodel.GetMedicineState
import com.hau.carepointtmdt.viewmodel.GetOrderItemByOrderIdState
import com.hau.carepointtmdt.viewmodel.UpdateAddressState

class CheckoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    private val checkoutViewModel: CheckOutViewModel by viewModels()

    private lateinit var addressItemRV: AddressItemRV
    private lateinit var deliveryItemRV: DeliveryItemRV
    private lateinit var medItem3RV: MedItem3RV

    private var addressLst: List<Address>? = null
    private var deliveryLst: List<Delivery>? = null
    private var orderItemLst: List<Order_Item>? = null
    private var medicineLst: List<Medicine>? = null
    var selectedAddress: Address? = null
    var selectedDelivery: Delivery? = null

    private lateinit var currentUser: User
    private lateinit var order_user: Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edtAddress.setOnFocusChangeListener(View.OnFocusChangeListener { view, b ->
            if (!b) {
                hideKeyboard(view)
            }
        })
        binding.edtAddress2.setOnFocusChangeListener(View.OnFocusChangeListener { view, b ->
            if (!b) {
                hideKeyboard(view)
            }
        })
        binding.edtAddressName.setOnFocusChangeListener(View.OnFocusChangeListener { view, b ->
            if (!b) {
                hideKeyboard(view)
            }
        })
        binding.edtAddressName2.setOnFocusChangeListener(View.OnFocusChangeListener { view, b ->
            if (!b) {
                hideKeyboard(view)
            }
        })
        binding.edtAddressPhone2.setOnFocusChangeListener(View.OnFocusChangeListener { view, b ->
            if (!b) {
                hideKeyboard(view)
            }
        })
        binding.edtAddressPhone.setOnFocusChangeListener(View.OnFocusChangeListener { view, b ->
            if (!b) {
                hideKeyboard(view)
            }
        })

        closeAllModal()
        if (addressLst.isNullOrEmpty()) {
            binding.hoverBtnToAddAddress.visibility = View.VISIBLE
            binding.frameAddress.visibility = View.GONE
        }

        sharedPreferencesManager = SharedPreferencesManager(this)
        currentUser = sharedPreferencesManager.getUser()!!
        order_user = sharedPreferencesManager.getOrder()!!

        if (selectedDelivery == null) {
            binding.txtDeliveryPrice.text = "0"
            binding.txtPrice.text = DecimalFormat("#,###").format(order_user.totalPrice)
        }


        getAddressByUserIdObservers()
        updateAddressObservers()
        createAddressObserver()
        getDeliveryObservers()
        getMedicineObservers()
        getOrderItemObservers()
        checkoutViewModel.getAddressByUserId(currentUser.user_id)
        checkoutViewModel.getDelivery()
        checkoutViewModel.getAllMedicine()

        binding.btnBackCheckout.setOnClickListener {
            finish()
        }

        binding.btnToAddress.setOnClickListener {
            binding.frame.visibility = View.VISIBLE
            binding.frameLstAddress.visibility = View.VISIBLE
        }

        binding.btnCloseLstAddress.setOnClickListener {
            closeAllModal()
            selectedAddress?.let { address ->
                binding.frameAddress.visibility = View.VISIBLE
                binding.hoverBtnToAddAddress.visibility = View.GONE
                binding.txtAddressName.text = selectedAddress!!.user_name
                binding.txtAddressPhone.text = selectedAddress!!.user_phone
                binding.txtAddress.text = selectedAddress!!.address
            }
        }
        binding.btnCloseModal.setOnClickListener {
            closeAllModal()
        }
        binding.btnCloseModal2.setOnClickListener {
            closeAllModal()
        }

        binding.btnToNewAddress.setOnClickListener {
            binding.frameAddAddress.visibility = View.VISIBLE
            binding.frameLstAddress.visibility = View.GONE
            dataInitCreateAddress()
        }
        binding.btnBackLstAddress2.setOnClickListener {
            binding.frameAddAddress.visibility = View.GONE
            binding.frameLstAddress.visibility = View.VISIBLE
        }
        binding.btnToAddAddress.setOnClickListener {
            binding.frame.visibility = View.VISIBLE
            binding.frameAddAddress.visibility = View.VISIBLE
            dataInitCreateAddress()
        }
        binding.btnBackLstAddress.setOnClickListener {
            binding.frameEditAddress.visibility = View.GONE
            binding.frameLstAddress.visibility = View.VISIBLE
        }

        binding.btnAddAddress.setOnClickListener {
            val address_name = binding.edtAddressName2.text
            val address_phone = binding.edtAddressPhone2.text
            val address_address = binding.edtAddress2.text
            val is_default =
                if (binding.swIsDefault2.isChecked || addressLst.isNullOrEmpty()) 1 else 0

            binding.txtErrorAddressName.visibility = View.GONE
            binding.txtErrorAddressPhone.visibility = View.GONE
            binding.txtErrorAddress.visibility = View.GONE

            var check = false

            if (address_name.isEmpty()) {
                binding.txtErrorAddressName.visibility = View.VISIBLE
                binding.txtErrorAddressName.text = "Người nhận hàng không được để trống!"
                check = true
            }

            if (address_phone.isEmpty()) {
                binding.txtErrorAddressPhone.visibility = View.VISIBLE
                binding.txtErrorAddressPhone.text = "Số điện thoại không được để trống!"
                check = true
            } else if (!ValidateData.isValidPhoneNumber(address_phone.toString())) {
                binding.txtErrorAddressPhone.visibility = View.VISIBLE
                binding.txtErrorAddressPhone.text = "Số điện thoại không hợp lệ!"
                check = true
            }

            if (address_address.isEmpty()) {
                binding.txtErrorAddress.visibility = View.VISIBLE
                binding.txtErrorAddress.text = "Địa chỉ không được để trống!"
                check = true
            }
            if (check) {
                return@setOnClickListener
            }
            checkoutViewModel.createAddress(
                currentUser.user_id,
                address_name.toString(), address_phone.toString(),
                address_address.toString(), is_default
            )
        }
        binding.btnCheckout.setOnClickListener {
            if (selectedAddress == null) {
                Toast.makeText(this, "Vui lòng chọn địa chỉ nhận hàng!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (selectedDelivery == null) {
                Toast.makeText(this, "Vui lòng chọn hình thức vận chuyển!", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
        }
        binding.btnBuyMore.setOnClickListener {
            finish()
        }
    }

    fun openEditAddressModal(address: Address) {
        binding.frameEditAddress.visibility = View.VISIBLE
        binding.frameLstAddress.visibility = View.GONE

        binding.edtAddressName.setText(address.user_name)
        binding.edtAddressPhone.setText(address.user_phone)
        binding.edtAddress.setText(address.address)
        binding.swIsDefault.isChecked = address.is_default == 1

        if (addressLst?.size == 1 || address.is_default == 1) {
            binding.swIsDefault.isEnabled = false
        } else {
            binding.swIsDefault.isEnabled = true
        }

        binding.btnSaveAddress.setOnClickListener {
            val address_name = binding.edtAddressName.text
            val address_phone = binding.edtAddressPhone.text
            val address_address = binding.edtAddress.text
            val is_default = if (binding.swIsDefault.isChecked) {
                1
            } else {
                0
            }

            binding.txtErrorAddressName2.visibility = View.GONE
            binding.txtErrorAddressPhone2.visibility = View.GONE
            binding.txtErrorAddress2.visibility = View.GONE

            var check = false

            if (address_name.isEmpty()) {
                binding.txtErrorAddressName2.visibility = View.VISIBLE
                binding.txtErrorAddressName2.text = "Người nhận hàng không được để trống!"
                check = true
            }

            if (address_phone.isEmpty()) {
                binding.txtErrorAddressPhone2.visibility = View.VISIBLE
                binding.txtErrorAddressPhone2.text = "Số điện thoại không được để trống!"
                check = true
            } else if (!ValidateData.isValidPhoneNumber(address_phone.toString())) {
                binding.txtErrorAddressPhone2.visibility = View.VISIBLE
                binding.txtErrorAddressPhone2.text = "Số điện thoại không hợp lệ!"
                check = true
            }

            if (address_address.isEmpty()) {
                binding.txtErrorAddress2.visibility = View.VISIBLE
                binding.txtErrorAddress2.text = "Địa chỉ không được để trống!"
                check = true
            }
            if (check) {
                return@setOnClickListener
            }

            address.user_name = address_name.toString()
            address.user_phone = address_phone.toString()
            address.address = address_address.toString()

            address.is_default = is_default

            checkoutViewModel.updateAddress(address)
        }
    }

    fun getAddressByUserIdObservers() {
        checkoutViewModel.getAddressState.observe(this) { state ->
            when (state) {
                is GetAddressByUserIdState.Loading -> {
                    binding.prgBarLoadAddress.visibility = View.VISIBLE
                }

                is GetAddressByUserIdState.Success -> {
                    binding.prgBarLoadAddress.visibility = View.GONE
                    addressLst = state.addressLst
                    addressLst = addressLst?.sortedByDescending { it.is_default }

                    binding.rvAddressLst.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    while (binding.rvAddressLst.itemDecorationCount > 0) {
                        binding.rvAddressLst.removeItemDecorationAt(0)
                    }
                    binding.rvAddressLst.addItemDecoration(
                        CustomVerticalDecoration(
                            resources.getDimensionPixelSize(
                                R.dimen.spacing_12dp
                            ), resources.getDimensionPixelSize(
                                R.dimen.spacing_0dp
                            )
                        )
                    )
                    addressItemRV = AddressItemRV(this, addressLst!!)
                    binding.rvAddressLst.adapter = addressItemRV

                    if (addressLst.isNullOrEmpty()) {
                        binding.hoverBtnToAddAddress.visibility = View.VISIBLE
                        binding.frameAddress.visibility = View.GONE
                    } else {
                        initializeDefaultAddress()
                    }
                }

                is GetAddressByUserIdState.Error -> {
                    binding.prgBarLoadAddress.visibility = View.GONE
                    Log.d("Get Address Error", state.message)
                }

                else -> {}
            }
        }
    }

    private fun initializeDefaultAddress() {
        selectedAddress = addressLst?.find { it.is_default == 1 }

        selectedAddress?.let { address ->
            binding.frameAddress.visibility = View.VISIBLE
            binding.hoverBtnToAddAddress.visibility = View.GONE

            binding.txtAddressName.text = address.user_name
            binding.txtAddressPhone.text = address.user_phone
            binding.txtAddress.text = address.address
        }
        Log.d("SelectedAddress", selectedAddress.toString())
    }

    fun updateAddressObservers() {
        checkoutViewModel.updateAddressState.observe(this) { state ->
            when (state) {
                is UpdateAddressState.Loading -> {
                }

                is UpdateAddressState.Success -> {
                    Toast.makeText(this, "Cập nhật địa chỉ thành công!", Toast.LENGTH_SHORT).show()
                    binding.frameEditAddress.visibility = View.GONE
                    binding.frameLstAddress.visibility = View.VISIBLE
                    checkoutViewModel.getAddressByUserId(currentUser.user_id)
                    addressItemRV.notifyDataSetChanged()
                }

                is UpdateAddressState.Error -> {
                    Log.d("Update Address Error", state.message)
                }

                else -> {}
            }
        }
    }

    fun createAddressObserver() {
        checkoutViewModel.createAddressState.observe(this) { state ->
            when (state) {
                is CreateAddressState.Loading -> {
                }

                is CreateAddressState.Success -> {
                    Toast.makeText(this, "Thêm địa chỉ mới thành công!", Toast.LENGTH_SHORT).show()
                    binding.frameAddAddress.visibility = View.GONE
                    binding.frameLstAddress.visibility = View.VISIBLE

                    checkoutViewModel.getAddressByUserId(currentUser.user_id)
                    addressItemRV.notifyDataSetChanged()
                }

                is CreateAddressState.Error -> {
                    Log.d("Create Address Error", state.message)
                }

                else -> {}
            }
        }
    }

    fun getDeliveryObservers() {
        checkoutViewModel.getDeliveryState.observe(this) { state ->
            when (state) {
                is GetDeliveryState.Loading -> {
                    binding.prgBarLoadDelivery.visibility = View.VISIBLE
                }

                is GetDeliveryState.Success -> {
                    binding.prgBarLoadDelivery.visibility = View.GONE
                    deliveryLst = state.deliveryLst

                    deliveryItemRV = DeliveryItemRV(this, deliveryLst!!) { delivery ->
                        selectedDelivery = delivery
                        binding.txtDeliveryPrice.text = DecimalFormat("#,###").format(
                            selectedDelivery!!.delivery_price
                        )
                        val price = order_user.totalPrice + selectedDelivery!!.delivery_price
                        binding.txtPrice.text = DecimalFormat("#,###").format(price)
                        Log.d("Selected Deli", selectedDelivery.toString())
                    }
                    binding.rvDelivery.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    while (binding.rvDelivery.itemDecorationCount > 0) {
                        binding.rvDelivery.removeItemDecorationAt(0)
                    }
                    binding.rvDelivery.addItemDecoration(
                        CustomVerticalDecoration(
                            resources.getDimensionPixelSize(
                                R.dimen.spacing_12dp
                            ), resources.getDimensionPixelSize(R.dimen.spacing_0dp)
                        )
                    )
                    binding.rvDelivery.adapter = deliveryItemRV

                }

                is GetDeliveryState.Error -> {
                    binding.prgBarLoadDelivery.visibility = View.GONE
                    Log.d("Get Delivery Error", state.message)
                }

                else -> {}
            }
        }
    }

    fun getOrderItemObservers() {
        checkoutViewModel.getOrderItemState.observe(this) { state ->
            when (state) {
                is GetOrderItemByOrderIdState.Loading -> {
                    binding.prgBarAllMedicine.visibility = View.VISIBLE
                }

                is GetOrderItemByOrderIdState.Success -> {
                    binding.prgBarAllMedicine.visibility = View.GONE

                    orderItemLst = state.orderItemLst.filter {
                        it.isSelected == 2
                    }
                    Log.d("OrderItem", orderItemLst.toString())

                    medItem3RV = MedItem3RV(this, orderItemLst!!, medicineLst!!)
                    binding.rvOrderItem.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    while (binding.rvOrderItem.itemDecorationCount > 0) {
                        binding.rvOrderItem.removeItemDecorationAt(0)
                    }
                    binding.rvOrderItem.addItemDecoration(
                        CustomVerticalDecoration(
                            resources.getDimensionPixelSize(
                                R.dimen.spacing_12dp
                            ), resources.getDimensionPixelSize(R.dimen.spacing_0dp)
                        )
                    )
                    binding.rvOrderItem.adapter = medItem3RV

                    binding.txtTotalPrice.text =
                        DecimalFormat("#,###").format(order_user.totalPrice)
                }

                is GetOrderItemByOrderIdState.Error -> {
                    binding.prgBarAllMedicine.visibility = View.GONE
                    Log.d("Get Delivery Error", state.message)
                }

                else -> {}
            }
        }
    }

    fun getMedicineObservers() {
        checkoutViewModel.getAllMedicine.observe(this) { state ->
            when (state) {
                is GetMedicineState.Loading -> {
                    binding.prgBarAllMedicine.visibility = View.VISIBLE
                }

                is GetMedicineState.Success -> {
                    binding.prgBarAllMedicine.visibility = View.GONE
                    medicineLst = state.medicineLst
                    checkoutViewModel.getOrderItemByOrderId(order_user.order_id)

                }

                is GetMedicineState.Error -> {
                    binding.prgBarAllMedicine.visibility = View.GONE
                    Log.d("Get Delivery Error", state.message)
                }

                else -> {}
            }
        }
    }

    private fun closeAllModal() {
        binding.frame.visibility = View.GONE
        binding.frameAddAddress.visibility = View.GONE
        binding.frameEditAddress.visibility = View.GONE
        binding.frameLstAddress.visibility = View.GONE
    }

    fun dataInitCreateAddress() {
        binding.edtAddressName2.setText("")
        binding.edtAddressPhone2.setText("")
        binding.edtAddress2.setText("")
        binding.swIsDefault2.isSelected = false
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


}