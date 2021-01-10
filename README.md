# MVVM 연습 (ViewModel, DataBinding)


### [1] 리사이클러뷰 뷰모델로 적용하는 법 (더미)

#### [방법1]

1. xml 바인딩으로 변경
2. 리사이클러뷰에 들어갈 Data 파일 만들기
3. 어댑터에 뷰홀더 합쳐서 코드작성
4. 뷰모델파일만들어서 _Recyclerview이렇게 관찰하는 거 넣고 fun RVCancel() 으로 더미 넣기

    → init()으로 넣어도 된다. 이렇게 하면 메인파일에서 굳이 함수 불러오지 않아도 적용됨.

5. 메인파일에서 뷰모델, 데이터바인딩 연결하고 private fun()으로 함수 만들어서 그 안에 myReportViewModel.RVCancel() 이런식으로 뷰모델의 함수를 사용

    옵저버를 이용하여 관찰한대로 뷰에 뿌려주기 (더미데이터 옵저버에서 들어가게 됨)

```kotlin
myReportViewModel.recyclerListData.observe(viewLifecycleOwner, Observer {
            cancelAdapter.data = it   // 더미데이터 들어감
            cancelAdapter.notifyDataSetChanged()
        })
```
<br>

#### [방법2]   

---
