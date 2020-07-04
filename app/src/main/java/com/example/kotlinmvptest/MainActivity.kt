package com.example.kotlinmvptest

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import autodispose2.autoDispose
import com.example.kotlinmvptest.model.base.BaseActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

//導入RxJava3/OkHttp4/AutoDispose/Retrofit2/Glide4/CCT
//建構BaseContent/View/Presenter/BaseFragment
//完成RetrofitService
//完成自動下拉Adapter

//完成MainActivity/MainPresenter
//  API->(管區簡介)
//  完成item
//  完成adapter
//  Activity套用

//完成ContentActivity/ContentPresenter
//  API->(植物資料)
//  ScrollView + RecyclerView
//    -> 完成CustomScrollView(繼承ScrollView，不被RecyclerView影響的ScrollView)
//    -> 完成Adapter，item跟Main一樣
//  完成XML
//    -> 上面Custom<ImageView + TextView>
//    -> 下面RecyclerView
//  完成Activity
//    -> onBackPressed
//    -> 網頁開啟用CCT
//完成DetailFragment
//  ScrollView + TextView
//有空再加動畫

class MainActivity : BaseActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {

    private lateinit var mainAdapter: MainAdapter
    private var loadingDialog: AlertDialog? = null

    override fun initPresenter(): MainContract.Presenter {
        if (this.presenter == null) {
            presenter = MainPresenter()
        }
        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadingDialog = AlertDialog.Builder(this)
            .setTitle("請稍候")
            .setMessage("讀取中...")
            .setCancelable(false)
            .show()

        presenter.getParkInfo().observeOn(AndroidSchedulers.mainThread())
            .autoDispose(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
            .subscribe({ parkList ->
                if (!this::mainAdapter.isInitialized) {
                    mainAdapter = MainAdapter(parkList)
                    val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                    recyclerView_main.layoutManager = linearLayoutManager
                    recyclerView_main.adapter = mainAdapter
                } else {
                    mainAdapter.addItem(parkList)
                }
                if (loadingDialog != null && loadingDialog!!.isShowing) loadingDialog!!.dismiss()
            }, { it.printStackTrace() })
    }

}
