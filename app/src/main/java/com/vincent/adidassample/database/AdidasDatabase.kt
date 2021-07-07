package com.vincent.adidassample.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vincent.adidassample.database.dao.AdidasDAO
import com.vincent.adidassample.model.News

@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class AdidasDatabase: RoomDatabase() {

    abstract fun adidasDAO(): AdidasDAO

}