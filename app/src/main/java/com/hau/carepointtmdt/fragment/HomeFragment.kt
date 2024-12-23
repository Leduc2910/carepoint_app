package com.hau.carepointtmdt.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hau.carepointtmdt.adapter.HomeBlogItemRV
import com.hau.carepointtmdt.adapter.HomeDoctorItemRV
import com.hau.carepointtmdt.adapter.HomeFunctionItemRecycleView
import com.hau.carepointtmdt.adapter.HomeMedItemRV
import com.hau.carepointtmdt.adapter.HomeQuickTestRV
import com.hau.carepointtmdt.adapter.HomeSpecialtyItemRV
import com.hau.carepointtmdt.model.HomeBlogItem
import com.hau.carepointtmdt.model.HomeDoctorItem
import com.hau.carepointtmdt.model.HomeFuncItem
import com.hau.carepointtmdt.model.HomeMedCategory
import com.hau.carepointtmdt.model.HomeMedItem
import com.hau.carepointtmdt.model.HomeSpecialtyItem
import com.hau.carepointtmdt.model.HomeQuickTestItem
import com.hau.carepointtmdt.R
import com.hau.carepointtmdt.validation.CustomHorizontalDecoration
import com.hau.carepointtmdt.validation.CustomVerticalDecoration
import com.hau.carepointtmdt.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var homeFunctionAdapter: HomeFunctionItemRecycleView
    private lateinit var homeSpecialtyAdapter: HomeSpecialtyItemRV
    private lateinit var homeDoctorAdapter: HomeDoctorItemRV
    private lateinit var homeQuickTestRV: HomeQuickTestRV
    private lateinit var homeBlogAdapter: HomeBlogItemRV
    private lateinit var homeMedIAdapter: HomeMedItemRV

    private lateinit var homeFuncItemLst: ArrayList<HomeFuncItem>
    private lateinit var specialtyItemLst: ArrayList<HomeSpecialtyItem>
    private lateinit var homeDoctorItemLst: ArrayList<HomeDoctorItem>
    private lateinit var homeQuickTestLst: ArrayList<HomeQuickTestItem>
    private lateinit var homeBlogItemLst: ArrayList<HomeBlogItem>
    private lateinit var homeMedItemLst: ArrayList<HomeMedItem>
    private lateinit var homeMedCategoryLst: ArrayList<HomeMedCategory>

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

    lateinit var imgMedID: Array<Int>
    lateinit var txtMedName: Array<String>
    lateinit var txtMedPrice: Array<String>
    lateinit var txtMedUnit: Array<String>

    lateinit var cateMedName : Array<String>

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

        homeMedCategoryLst.forEach { category ->
            val tab = binding.tblCateMed.newTab()
            tab.text = category.cateName
            binding.tblCateMed.addTab(tab)
        }

        for (i in 0 until binding.tblCateMed.tabCount) {
            val tab = (binding.tblCateMed.getChildAt(0) as ViewGroup).getChildAt(i)
            val layoutParams = tab.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.marginEnd = 20
            tab.layoutParams = layoutParams
        }

        val medItemLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMedHome.layoutManager = medItemLayoutManager
        homeMedIAdapter = HomeMedItemRV(requireContext(), homeMedItemLst)
        binding.rvMedHome.adapter = homeMedIAdapter

    }

    private fun dataInitialize() {

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
            val homeFuncItem = HomeFuncItem(imgFuncId[i], titleFuncText[i], describeFuncText[i])
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
            val homeDoctorItem = HomeDoctorItem(imgDoctorItemID[i], txtDocSpe[i], txtDocName[i])
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

        homeMedItemLst = arrayListOf<HomeMedItem>()

        imgMedID = arrayOf(
            R.drawable.img_med_1,
            R.drawable.img_med_2,
            R.drawable.img_med_3,
            R.drawable.img_med_4,
            R.drawable.img_med_5,
            R.drawable.img_med_6
        )

        txtMedName = arrayOf(
            "Acetaminophen (Paracetamol)",
            "Prozac (Fluoxetine)",
            "Plavix (Clopidogrel)",
            "Zoloft (Sertraline)",
            "Augmentin (Amoxicillin/Clavulanate)",
            "APrednisone",
        )

        txtMedPrice = arrayOf(
            "11.000 ₫",
            "12.000 ₫",
            "13.000 ₫",
            "14.000 ₫",
            "15.000 ₫",
            "16.000 ₫"
        )

        txtMedUnit = arrayOf(
            "Hộp",
            "Lọ",
            "Hộp",
            "Vỉ",
            "Lọ",
            "Vỉ",
        )

        for (i in imgMedID.indices) {
            val homeMedItem = HomeMedItem(imgMedID[i], txtMedName[i], txtMedPrice[i], txtMedUnit[i])
            homeMedItemLst.add(homeMedItem)
        }

        homeMedCategoryLst = arrayListOf<HomeMedCategory>()

        cateMedName = arrayOf(
            "Tất cả",
            "Chăm sóc cá nhân",
            "Làm đẹp",
            "Bệnh lý"
        )

        for (i in cateMedName.indices) {
            val homeMedCategory = HomeMedCategory(cateMedName[i])
            homeMedCategoryLst.add(homeMedCategory)
        }
    }
}