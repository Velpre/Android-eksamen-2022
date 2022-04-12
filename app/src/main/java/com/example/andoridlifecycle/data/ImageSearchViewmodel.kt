package com.example.andoridlifecycle.data

import android.app.Application
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class ImageSearchViewmodel : ViewModel() {
    private var mockUri: String =
        "https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/PNG_transparency_demonstration_1.png/640px-PNG_transparency_demonstration_1.png"

    //private val repository: ImageSearchRepository = ImageSearchRepository();
    private val urls: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>().also {
            loadUsers()
        }
    }

    fun getUrls(): LiveData<List<String>> {
        return urls
    }

    private fun loadUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            //repository.getJSON(mockUri)
        }
    }
}