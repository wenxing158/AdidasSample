package com.vincent.adidassample.di

import androidx.room.Room
import com.vincent.adidassample.R
import com.vincent.adidassample.database.dao.AdidasDAO
import com.vincent.adidassample.database.AdidasDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AdidasDatabase::class.java,
            androidApplication().baseContext.getString(R.string.app_name)
        ).build()
    }

    single {
        get<AdidasDatabase>().adidasDAO()
    }
}