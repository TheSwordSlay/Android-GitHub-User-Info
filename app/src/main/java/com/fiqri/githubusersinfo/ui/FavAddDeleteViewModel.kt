package com.fiqri.githubusersinfo.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import com.fiqri.githubusersinfo.database.Fav
import com.fiqri.githubusersinfo.repository.FavRepository

class FavAddDeleteViewModel(application: Application) : ViewModel() {
    private val mFavRepository: FavRepository = FavRepository(application)

    fun insert(fav: Fav) {
        mFavRepository.insert(fav)
    }

    fun update(fav: Fav) {
        mFavRepository.update(fav)
    }

    fun delete(fav: Fav) {
        mFavRepository.delete(fav)
    }
}