package com.fiqri.githubusersinfo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fiqri.githubusersinfo.data.response.ItemsItem
import com.fiqri.githubusersinfo.data.response.UserListResponse
import com.fiqri.githubusersinfo.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel()  {
    private val _listDataUsers = MutableLiveData<List<ItemsItem>>()
    val listDataUsers: LiveData<List<ItemsItem>> = _listDataUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    companion object{
        const val TAG = "MainViewModel"
        private const val USER_QUERY = "a"
    }

    init {
        getUserList(USER_QUERY)
    }

    fun getUserList(search: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserList(search)
        client.enqueue(object : Callback<UserListResponse> {
            override fun onResponse(
                call: Call<UserListResponse>,
                response: Response<UserListResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listDataUsers.value = response.body()?.items
                    }
                }
            }
            override fun onFailure(call: Call<UserListResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}