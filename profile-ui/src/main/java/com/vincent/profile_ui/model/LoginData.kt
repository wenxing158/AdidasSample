package com.vincent.profile_ui.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class LoginData(
    val userId: Int,
    val nickname: String,
    val avatar: String,
): Parcelable
