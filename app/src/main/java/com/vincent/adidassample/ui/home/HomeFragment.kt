package com.vincent.adidassample.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vincent.adidassample.MainActivity
import com.vincent.adidassample.R
import com.vincent.adidassample.database.dao.AdidasDAO
import com.vincent.adidassample.model.News
import com.vincent.adidassample.model.NewsItem
import com.vincent.profile_ui.utils.LoginConst
import com.vincent.profile_ui.utils.Status
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

//TODO Can extract with StarFragment in the future to create a BaseHomeFragment
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private val adidasDAO : AdidasDAO by inject()
    private var isLogin = false
    private lateinit var username: String
    private lateinit var avatar: String
    private var lastVisibleItem = 0
    private lateinit var mAdapter: NewsItemAdapter
    private var page_count = 1
    private lateinit var starredItems: List<News>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainActivity = activity as MainActivity
        mainActivity.setTitle("Home Page")
        mainActivity.displayBottom(View.VISIBLE)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout_avatar.setOnClickListener {
            if (!isLogin) {
                it.findNavController().navigate(R.id.action_navigation_home_to_login_activity)
            }
        }

        swipe_refresh_layout.setOnRefreshListener {
            page_count = 1
            updateViewModel()
        }
        recyclerViewNews.layoutManager = GridLayoutManager(activity, 1)
        recyclerViewNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (lastVisibleItem + 1 == mAdapter.itemCount) {
                        updateViewModel()
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition()
            }
        })
        updateStarredItems()
    }

    private fun updateViewModel() {
        homeViewModel.getNews(page_count).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Toast.makeText(this@HomeFragment.activity, "SUCCESS", Toast.LENGTH_SHORT).show()

                        val items: MutableList<NewsItem> = it.data!!.newslist

                        items.also {
                            for (starredItem in starredItems) {
                                for (index in items.indices) {
                                    if (starredItem.id == items[index].id) {
                                        val newsItem = items[index]
                                        newsItem.saved = true
                                        items[index] = newsItem
                                        break
                                    }
                                }
                            }
                        }
                        if (page_count == 1) {
                            mAdapter = NewsItemAdapter(items, adidasDAO, recyclerViewNews.context)
                            recyclerViewNews.adapter = mAdapter
                        } else {
                            mAdapter.updateListView(items)
                        }
                        page_count++
                    }
                    Status.ERROR -> {
                        Toast.makeText(this@HomeFragment.activity, "ERROR", Toast.LENGTH_SHORT).show()

                    }
                    Status.LOADING -> {
                        Toast.makeText(this@HomeFragment.activity, "LOADING", Toast.LENGTH_SHORT).show()
                    }
                }

                swipe_refresh_layout.isRefreshing?.let {
                    swipe_refresh_layout.isRefreshing = false
                }
            }
        })
    }

    private fun updateStarredItems() {
        homeViewModel.queryStarred().observe(viewLifecycleOwner, Observer {
            starredItems = it

        })
    }

    override fun onResume() {
        super.onResume()
        page_count = 1
        //TODO Can be enhanced in the future for SP.
        val sharedPreferences: SharedPreferences = recyclerViewNews.context
            .getSharedPreferences(LoginConst.ARG_SP, Context.MODE_PRIVATE)
        isLogin = sharedPreferences.getBoolean(LoginConst.ARG_LOGIN_DATA_IS_LOGIN, false)
        username = sharedPreferences.getString(LoginConst.ARG_LOGIN_DATA_NAME, null).toString()
        avatar = sharedPreferences.getString(LoginConst.ARG_LOGIN_DATA_AVATAR, null).toString()
        updateViewModel()
        updateProfileUI()
    }

    private fun updateProfileUI() {
        if (isLogin) {
            textView_user.text = username
            activity?.let { Glide.with(it).load(avatar).into(imageView_avatar) }
        } else {
            textView_user.text = "Click here to login"
            activity?.let { Glide.with(it).load(R.mipmap.avatar_default).into(imageView_avatar) }
        }
        val content = SpannableString(textView_user.text)
        content.setSpan(UnderlineSpan(), 0, textView_user.text.length, 0)
        textView_user.text = content
    }
}