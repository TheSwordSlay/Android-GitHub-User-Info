package com.fiqri.githubusersinfo.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fiqri.githubusersinfo.data.response.ItemsItem
import com.fiqri.githubusersinfo.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowFragmentModel : ViewModel() {
    private val _listDataUsers = MutableLiveData<List<ItemsItem>>()
    val listDataUsers: LiveData<List<ItemsItem>> = _listDataUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getFollowList(search: String, position: Int) {
        _isLoading.value = true
        var client = ApiConfig.getApiService().getFollowing(search)
        if(position == 2) {
            client = ApiConfig.getApiService().getFollowers(search)
        }
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listDataUsers.value = response.body()
                    }
                } else {
                    Log.e(MainViewModel.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(MainViewModel.TAG, "onFailure: ${t.message}")
            }
        })
    }
}