package com.example.kotlinmvptest.content

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import autodispose2.autoDispose
import com.bumptech.glide.Glide
import com.example.kotlinmvptest.R
import com.example.kotlinmvptest.databinding.ActivityContentBinding
import com.example.kotlinmvptest.model.CommonValues
import com.example.kotlinmvptest.model.EndlessRecyclerOnScrollListener
import com.example.kotlinmvptest.model.service.park.Result
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

import kotlinx.android.synthetic.main.activity_content.*
import java.util.concurrent.TimeUnit

//        "E_Pic_URL": "http://www.zoo.gov.tw/iTAP/05_Exhibit/11_KoalaHouse.jpg",
//        "E_Info": "無尾熊為最具代表性的有袋目動物之一。來自澳洲「庫倫賓野生動物保護區」，代表城市友誼並肩負保育、教育使命的無尾熊們，自引進以來一直都是眾所矚目的焦點。無尾熊館設有多個獨立空間，每間屋頂都有天窗可以讓陽光照射進來。在適當天氣時，無尾熊偶爾也會到戶外場地活動，享受溫暖的陽光。",
//        "E_Category": "室內區",
//        "E_Name": "無尾熊館",
//        "E_URL": "http://www.zoo.gov.tw/introduce/gq.aspx?tid=7"
class ContentActivity : AppCompatActivity() {
    private lateinit var viewModel: ContentViewModel
    private lateinit var binding: ActivityContentBinding
    private var loadingDialog: AlertDialog? = null
    private lateinit var contentAdapter: ContentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_content)
        viewModel = ViewModelProvider(this).get(ContentViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_content)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        loadingDialog = AlertDialog.Builder(this)
            .setTitle("請稍候")
            .setMessage("讀取中...")
            .setCancelable(false)
            .show()

        //正常要在這邊或MainAdapter傳出前判斷一下是否null，這邊懶得做處理直接!!
        viewModel.parkResult = intent.getParcelableExtra<Result>(CommonValues.RESULT_PARK)!!

        imageView_content_back.setOnClickListener { onBackPressed() }
        textView_content_title.text = viewModel.parkResult.name
        Glide.with(this)
            .load(viewModel.parkResult.picUrl)
            .into(imageView_content_intro)
        textView_content_intro.text = viewModel.parkResult.info
        textView_content_category.text = viewModel.parkResult.category
        textView_content_web.clicks().throttleFirst(3, TimeUnit.SECONDS)
            .autoDispose(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
            .subscribe {
            var builder = CustomTabsIntent.Builder()
            var customTabIntent = builder.build()
            customTabIntent.intent.setPackage("com.android.chrome")
            customTabIntent.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            try {
                customTabIntent.launchUrl(this, Uri.parse(viewModel.parkResult.url))
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }

        loadPlantInfo()

        recyclerVIew_content_plant.addOnScrollListener(object : EndlessRecyclerOnScrollListener(){
            override fun onLoadMore() {
                if (contentAdapter.loadState == contentAdapter.LOADING_END) return
                contentAdapter.loadState = contentAdapter.LOADING
                loadPlantInfo()
            }
        })
    }

    fun loadPlantInfo() {
        var start = viewModel.plantList.size
        viewModel.getPlantInfo().observeOn(AndroidSchedulers.mainThread())
            .autoDispose(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
            .subscribe({ plantList ->
                if (!this::contentAdapter.isInitialized) {
                    contentAdapter = ContentAdapter(this, plantList)
                    val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                    recyclerVIew_content_plant.layoutManager = linearLayoutManager
                    recyclerVIew_content_plant.adapter = contentAdapter
                    constraintLayout_content_content.visibility = View.VISIBLE
                } else {
                    if (start == plantList.size) {
                        contentAdapter.loadState = contentAdapter.LOADING_END
                        return@subscribe
                    }
                    contentAdapter.addItem(start, plantList)
                    contentAdapter.loadState = contentAdapter.LOADING_COMPLETE
                }
                if (loadingDialog != null && loadingDialog!!.isShowing) loadingDialog!!.dismiss()
            }, { it.printStackTrace() })
    }

}
