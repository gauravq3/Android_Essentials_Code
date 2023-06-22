package com.master.androidessentials.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.master.androidessentials.models.userslist.UsersList
import com.master.androidessentials.networking.ApiResponse
import com.master.androidessentials.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    private val _allPosts = MutableLiveData<ApiResponse<UsersList>>()
    val allPosts: LiveData<ApiResponse<UsersList>> get() = _allPosts


    fun fetchAllPosts() {
        _allPosts.value = ApiResponse.Loading
        viewModelScope.launch {

            try {

                // Perform the API call on the IO Dispatcher
             val response=   repository.getAllPosts()
                _allPosts.value = response


                // Handle the received data

            } catch (e: Exception) {
                // Handle any exceptions
            }


        }
    }

}