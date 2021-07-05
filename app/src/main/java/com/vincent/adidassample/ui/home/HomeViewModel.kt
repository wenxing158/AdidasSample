package com.vincent.adidassample.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vincent.adidassample.model.News
import com.vincent.adidassample.model.NewsItem
import com.vincent.adidassample.repository.MainRepository
import com.vincent.profile_ui.utils.Resource
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val listNews = MutableLiveData<List<NewsItem>>()
    private val listNews1 = MutableLiveData<List<News>>()

    fun getNews() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getNews()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getListNews(): LiveData<List<News>> {
        listNews1.value = listOf(
            News(),
            News(),
            News(),
            News(),
            News(),
            News(),
            News(),
            News(),
            News(),
            News(),
            News(),
            News()
        )
        return listNews1
    }
}