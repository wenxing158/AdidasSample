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
import com.vincent.adidassample.databinding.FragmentNewsDetailBinding
import com.vincent.adidassample.model.NewsItem
import com.vincent.adidassample.utils.AppConst
import kotlinx.android.synthetic.main.fragment_news_detail.view.*

class NewsDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainActivity = activity as MainActivity
        mainActivity.setTitle("News Title")
        val rootView = DataBindingUtil.inflate<FragmentNewsDetailBinding>(
            inflater,
            R.layout.fragment_news_detail,
            null,
            false
        )
        val newsItem = arguments?.getParcelable<NewsItem>(AppConst.ARG_NEWS_ITEM)
        rootView.newsItem = newsItem
        Glide.with(rootView.root).load(newsItem!!.picUrl).into(rootView.imageviewDetail)
        rootView.root.textview_read_more.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(AppConst.ARG_DETAIL_WEBVIEW_URL, newsItem.url)
            it.findNavController().navigate(R.id.action_navigation_detail_to_navigation_news_detail_webview, bundle)
        }
        return rootView.root
    }

}