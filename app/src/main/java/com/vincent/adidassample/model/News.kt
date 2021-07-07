package com.vincent.adidassample.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class News(
    @PrimaryKey
    @NonNull
    var id: String,
    var ctime: String,
    var title: String,
    var description: String,
    var source: String,
    var picUrl: String,
    var url: String,
    var saved: Boolean = false
)
