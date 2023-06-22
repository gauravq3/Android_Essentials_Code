package com.master.androidessentials.mvvm.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.master.androidessentials.mvvm.models.userslist.User
import com.master.androidessentials.mvvm.models.userslist.UsersList
import com.master.androidessentials.networking.ApiResponse
import com.master.androidessentials.mvvm.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    private val _allPosts = MutableLiveData<ApiResponse<UsersList>>()
    val allPosts: LiveData<ApiResponse<UsersList>> get() = _allPosts

    //user id
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    fun setUserDetails(userdata: User) {
        _user.value = userdata
    }


    fun fetchAllPosts() {
        _allPosts.value = ApiResponse.Loading
        viewModelScope.launch {
            try {
                val response = repository.getAllPosts()
                _allPosts.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}