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
    private val _allPosts = MutableLiveData<ApiResponse<List<User>>>()
    val allPosts: LiveData<ApiResponse<List<User>>> get() = _allPosts

    //user id
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    fun setUserDetails(userdata: User) {
        _user.value = userdata
    }


    fun fetchAllPosts() {
        _allPosts.value = ApiResponse.Loading
        viewModelScope.launch {
            repository.getAllUsers().observeForever { response ->
                _allPosts.value = response
            }
        }
    }

}