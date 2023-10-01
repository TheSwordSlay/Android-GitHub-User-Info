package com.fiqri.githubusersinfo.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fiqri.githubusersinfo.database.Fav
import com.fiqri.githubusersinfo.repository.FavRepository

class FavViewModel(application: Application) : ViewModel() {

    private val mFavRepository: FavRepository = FavRepository(application)

    fun getAllNotes(): LiveData<List<Fav>> = mFavRepository.getAllFavs()
    fun getFavUsername(username: String): LiveData<Fav> = mFavRepository.getUsernameFav(username)
}