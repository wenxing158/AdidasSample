package com.vincent.adidassample

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.vincent.adidassample.database.dao.AdidasDAO
import com.vincent.adidassample.databinding.ActivityMainBinding
import com.vincent.profile_ui.LoginActivity
import com.vincent.profile_ui.utils.LoginConst
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private var isLogin: Boolean = false
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mainBinding: ActivityMainBinding
    private val adidasDAO: AdidasDAO by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }

    override fun onResume() {
        super.onResume()
        sharedPreferences = getSharedPreferences(LoginConst.ARG_SP, Context.MODE_PRIVATE)
        isLogin = sharedPreferences.getBoolean(LoginConst.ARG_LOGIN_DATA_IS_LOGIN, false)

        if (isLogin) {
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.item_logout -> logout()
                    else -> throw UnsupportedOperationException("No itemId called")
                }
            }
        }
    }

    fun displayBottom(type: Int) {
        mainBinding.navView.visibility = type
    }

    private fun initUI() {
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(mainBinding.navView, navHostFragment.navController)
        toolbar.inflateMenu(R.menu.toolbar_menu)
    }

    fun setTitle(title: String) {
        toolbar.title = title
    }

    fun logout(): Boolean {
        sharedPreferences.edit().clear().commit()
        Thread{
            adidasDAO.deleteAll()
        }.start()
        startActivity(Intent(this, LoginActivity::class.java))
        return false
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.nav_view).navigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}