package com.example.kotlinmvptest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinmvptest.model.LoadMoreAdapter
import com.example.kotlinmvptest.model.service.park.Result
import com.jakewharton.rxbinding4.view.clicks
import java.util.concurrent.TimeUnit

class MainAdapter(parkList: MutableList<Result>) : LoadMoreAdapter() {
    private var parkList = parkList
    private lateinit var context: Context

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
            //            context.startActivity()
        }
    }

    override fun _getItemCount(): Int {
        return parkList.size
    }

    fun addItem(newItems: MutableList<Result>) {
        var start = parkList.size
        parkList.addAll(newItems)
//        notifyItemChanged(start, parkList.size - 1)
        notifyItemInserted(parkList.size - start)
    }
}