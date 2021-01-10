package com.example.milky

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

class MainViewModel : ViewModel() {
    // MutableLiveData<String>("") 초기화
    // 양방향 데이터바인딩
    // -> viewModel에 있는 데이터를 layout에 binding, layout에서 입력되는 정보를 viewModel에 반영
    // -> Binding 시킬때 "@={viewModel.nickname}"(양방향)

    val universecount = MutableLiveData<String>("")
    val tv_detail_showcount = MutableLiveData<String>("")
    val PlaceList = MutableLiveData<MutableList<MenuData>>()


    // View에서 관찰할 값(Observe)
    // true-> 유니버스 파랗게, 카운트 하나 증가, false-> 유니버스 회색, 카운트 하나 감소 layout 코드
    private val _isSelected = MutableLiveData<Boolean>(false)
    val isSelected: LiveData<Boolean>
        get() = _isSelected

    private val _isSelected2 = MutableLiveData<Boolean>(false)
    val isSelected2: LiveData<Boolean>
        get() = _isSelected2

//    val _recyclerListData = MutableLiveData<MutableList<MenuData>>()
    private val _recyclerListData = MutableLiveData<MutableList<MenuData>>()
    val recyclerListData: LiveData<MutableList<MenuData>>
        get() = _recyclerListData

//    // true-> visible , false-> gone
//    private val _isValid = MutableLiveData<Boolean>(false)
//    val isValid : LiveData<Boolean>
//        get() = _isValid


    fun clickCheck() {
        _isSelected.value = !_isSelected.value!!
    }

    fun clickCheck2() {
        _isSelected2.value = !_isSelected2.value!!
    }

//    init {
//        val menuDatas = mutableListOf<MenuData>()
//
//        menuDatas.add(MenuData("라떼라떼", "4,000원", "두유"))
//        menuDatas.add(MenuData("모카모카", "5,000원", "두유22"))
//        menuDatas.add(MenuData("유기농녹차맛두유", "5,000원", "두유33"))
//        _recyclerListData.postValue(menuDatas)
//    }

    // 리사이클러뷰
    fun makeApiCall() {
        val menuDatas = mutableListOf<MenuData>()

        menuDatas.add(MenuData("라떼라떼", "4,000원", "두유"))
        menuDatas.add(MenuData("모카모카", "5,000원", "두유22"))
        menuDatas.add(MenuData("유기농녹차맛두유", "5,000원", "두유33"))
        _recyclerListData.postValue(menuDatas)
    }
}