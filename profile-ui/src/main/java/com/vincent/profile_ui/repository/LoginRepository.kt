package com.vincent.profile_ui.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.vincent.profile_ui.model.LoginResponse
import java.io.BufferedReader
import java.io.InputStreamReader


class LoginRepository(private val context: Context) {

    suspend fun login(): LoginResponse? {
        Thread.sleep(2000)
        var responseString =
            BufferedReader(InputStreamReader(context.assets.open("login_response.json"))).readLines()
                .toString()
        responseString = responseString.replace("[", "")
        responseString = responseString.replace("]", "")
        Log.i("Login Response", "responseString: " + responseString)
        var loginResponse = Gson().fromJson(responseString, LoginResponse::class.java)
        return loginResponse
    }
}