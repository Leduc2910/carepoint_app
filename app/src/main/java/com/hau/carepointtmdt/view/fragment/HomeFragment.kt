package com.hau.carepointtmdt.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.hau.carepointtmdt.model.HomeBlogItem
import com.hau.carepointtmdt.model.HomeDoctorItem
import com.hau.carepointtmdt.model.HomeFuncItem
import com.hau.carepointtmdt.model.HomeSpecialtyItem
import com.hau.carepointtmdt.model.HomeQuickTestItem
import com.hau.carepointtmdt.R
import com.hau.carepointtmdt.validation.CustomHorizontalDecoration
import com.hau.carepointtmdt.validation.CustomVerticalDecoration
import com.hau.carepointtmdt.databinding.FragmentHomeBinding
import com.hau.carepointtmdt.model.Address
import com.hau.carepointtmdt.model.User
import com.hau.carepointtmdt.validation.GridSpacingItemDecoration
import com.hau.carepointtmdt.validation.SharedPreferencesManager
import com.hau.carepointtmdt.view.activity.MedicineHomeActivity
import com.hau.carepointtmdt.view.adapter.HomeBlogItemRV
import com.hau.carepointtmdt.view.adapter.HomeDoctorItemRV
import com.hau.carepointtmdt.view.adapter.HomeFunctionItemRecycleView
import com.hau.carepointtmdt.view.adapter.HomeQuickTestRV
import com.hau.carepointtmdt.view.adapter.HomeSpecialtyItemRV
import com.hau.carepointtmdt.view.adapter.MedItemRV
import com.hau.carepointtmdt.viewmodel.GetAddressByUserIdState
import com.hau.carepointtmdt.viewmodel.GetAllCatalogueState
import com.hau.carepointtmdt.viewmodel.GetMedicineState
import com.hau.carepointtmdt.viewmodel.GetProductByCatalogueIdState
import com.hau.carepointtmdt.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var currentUser: User

    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var homeFunctionAdapter: HomeFunctionItemRecycleView
    private lateinit var homeSpecialtyAdapter: HomeSpecialtyItemRV
    private lateinit var homeDoctorAdapter: HomeDoctorItemRV
    private lateinit var homeQuickTestRV: HomeQuickTestRV
    private lateinit var homeBlogAdapter: HomeBlogItemRV
    private lateinit var homeMedIAdapter: MedItemRV

    private var addressLst: List<Address>? = null
    private lateinit var homeFuncItemLst: ArrayList<HomeFuncItem>
    private lateinit var specialtyItemLst: ArrayList<HomeSpecialtyItem>
    private lateinit var homeDoctorItemLst: ArrayList<HomeDoctorItem>
    private lateinit var homeQuickTestLst: ArrayList<HomeQuickTestItem>
    private lateinit var homeBlogItemLst: ArrayList<HomeBlogItem>

    var space: Int = 0
    var space2: Int = 0
    var endSpace: Int = 0

    lateinit var imgFuncId: Array<Int>
    lateinit var titleFuncText: Array<String>
    lateinit var describeFuncText: Array<String>

    lateinit var imgSpeId: Array<Int>
    lateinit var describeSpeText: Array<String>

    lateinit var imgDoctorItemID: Array<Int>
    lateinit var txtDocSpe: Array<String>
    lateinit var txtDocName: Array<String>

    lateinit var imgTestID: Array<Int>
    lateinit var txtTest: Array<String>

    lateinit var imgBlogId: Array<Int>
    lateinit var txtBlogCate: Array<String>
    lateinit var txtBlogTitle: Array<String>
    lateinit var txtBlogDes: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferencesManager = SharedPreferencesManager(requireContext())
        currentUser = sharedPreferencesManager.getUser()!!


        dataInitialize()

        val funcItemLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFuncItem.layoutManager = funcItemLayoutManager
        binding.rvFuncItem.addItemDecoration(CustomHorizontalDecoration(space, endSpace))
        homeFunctionAdapter = HomeFunctionItemRecycleView(requireContext(), homeFuncItemLst)
        binding.rvFuncItem.adapter = homeFunctionAdapter

        val specialtyItemLayoutManager = GridLayoutManager(requireContext(), 4)
        binding.rvSpecialtyItem.layoutManager = specialtyItemLayoutManager
        homeSpecialtyAdapter = HomeSpecialtyItemRV(requireContext(), specialtyItemLst)
        binding.rvSpecialtyItem.adapter = homeSpecialtyAdapter

        val doctorItemLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvDoctorHome.layoutManager = doctorItemLayoutManager
        binding.rvDoctorHome.addItemDecoration(CustomHorizontalDecoration(space, endSpace))
        homeDoctorAdapter = HomeDoctorItemRV(requireContext(), homeDoctorItemLst)
        binding.rvDoctorHome.adapter = homeDoctorAdapter

        val quickTestLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvTestHome.layoutManager = quickTestLayoutManager
        binding.rvTestHome.addItemDecoration(CustomHorizontalDecoration(space2, endSpace))
        homeQuickTestRV = HomeQuickTestRV(requireContext(), homeQuickTestLst)
        binding.rvTestHome.adapter = homeQuickTestRV

        val blogItemLayoutManager = LinearLayoutManager(requireContext())
        binding.rvBlogHome.layoutManager = blogItemLayoutManager
        binding.rvBlogHome.addItemDecoration(CustomVerticalDecoration(endSpace, endSpace))
        homeBlogAdapter = HomeBlogItemRV(requireContext(), homeBlogItemLst)
        binding.rvBlogHome.adapter = homeBlogAdapter

        setupObserversCatalogueLst()
        setupObserversMedicineLst()
        getAddressObservers()
        homeViewModel.getAllCatalogue()
        homeViewModel.getAddressByUserId(currentUser.user_id)

        binding.btnGoToMedicineHome.setOnClickListener {
            val intent = Intent(requireContext(), MedicineHomeActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setupObserversCatalogueLst() {
        homeViewModel.getAllCatalogueState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is GetAllCatalogueState.Loading -> {
                    binding.tblCateMed.visibility = View.GONE
                    binding.tblCateMed.removeAllTabs()
                }

                is GetAllCatalogueState.Success -> {
                    binding.tblCateMed.visibility = View.VISIBLE
                    binding.tblCateMed.removeAllTabs()

                    val homeMedCatalogueLst = state.catalogueLst
                    Log.d("Catalogue lst", homeMedCatalogueLst.toString())
                    homeMedCatalogueLst.forEach { category ->
                        val tab = binding.tblCateMed.newTab()
                        tab.text = category.pCatalogue_name
                        tab.tag = category.pCatalogue_id
                        binding.tblCateMed.addTab(tab)
                    }

                    for (i in 0 until binding.tblCateMed.tabCount) {
                        val tab = (binding.tblCateMed.getChildAt(0) as ViewGroup).getChildAt(i)
                        val layoutParams = tab.layoutParams as ViewGroup.MarginLayoutParams
                        layoutParams.marginEnd = 20
                        tab.layoutParams = layoutParams
                    }

                    homeViewModel.getProductByCatalogueId(homeMedCatalogueLst[0].pCatalogue_id)

                    binding.tblCateMed.addOnTabSelectedListener(object :
                        TabLayout.OnTabSelectedListener {
                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            val catalogueId = tab?.tag as? Int
                            if (catalogueId != null) {
                                homeViewModel.getProductByCatalogueId(catalogueId)
                            }
                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {
                        }

                        override fun onTabReselected(tab: TabLayout.Tab?) {
                        }
                    })
                }

                is GetAllCatalogueState.Error -> {
                    binding.tblCateMed.visibility = View.GONE
                    binding.tblCateMed.removeAllTabs()
                    Log.d("Home Catalogue Error", state.message)
                }

            }
        }
    }

    private fun setupObserversMedicineLst() {
        homeViewModel.getProductByCatalogueIdState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is GetProductByCatalogueIdState.Loading -> {
                    binding.prgBarLoadHomeMed.visibility = View.VISIBLE
                }

                is GetProductByCatalogueIdState.Success -> {
                    binding.prgBarLoadHomeMed.visibility = View.GONE
                    var homeMedItemLst = state.medicineLst?.take(8)

                    homeMedItemLst = homeMedItemLst?.shuffled()

                    val medItemLayoutManager = GridLayoutManager(requireContext(), 2)
                    binding.rvMedHome.layoutManager = medItemLayoutManager
                    while (binding.rvMedHome.itemDecorationCount > 0) {
                        binding.rvMedHome.removeItemDecorationAt(0)
                    }
                    binding.rvMedHome.addItemDecoration(GridSpacingItemDecoration(2, space))
                    homeMedIAdapter = homeMedItemLst?.let { MedItemRV(requireContext(), it) }!!
                    binding.rvMedHome.adapter = homeMedIAdapter
                }

                is GetProductByCatalogueIdState.Error -> {
                    binding.prgBarLoadHomeMed.visibility = View.GONE
                    Log.d("Home Medicine Error", state.message)
                }
            }
        }
    }

    private fun getAddressObservers() {
        homeViewModel.getAddressState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is GetAddressByUserIdState.Error -> {
                    Log.d("Get Address Error", state.message)
                    binding.frameHoverAddressHome.visibility = View.GONE
                }

                GetAddressByUserIdState.Loading -> {

                }

                is GetAddressByUserIdState.Success -> {
                    addressLst = state.addressLst

                    if (!addressLst.isNullOrEmpty()) {
                        binding.frameHoverAddressHome.visibility = View.VISIBLE
                        for (address in addressLst!!) {
                            if (address.is_default == 1) {
                                binding.txtHomeUserAddress.text = address.address
                                break
                            }
                        }
                    }
                }
            }
        }
    }

    private fun dataInitialize() {

        binding.txtTitleUserName.text = "Xin chào " + currentUser.name

        space2 = resources.getDimensionPixelSize(R.dimen.spacing_8dp)
        space = resources.getDimensionPixelSize(R.dimen.spacing_16dp)
        endSpace = resources.getDimensionPixelSize(R.dimen.spacing_20dp)

        homeFuncItemLst = arrayListOf<HomeFuncItem>()

        imgFuncId = arrayOf(
            R.drawable.ic_1,
            R.drawable.ic_2,
            R.drawable.ic_3
        )

        titleFuncText = arrayOf(
            "Đặt lịch\nkhám bệnh",
            "Giao đơn thuốc tận nơi",
            "Nhắc nhở\nuống thuốc"
        )

        describeFuncText = arrayOf(
            "Đặt lịch khám với bác sĩ phù hợp",
            "Đặt và nhận thuốc tại nhà",
            "Chúng tôi sẽ nhắc bạn uống thuốc"
        )

        for (i in imgFuncId.indices) {
            val homeFuncItem =
                HomeFuncItem(imgFuncId[i], titleFuncText[i], describeFuncText[i])
            homeFuncItemLst.add(homeFuncItem)
        }

        specialtyItemLst = arrayListOf<HomeSpecialtyItem>()

        imgSpeId = arrayOf(
            R.drawable.ic_spe_1,
            R.drawable.ic_spe_2,
            R.drawable.ic_spe_3,
            R.drawable.ic_spe_4,
            R.drawable.ic_spe_5,
            R.drawable.ic_spe_6,
            R.drawable.ic_spe_7,
            R.drawable.ic_spe_8
        )

        describeSpeText = arrayOf(
            "Tổng quát",
            "Nha sĩ",
            "Tim mạch",
            "Thần kinh",
            "Vắc xin",
            "Phẫu thuật",
            "Dị ứng",
            "Thú y"
        )

        for (i in imgSpeId.indices) {
            val specialtyItem = HomeSpecialtyItem(imgSpeId[i], describeSpeText[i])
            specialtyItemLst.add(specialtyItem)
        }

        homeDoctorItemLst = arrayListOf<HomeDoctorItem>()

        imgDoctorItemID = arrayOf(
            R.drawable.img_doctor_1,
            R.drawable.img_doctor_2
        )

        txtDocSpe = arrayOf(
            "Bác sĩ tim mạch",
            "Bác sĩ thú y"
        )

        txtDocName = arrayOf(
            "Ts. Nguyễn Hoàng Phúc",
            "Ths. Trần Huy Chiến"
        )

        for (i in imgDoctorItemID.indices) {
            val homeDoctorItem =
                HomeDoctorItem(imgDoctorItemID[i], txtDocSpe[i], txtDocName[i])
            homeDoctorItemLst.add(homeDoctorItem)
        }

        homeQuickTestLst = arrayListOf<HomeQuickTestItem>()

        imgTestID = arrayOf(
            R.drawable.img_test_1,
            R.drawable.img_test_2,
            R.drawable.img_test_3
        )

        txtTest = arrayOf(
            "Chẩn đoán\nbệnh Alzheimer",
            "Chẩn đoán\nbệnh hen suyễn",
            "Chẩn đoán\nbệnh tim mạch"
        )


        for (i in imgTestID.indices) {
            val homeQuickTestItem = HomeQuickTestItem(imgTestID[i], txtTest[i])
            homeQuickTestLst.add(homeQuickTestItem)
        }

        homeBlogItemLst = arrayListOf<HomeBlogItem>()

        imgBlogId = arrayOf(
            R.drawable.img_blog_1,
            R.drawable.img_blog_2,
            R.drawable.img_blog_2,
            R.drawable.img_blog_2,
            R.drawable.img_blog_3
        )

        txtBlogCate = arrayOf(
            "Mới",
            "Mới",
            "Sức khỏe",
            "Sức khỏe",
            "Sắc đẹp"
        )

        txtBlogTitle = arrayOf(
            "Thuốc mới được FDA phê duyệt",
            "Thuốc mới được FDA phê duyệt",
            "Thuốc mới được FDA phê duyệt",
            "Thuốc mới được FDA phê duyệt",
            "Thuốc mới được FDA phê duyệt"
        )

        txtBlogDes = arrayOf(
            "Việc đạt được sự chấp thuận của FDA cho một loại thuốc mới là một thách thức lớn, và con đường để đạt được điều đó...",
            "Việc đạt được sự chấp thuận của FDA cho một loại thuốc mới là một thách thức lớn, và con đường để đạt được điều đó...",
            "Việc đạt được sự chấp thuận của FDA cho một loại thuốc mới là một thách thức lớn, và con đường để đạt được điều đó...",
            "Việc đạt được sự chấp thuận của FDA cho một loại thuốc mới là một thách thức lớn, và con đường để đạt được điều đó...",
            "Việc đạt được sự chấp thuận của FDA cho một loại thuốc mới là một thách thức lớn, và con đường để đạt được điều đó..."
        )

        for (i in imgBlogId.indices) {
            val homeBlogItem =
                HomeBlogItem(imgBlogId[i], txtBlogCate[i], txtBlogTitle[i], txtBlogDes[i])
            homeBlogItemLst.add(homeBlogItem)
        }


    }
}