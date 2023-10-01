package com.fiqri.githubusersinfo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fiqri.githubusersinfo.data.response.DetailUserResponse
import com.fiqri.githubusersinfo.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailModel : ViewModel() {

    companion object{
        const val TAG = "MainViewModel"
        private const val USERNAME = "a"
    }

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _nama = MutableLiveData<String>()
    val nama: LiveData<String> = _nama

    private val _follower = MutableLiveData<Int>()
    val followers: LiveData<Int> = _follower

    private val _following = MutableLiveData<Int>()
    val followings: LiveData<Int> = _following

    private val _avatar_url = MutableLiveData<String>()
    val avatar_url: LiveData<String> = _avatar_url

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _username.value = response.body()?.login
                        _nama.value = response.body()?.name
                        _follower.value = response.body()?.followers
                        _following.value = response.body()?.following
                        _avatar_url.value = response.body()?.avatar_url
                    }
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}