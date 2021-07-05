package com.vincent.adidassample.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class NewsItem(
    val id: String,
    val ctime: String,
    val title: String,
    val description: String,
    val source: String,
    val picUrl: String,
    val url: String,
): Parcelable
