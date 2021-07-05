package com.vincent.adidassample.di

import com.vincent.adidassample.api.APIHelper
import com.vincent.adidassample.api.AdidasService
import com.vincent.adidassample.api.RetrofitBuilder
import com.vincent.adidassample.repository.MainRepository
import com.vincent.adidassample.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelsModule = module {
    viewModel { HomeViewModel(get<MainRepository>()) }
}

val repositoryModule = module {
    single { MainRepository(get<APIHelper>()) }
}

val remoteModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(RetrofitBuilder.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<APIHelper> {
        APIHelper(get<AdidasService>())
    }

    single<AdidasService> {
        get<Retrofit>().create(AdidasService::class.java)
    }
}