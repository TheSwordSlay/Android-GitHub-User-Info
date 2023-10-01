package com.fiqri.githubusersinfo.data.retrofit

import com.fiqri.githubusersinfo.data.response.DetailUserResponse
import com.fiqri.githubusersinfo.data.response.ItemsItem
import com.fiqri.githubusersinfo.data.response.UserListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token YOUR_API_TOKEN")
    fun getUserList(
        @Query("q") q: String
    ): Call<UserListResponse>

    @GET("users/{username}")
    @Headers("Authorization: token YOUR_API_TOKEN")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token YOUR_API_TOKEN")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token YOUR_API_TOKEN")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}