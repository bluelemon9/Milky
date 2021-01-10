package com.example.milky

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.milky.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    // 뷰모델 선언 (ktx 이용)
    // -> private val 변수명 : 해당ViewModel by viewModels() (In Activity)
    // -> private val 변수명 : 해당ViewModel by activityViewModels() (In Fragment)
    private val mainViewModel: MainViewModel by viewModels()

    //리사이클러뷰 적용
    lateinit var menuAdapter: MenuAdapter
    val datas = mutableListOf<MenuData>()   // 서버


    // View는 ViewModel을 관찰
    // ViewModel에 있는 여러값들을 이용하여 뷰를 그려주는 역할을 할 뿐
    // 값의 변경이나 어떠한 처리와 같은 모든일들은 ViewModel이 수행

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 데이터 바인딩 코드로 변경
        // ->Activity이름Binding = DataBindingUitl.setContentView(this, R.layout.레이아웃이름) eg) ActivityNickNameBinding
        // ->Fragment이름Binding = DataBindingUitl.setContentView(this, R.layout.레이아웃이름) eg) FragmentNickNameBinding
        // setContentView(R.layout.activity_main)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        // layout에서 <data>에 선언한 viewModel에 연결
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this

        setDetailData(binding)
        universeAddCancel(binding)
        showDetailTime(binding)
        setTextBold(binding)
    }

    private fun setDetailData(binding: ActivityMainBinding) {
//        var datas: MutableList<MenuData> = mutableListOf<MenuData>()
//        val datass = MutableLiveData<MutableList<MenuData>>()

        // 어댑터에 context 객체를 파라미터로 전달 (더미)
        menuAdapter = MenuAdapter(this)
//        menuAdapter = MenuAdapter(view.context)   // 더미 프래그먼트일때
//        menuAdapter= MenuAdapter(view.context, datas) // 서버 프래그먼트

        binding.rvDetailMenu.adapter = menuAdapter
        binding.rvDetailMenu.layoutManager = LinearLayoutManager(this)
        mainViewModel.makeApiCall()

        mainViewModel.recyclerListData.observe(this, Observer {
//            if (it != null) {
                menuAdapter.data = it   // 더미데이터 들어감
                menuAdapter.notifyDataSetChanged()
//            } else {
//                Toast.makeText(this, "데이터없음", Toast.LENGTH_SHORT).show()
//            }
        })

//        mainViewModel.recyclerListData.observe(this, Observer { recyclerListData ->
//                datass.value = recyclerListData
//                Log.d("test", "${recyclerListData}")
//                Log.e("dataValue1", datass.value!!.get(0).toString())
//                Log.e("dataValue2", datass.value!!.get(1).toString())
//                Log.e("dataValue3", datass.value!!.get(2).toString())
//            }
    }


    var num = 0
    // 유니버스 추가,취소 -> 뷰모델에 있는 isSelected값을 관찰하여 유니버스 카운트 증감
    private fun universeAddCancel(binding: ActivityMainBinding) {
        mainViewModel.isSelected.observe(this, Observer { isSelected ->
            isSelected?.let {
                if (isSelected) {
                    binding.tvDetailUniverseCount.text = num.toString()
                    num = "${binding.tvDetailUniverseCount.text}".toInt() - 1
                    binding.btnDetailUniverse.setBackgroundResource(R.drawable.ic_up)
                } else {
//                    Log.d("바인딩2", "${binding.tvDetailUniverseCount.text}")
                    binding.tvDetailUniverseCount.text = num.toString()
                    num = "${binding.tvDetailUniverseCount.text}".toInt() + 1
                    binding.btnDetailUniverse.setBackgroundResource(R.drawable.ic_back)
//                    Log.d("바인딩결과2", "${num}")
//                    Log.d("바인딩결과22", "${binding.tvDetailUniverseCount.text}")
                }
                binding.tvDetailShowcount.text =
                    "${binding.tvDetailUniverseCount.text}" + "명의 밀키들이 유니버스에 추가했어요"
            }
        })
    }

    // 운영시간 보기
    private fun showDetailTime(binding: ActivityMainBinding) {
        mainViewModel.isSelected2.observe(this, Observer { isSelected2 ->
            isSelected2?.let {
                if (isSelected2) {
                    if (binding.tvDetailTime2.visibility == View.GONE) {
                        binding.btnDetailTime.setBackgroundResource(R.drawable.ic_up)
                        binding.tvDetailTime2.visibility = View.VISIBLE
                    } else if (binding.tvDetailTime2.visibility == View.VISIBLE) {
                        binding.btnDetailTime.setBackgroundResource(R.drawable.ic_back)
                        binding.tvDetailTime2.visibility = View.GONE
                    }
                }
            }
        })
    }

    // 특정 글자 굵게
    private fun setTextBold(binding: ActivityMainBinding) {
        val content = binding.tvDetailMsg.text.toString()
        val spannableString = SpannableString(content)

        val word = "카페 방문 시 한번 더 확인하시기를 추천드립니다."
        val start = content.indexOf(word)
        val end = start + word.length

        spannableString.setSpan(
            StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvDetailMsg.text = spannableString
    }
}
