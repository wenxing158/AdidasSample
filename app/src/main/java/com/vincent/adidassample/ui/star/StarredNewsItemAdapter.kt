package com.vincent.adidassample.ui.star

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vincent.adidassample.R
import com.vincent.adidassample.database.dao.AdidasDAO
import com.vincent.adidassample.model.News
import com.vincent.adidassample.model.NewsItem
import com.vincent.adidassample.utils.AppConst
import kotlinx.android.synthetic.main.item_news.view.*

class StarredNewsItemAdapter(
    private val list: MutableList<News>,
    val adidasDao: AdidasDAO,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface StarViewListener {
        fun onStarClick()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(item: News, starViewListener: StarViewListener) {
            Glide.with(itemView).load(item.picUrl).into(itemView.imageView)
            itemView.textViewName.text = item.title
            itemView.textViewDate.text = item.ctime
            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable(AppConst.ARG_NEWS_ITEM, NewsItem(
                    item.id,
                    item.ctime,
                    item.title,
                    item.description,
                    item.source,
                    item.picUrl,
                    item.url,
                    item.saved
                ))
                it.findNavController()
                    .navigate(R.id.action_navigation_star_to_navigation_news_detail, bundle)
            }
            if (item.saved) {
                itemView.imageView_star.setImageResource(R.mipmap.star)
            } else {
                itemView.imageView_star.setImageResource(R.mipmap.star_grey)
            }
            itemView.imageView_star.setOnClickListener {
                starViewListener.onStarClick()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        val holder = holder as ViewHolder
        holder.bindView(item, object : StarViewListener {
            override fun onStarClick() {
                Thread {
                    adidasDao.delete(
                        News(
                            item.id,
                            item.ctime,
                            item.title,
                            item.description,
                            item.source,
                            item.picUrl,
                            item.url,
                            !item.saved
                        )
                    )
                }.start()
                list.removeAt(position)
            }
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
