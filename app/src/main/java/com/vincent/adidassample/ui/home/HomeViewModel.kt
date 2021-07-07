package com.vincent.adidassample.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vincent.adidassample.database.dao.AdidasDAO
import com.vincent.adidassample.model.News
import com.vincent.adidassample.model.NewsItem
import com.vincent.adidassample.model.NewsListResponse
import com.vincent.adidassample.repository.MainRepository
import com.vincent.profile_ui.utils.Resource
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val mainRepository: MainRepository, private val adidasDAO: AdidasDAO) : ViewModel() {


    fun getNews(page_count: Int): LiveData<Resource<NewsListResponse>> = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getNews(page_count)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun queryStarred(): LiveData<List<News>> {
        return adidasDAO.queryStarred(true)
    }
}