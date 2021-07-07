package com.vincent.profile_ui.ui

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
import com.vincent.profile_ui.R
import com.vincent.profile_ui.model.LoginData
import com.vincent.profile_ui.utils.LoginConst
import com.vincent.profile_ui.utils.Status
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val content = SpannableString(textview_login.text)
        content.setSpan(UnderlineSpan(), 0, textview_login.text.length, 0)
        textview_login.text = content

        textview_login.setOnClickListener {
            loginViewModel.login().observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(this@LoginFragment.activity, "LOGIN SUCCESS", Toast.LENGTH_SHORT).show()

                            val loginData: LoginData = it.data!!.data
//                            var intent: Intent = Intent()
//                            intent.putExtra(LoginConst.ARG_LOGIN_DATA, loginData)
//                            onActivityResult(0, 0, intent)
                            //TODO Can be enhanced further with wrapped SP.
                            val sharedPreferences: SharedPreferences = textview_login.context
                                .getSharedPreferences(LoginConst.ARG_SP, Context.MODE_PRIVATE)
                            val edit = sharedPreferences.edit()
                            edit.putString(LoginConst.ARG_LOGIN_DATA_AVATAR, loginData.avatar)
                            edit.putString(LoginConst.ARG_LOGIN_DATA_NAME, loginData.nickname)
                            edit.putBoolean(LoginConst.ARG_LOGIN_DATA_IS_LOGIN, true)
                            edit.commit()
                            activity?.finish()
                        }
                        Status.ERROR -> {
                            Toast.makeText(this@LoginFragment.activity, "LOGIN ERROR", Toast.LENGTH_SHORT).show()

                        }
                        Status.LOADING -> {
                            Toast.makeText(this@LoginFragment.activity, "LOADING", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            })
        }
    }
}