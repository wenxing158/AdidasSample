package com.vincent.profile_ui.model

data class LoginResponse(
    val code: Int,
    val msg: String,
    val data: LoginData,
)
