package com.vincent.adidassample.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vincent.adidassample.R
import com.vincent.adidassample.model.NewsItem
import com.vincent.adidassample.utils.AppConst
import kotlinx.android.synthetic.main.item_news.view.*

class NewsItemAdapter(
    private val list: List<NewsItem>,
    private val context: Context
) : RecyclerView.Adapter<NewsItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: NewsItem) {
            Glide.with(itemView).load(item.picUrl).into(itemView.imageView)
            itemView.textViewName.text = item.title
            itemView.textViewDate.text = item.ctime
            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable(AppConst.ARG_NEWS_ITEM, item)
                it.findNavController()
                    .navigate(R.id.action_navigation_home_to_navigation_news_detail, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bindView(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
