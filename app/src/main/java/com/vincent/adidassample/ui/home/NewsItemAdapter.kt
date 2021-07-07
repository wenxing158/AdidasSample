package com.vincent.adidassample.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import com.vincent.profile_ui.utils.LoginConst
import kotlinx.android.synthetic.main.footer_text_view.view.*
import kotlinx.android.synthetic.main.item_news.view.*

class NewsItemAdapter(
    private val list: MutableList<NewsItem>,
    val adidasDao: AdidasDAO,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val NORMAL_TYPE = 0
        const val FOOTER_TYPE = 1
    }

    interface StarViewListener {
        fun onStarClick(saved: Boolean)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(item: NewsItem, adidasDao: AdidasDAO, starViewListener: StarViewListener) {
            Glide.with(itemView).load(item.picUrl).into(itemView.imageView)
            itemView.textViewName.text = item.title
            itemView.textViewDate.text = item.ctime
            itemView.setOnClickListener {
                if (verifyLogin()) {
                    val bundle = Bundle()
                    bundle.putParcelable(AppConst.ARG_NEWS_ITEM, item)
                    it.findNavController()
                        .navigate(R.id.action_navigation_home_to_navigation_news_detail, bundle)
                } else {
                    android.app.AlertDialog.Builder(itemView.context)
                        .setMessage("Go to login")
                        .setPositiveButton("Yes") { dialogInterface, i ->
                            it.findNavController()
                                .navigate(R.id.action_navigation_home_to_login_activity)
                        }
                        .setNegativeButton("No") { dialogInterface, i ->

                        }
                        .create().show()
                }
            }
            if (item.saved) {
                itemView.imageView_star.setImageResource(R.mipmap.star)
            } else {
                itemView.imageView_star.setImageResource(R.mipmap.star_grey)
            }
            itemView.imageView_star.setOnClickListener {
                if (verifyLogin()) {
                    if (item.saved) {
                        itemView.imageView_star.setImageResource(R.mipmap.star_grey)
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
                        starViewListener.onStarClick(false)
                    } else {
                        itemView.imageView_star.setImageResource(R.mipmap.star)
                        Thread {
                            adidasDao.add(
                                News(
                                    item.id,
                                    item.ctime,
                                    item.title,
                                    item.description,
                                    item.source,
                                    item.picUrl,
                                    item.url,
                                    true
                                )
                            )
                        }.start()
                        starViewListener.onStarClick(true)
                    }

                }
            }



        }

        private fun verifyLogin(): Boolean {
            val sharedPreferences: SharedPreferences = itemView.context
                .getSharedPreferences(LoginConst.ARG_SP, Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean(LoginConst.ARG_LOGIN_DATA_IS_LOGIN, false)
        }
    }

    class FootViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(list: List<NewsItem>) {
            if (list.isNotEmpty()) {
                itemView.text_view_footer.visibility = View.VISIBLE
                itemView.text_view_footer.text = "正在刷新"
            } else {
                itemView.text_view_footer.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            FOOTER_TYPE -> return FootViewHolder(
                LayoutInflater.from(context).inflate(R.layout.footer_text_view, parent, false)
            )
            else -> return ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_news, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val item = list[position]
            holder.bindView(item, adidasDao, object : StarViewListener {
                override fun onStarClick(saved: Boolean) {
                    item.saved = saved
                    list[position] = item
                    Log.i("list[position]", "item" + position + item.saved)
                    Log.i("list[position]", "list[position]" + position + list[position].saved)
                }
            })
        } else if (holder is FootViewHolder) {
            holder.bindView(list)
        }
    }

    fun updateListView(newData: List<NewsItem>) {
        newData?.let { list.addAll(newData) }
        notifyItemRangeChanged(list.size - newData.size, list.size - 1)
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return (position == itemCount - 1)?.let { if (it) FOOTER_TYPE else NORMAL_TYPE }
    }
}
