package com.example.kotlinmvptest.content.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import com.example.kotlinmvptest.R
import com.example.kotlinmvptest.model.CommonValues
import com.example.kotlinmvptest.model.base.BaseFragment
import com.example.kotlinmvptest.model.service.plant.Result
import kotlinx.android.synthetic.main.fragment_detail.*
import java.lang.StringBuilder

class DetailFragment : BaseFragment<DetailContract.View, DetailContract.Presenter>(), DetailContract.View {
    companion object {
        public fun getInstance(): DetailFragment {
            val args = Bundle()
            val fragment = DetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initPresenter(): DetailContract.Presenter {
        if(presenter == null){
            presenter = DetailPresenter()
        }
        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments == null) activity!!.onBackPressed()
        presenter.setPlantResult(arguments!!.getParcelable<Result>(CommonValues.RESULT_PLANT)!!)

        imageView_detail_back.setOnClickListener { activity!!.onBackPressed() }
        textView_detail_title.text = presenter.getPlantResult().nameCh

        Glide.with(context!!)
            .load(presenter.getPlantResult().pic01Url)
            .into(imageView_detail_cover)

        var stringBuilder = StringBuilder()
        stringBuilder.append(presenter.getPlantResult().nameCh)
        stringBuilder.append("\n")
        stringBuilder.append(presenter.getPlantResult().nameEn)
        stringBuilder.append("\n")
        stringBuilder.append("\n")
        stringBuilder.append("別名")
        stringBuilder.append(presenter.getPlantResult().alsoKnown)
        stringBuilder.append("\n")
        stringBuilder.append("\n")
        stringBuilder.append("簡介")
        stringBuilder.append("\n")
        stringBuilder.append(presenter.getPlantResult().brief)
        stringBuilder.append("\n")
        stringBuilder.append("\n")
        stringBuilder.append("辨認方法")
        stringBuilder.append("\n")
        stringBuilder.append(presenter.getPlantResult().feature)
        stringBuilder.append("\n")
        stringBuilder.append("\n")
        stringBuilder.append("功能性")
        stringBuilder.append("\n")
        stringBuilder.append(presenter.getPlantResult().functionNAppLocation)
        stringBuilder.append("\n")
        stringBuilder.append("\n")
        stringBuilder.append("最後更新：")
        stringBuilder.append(presenter.getPlantResult().update)

        textView_detail_content.text = stringBuilder.toString()
    }
}
