package com.example.kotlinmvptest

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinmvptest.content.ContentActivity
import com.example.kotlinmvptest.model.CommonValues
import com.example.kotlinmvptest.model.LoadMoreAdapter
import com.example.kotlinmvptest.model.service.park.Result
import com.jakewharton.rxbinding4.view.clicks
import java.util.concurrent.TimeUnit

class MainAdapter(activity: MainActivity, parkList: MutableList<Result>) : LoadMoreAdapter() {
    private var parkList = parkList
    private lateinit var context: Context
    private var activity = activity

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
        val holder = h as ViewHolder
        Glide.with(context)
            .load(parkList[position].picUrl)
            .into(holder.imageView_item)
        holder.textView_item_title.text = parkList[position].name
        holder.textView_item_content.text = parkList[position].info
        holder.textView_item_content2.text = parkList[position].category
        holder.constraintLayout_item.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe {
            var intent = Intent(context, ContentActivity::class.java)
            intent.putExtra(CommonValues.RESULT_PARK, parkList[position])
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right)
        }
    }

    override fun _getItemCount(): Int {
        return parkList.size
    }

    fun addItem(start: Int, newItems: MutableList<Result>) {
        parkList.addAll(newItems)
        notifyItemInserted(parkList.size - start)
    }
}