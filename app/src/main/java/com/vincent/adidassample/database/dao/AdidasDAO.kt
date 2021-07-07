package com.vincent.adidassample.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vincent.adidassample.model.News

@Dao
interface AdidasDAO {

    @Query("SELECT * FROM news WHERE saved = :saved")
    fun queryStarred(saved: Boolean): LiveData<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(news: News)

    @Update
    fun update(news: News)

    @Delete
    fun delete(news: News)

    @Query("DELETE FROM news")
    fun deleteAll()

}