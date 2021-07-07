package com.vincent.adidassample.ui.newsdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.vincent.adidassample.MainActivity
import com.vincent.adidassample.R
import com.vincent.adidassample.database.dao.AdidasDAO
import com.vincent.adidassample.databinding.FragmentNewsDetailBinding
import com.vincent.adidassample.model.News
import com.vincent.adidassample.model.NewsItem
import com.vincent.adidassample.utils.AppConst
import kotlinx.android.synthetic.main.fragment_news_detail.*
import kotlinx.android.synthetic.main.fragment_news_detail.view.*
import org.koin.android.ext.android.inject

class NewsDetailFragment : Fragment() {

    private val adidasDao : AdidasDAO by inject()
    private lateinit var item: NewsItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainActivity = activity as MainActivity
        mainActivity.setTitle("News Title")
        mainActivity.displayBottom(View.GONE)
        val rootView = DataBindingUtil.inflate<FragmentNewsDetailBinding>(
            inflater,
            R.layout.fragment_news_detail,
            null,
            false
        )
        item = arguments?.getParcelable<NewsItem>(AppConst.ARG_NEWS_ITEM)!!
        rootView.newsItem = item
        Glide.with(rootView.root).load(item!!.picUrl).into(rootView.imageviewDetail)
        rootView.root.textview_read_more.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(AppConst.ARG_DETAIL_WEBVIEW_URL, item.url)
            it.findNavController().navigate(R.id.action_navigation_detail_to_navigation_news_detail_webview, bundle)
        }

        return rootView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (item.saved) {
            star_button_float.setImageResource(R.mipmap.star)
        } else {
            star_button_float.setImageResource(R.mipmap.star_grey)
        }
        star_button_float.setOnClickListener {
            if (item.saved) {
                star_button_float.setImageResource(R.mipmap.star_grey)
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
            } else {
                star_button_float.setImageResource(R.mipmap.star)
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
            }
        }

    }

}