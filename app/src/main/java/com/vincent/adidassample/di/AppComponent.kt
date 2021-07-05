package com.vincent.adidassample.di

import com.vincent.profile_ui.di.loginRepositoryModule
import com.vincent.profile_ui.di.loginViewModelsModule

val appComponent = listOf(
    viewModelsModule,
    repositoryModule,
    remoteModule,
    loginViewModelsModule,
    loginRepositoryModule
)