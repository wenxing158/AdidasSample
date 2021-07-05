package com.vincent.profile_ui.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vincent.profile_ui.repository.LoginRepository
import com.vincent.profile_ui.utils.Resource
import kotlinx.coroutines.Dispatchers

class LoginViewModel (private val loginRepository: LoginRepository) : ViewModel() {

    fun login() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = loginRepository.login()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}