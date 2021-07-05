package com.vincent.profile_ui.di

import android.content.Context
import android.content.SharedPreferences
import com.vincent.profile_ui.repository.LoginRepository
import com.vincent.profile_ui.ui.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginViewModelsModule = module {
    viewModel { LoginViewModel(get<LoginRepository>()) }
}

val loginRepositoryModule = module {
    single {
        LoginRepository(get())
    }

    single {

    }
}