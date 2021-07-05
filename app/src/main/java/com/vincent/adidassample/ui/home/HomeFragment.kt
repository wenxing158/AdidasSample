package com.vincent.adidassample.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.vincent.adidassample.MainActivity
import com.vincent.adidassample.R
import com.vincent.adidassample.model.NewsItem
import com.vincent.profile_ui.utils.LoginConst
import com.vincent.profile_ui.utils.Status
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var isLogin = false
    private lateinit var username: String
    private lateinit var avatar: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainActivity = activity as MainActivity
        mainActivity.setTitle("Home Page")
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewNews = recyclerViewNews
//        homeViewModel.getListNews().observe(viewLifecycleOwner, Observer {
//            val items: List<News> = it
//            recyclerViewNews.adapter = NewsAdapter(items, view.context)
//        })
        layout_avatar.setOnClickListener {
            if (!isLogin) {
                it.findNavController().navigate(R.id.action_navigation_home_to_login_activity)
            }
        }

        homeViewModel.getNews().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Toast.makeText(this@HomeFragment.activity, "SUCCESS", Toast.LENGTH_LONG).show()

                        val items: List<NewsItem> = it.data!!.newslist
                        recyclerViewNews.adapter = NewsItemAdapter(items, view.context)

                    }
                    Status.ERROR -> {
                        Toast.makeText(this@HomeFragment.activity, "ERROR", Toast.LENGTH_LONG).show()

                    }
                    Status.LOADING -> {
                        Toast.makeText(this@HomeFragment.activity, "LOADING", Toast.LENGTH_LONG).show()
                    }
                }

            }
        })
    }

    override fun onResume() {
        super.onResume()

        val sharedPreferences: SharedPreferences = recyclerViewNews.context
            .getSharedPreferences(LoginConst.ARG_SP, Context.MODE_PRIVATE)
        isLogin = sharedPreferences.getBoolean(LoginConst.ARG_LOGIN_DATA_IS_LOGIN, false)
        username = sharedPreferences.getString(LoginConst.ARG_LOGIN_DATA_NAME, null).toString()
        avatar = sharedPreferences.getString(LoginConst.ARG_LOGIN_DATA_AVATAR, null).toString()
        if (isLogin) {
            updateProfileUI()
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        val loginData = data?.getParcelableExtra<LoginData>(LoginConst.ARG_LOGIN_DATA)
//        updateProfileUI(loginData)
//    }

    private fun updateProfileUI() {
        textView_user.text = username
        activity?.let { Glide.with(it).load(avatar).into(imageView_avatar) }
    }
}