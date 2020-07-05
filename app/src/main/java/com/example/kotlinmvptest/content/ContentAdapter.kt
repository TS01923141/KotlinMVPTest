package com.example.kotlinmvptest.content

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinmvptest.R
import com.example.kotlinmvptest.content.detail.DetailFragment
import com.example.kotlinmvptest.model.CommonValues
import com.example.kotlinmvptest.model.LoadMoreAdapter
import com.example.kotlinmvptest.model.service.plant.Result
import com.jakewharton.rxbinding4.view.clicks
import java.util.concurrent.TimeUnit

class ContentAdapter(activity: ContentActivity, plantList: MutableList<Result>) : LoadMoreAdapter() {
    private var plantList = plantList
    private lateinit var context: Context
    private val activity = activity

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val constraintLayout_item: ConstraintLayout = itemView.findViewById(R.id.constraintLayout_item)
        val imageView_item: ImageView = itemView.findViewById(R.id.imageView_item)
        val textView_item_title: TextView = itemView.findViewById(R.id.textView_item_title)
        val textView_item_content: TextView = itemView.findViewById(R.id.textView_item_content)
        val textView_item_content2: TextView = itemView.findViewById(R.id.textView_item_content2)
    }
    override fun _onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun _onBindViewHolder(h: RecyclerView.ViewHolder, position: Int) {
        val holder = h as ContentAdapter.ViewHolder
        Glide.with(context)
            .load(plantList[position].pic01Url)
            .into(holder.imageView_item)
        holder.textView_item_title.text = plantList[position].nameCh
        holder.textView_item_content.text = plantList[position].alsoKnown
        holder.textView_item_content2.visibility = View.GONE
        holder.constraintLayout_item.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe {
            //replace fragment
            var fragment = DetailFragment.getInstance()
            var bundle = Bundle()
            bundle.putParcelable(CommonValues.RESULT_PLANT, plantList[position])
            fragment.arguments = bundle
            val fragManager = activity.supportFragmentManager
            val fragTrans = fragManager.beginTransaction()
            fragTrans.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right)
            fragTrans.replace(R.id.constraintLayout_content_fragmentContent, fragment)
            fragTrans.addToBackStack(null)
            fragTrans.commitAllowingStateLoss()
        }
    }

    override fun _getItemCount(): Int {
        return plantList.size
    }

    fun addItem(start: Int, newItems: MutableList<Result>) {
        plantList.addAll(newItems)
        notifyItemInserted(plantList.size - start)
    }
}