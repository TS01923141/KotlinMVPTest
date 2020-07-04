package com.example.kotlinmvptest.model

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmvptest.R

import java.util.Locale

abstract class LoadMoreAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_FOOTER = -1
    //目前加載狀態, 默認為加載完成
    /**
     * 設置上拉加載狀態
     *
     * @param loadState 0.正在加載 1.加載完成 2.加載到底
     */
    var loadState = 2
        set(loadState) {
            Log.d(TAG, "setLoadState: current load state: " + this.loadState)
            Log.d(TAG, "setLoadState: new load state: $loadState")
            field = loadState
            notifyDataSetChanged()
        }
    //正在加載
    val LOADING = 1
    //加載完成
    val LOADING_COMPLETE = 2
    //加載到底
    val LOADING_END = 3

    private val loading_zh = "正在讀取中..."
    private val load_end_zh = "沒有更多了"
    private val loading_en = "loading..."
    private val load_end_en = "no more"

    override fun getItemViewType(position: Int): Int {
        //最後一個item設值為FooterView
        return if (position + 1 == itemCount) {
            TYPE_FOOTER
        } else {
            position
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //進行判斷顯示類型, 來創建返回不同的View
        if (viewType == TYPE_FOOTER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_footer, parent, false)
            return FootViewHolder(view)
        } else {
            return _onCreateViewHolder(parent, viewType)
        }
    }


    abstract fun _onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FootViewHolder) {
            Log.d(TAG, "onBindViewHolder: loadState: " + this.loadState)
            val loading = if (Locale.getDefault().language == Locale.TAIWAN.language)
                loading_zh
            else
                loading_en
            val load_end = if (Locale.getDefault().language == Locale.TAIWAN.language)
                load_end_zh
            else
                load_end_en
            when (this.loadState) {
                LOADING -> {
                    holder.textView_footer_loading.text = loading
                    holder.textView_footer_loading.visibility = View.VISIBLE
                }
                LOADING_COMPLETE -> holder.textView_footer_loading.visibility = View.INVISIBLE
                LOADING_END -> {
                    holder.textView_footer_loading.text = load_end
                    holder.textView_footer_loading.visibility = View.VISIBLE
                }
                else -> {
                }
            }
        } else {
            _onBindViewHolder(holder, position)
        }
    }

    abstract fun _onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    override fun getItemCount(): Int {
        val size = _getItemCount()
        Log.d(TAG, "getItemCount: size: $size")
        return if (size > 0) {
            size + 1
        } else {
            0
        }
    }

    abstract fun _getItemCount(): Int

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            val gridManager = manager as GridLayoutManager?
            gridManager!!.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    //如果目前是footer的位置, 那麼該item佔據兩個單位格, 正常情況下佔據一個單位格
                    return if (getItemViewType(position) == TYPE_FOOTER) gridManager.getSpanCount() else 1
                }
            })
        }
    }

    private inner class FootViewHolder


    @SuppressLint("WrongViewCast", "CutPasteId")
    constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var textView_footer_loading: TextView

        init {
            textView_footer_loading = itemView.findViewById(R.id.textView_footer_loading)
        }
    }

    companion object {
        private val TAG = "LoadMoreWrapper"
    }
}
