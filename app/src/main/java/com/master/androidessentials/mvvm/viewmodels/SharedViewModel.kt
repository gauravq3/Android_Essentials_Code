package com.master.androidessentials.mvvm.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.master.androidessentials.mvvm.models.userslist.User
import com.master.androidessentials.mvvm.repositories.HomeRepository
import com.master.androidessentials.networking.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    private val _usersList = MutableStateFlow<ApiResponse<List<User>>>(ApiResponse.Loading)
    val usersList: StateFlow<ApiResponse<List<User>>> get() = _usersList

    //user id
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user

    fun setUserDetails(userdata: User) {
        _user.value = userdata
    }

    fun fetchAllPosts() {
        _usersList.value = ApiResponse.Loading
        viewModelScope.launch {
            repository.getAllUsers().collect{ response ->
                _usersList.value = response
            }
        }
    }


}