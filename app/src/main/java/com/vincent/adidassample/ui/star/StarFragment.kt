package com.vincent.adidassample.ui.star

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vincent.adidassample.R
import com.vincent.adidassample.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class StarFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_star, container, false)
    }

}