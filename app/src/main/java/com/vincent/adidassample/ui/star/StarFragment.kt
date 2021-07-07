package com.vincent.adidassample.ui.star

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.vincent.adidassample.MainActivity
import com.vincent.adidassample.R
import com.vincent.adidassample.database.dao.AdidasDAO
import com.vincent.adidassample.model.News
import com.vincent.adidassample.ui.home.HomeViewModel
import com.vincent.profile_ui.utils.LoginConst
import kotlinx.android.synthetic.main.fragment_star.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class StarFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private val adidasDAO: AdidasDAO by inject()
    private var isLogin = false

    private lateinit var username: String
    private lateinit var avatar: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainActivity = activity as MainActivity
        mainActivity.setTitle("Favorite Page")
        mainActivity.displayBottom(View.VISIBLE)
        return inflater.inflate(R.layout.fragment_star, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout_avatar.setOnClickListener {
            if (!isLogin) {
                it.findNavController().navigate(R.id.action_navigation_star_to_login_activity)
            }
        }
        homeViewModel.queryStarred().observe(viewLifecycleOwner, Observer {
            var mutableList: MutableList<News> = ArrayList(it)
            recyclerViewNews.adapter =
                StarredNewsItemAdapter(mutableList, adidasDAO, recyclerViewNews.context)
        })
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences: SharedPreferences = recyclerViewNews.context
            .getSharedPreferences(LoginConst.ARG_SP, Context.MODE_PRIVATE)
        isLogin = sharedPreferences.getBoolean(LoginConst.ARG_LOGIN_DATA_IS_LOGIN, false)
        username = sharedPreferences.getString(LoginConst.ARG_LOGIN_DATA_NAME, null).toString()
        avatar = sharedPreferences.getString(LoginConst.ARG_LOGIN_DATA_AVATAR, null).toString()
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