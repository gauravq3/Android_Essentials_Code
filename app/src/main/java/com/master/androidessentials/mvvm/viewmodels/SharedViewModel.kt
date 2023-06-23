package com.master.androidessentials.mvvm.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.master.androidessentials.mvvm.models.userslist.User
import com.master.androidessentials.networking.ApiResponse
import com.master.androidessentials.mvvm.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    private val _usersList = MutableLiveData<ApiResponse<List<User>>>()
    val usersList: LiveData<ApiResponse<List<User>>> get() = _usersList

    //user id
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    fun setUserDetails(userdata: User) {
        _user.value = userdata
    }

    fun fetchAllPosts() {
        _usersList.value = ApiResponse.Loading
        viewModelScope.launch {
            repository.getAllUsers().observeForever { response ->
                _usersList.value = response
            }
        }
    }

}