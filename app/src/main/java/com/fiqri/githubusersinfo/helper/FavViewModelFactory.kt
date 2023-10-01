package com.fiqri.githubusersinfo.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fiqri.githubusersinfo.ui.FavAddDeleteViewModel
import com.fiqri.githubusersinfo.ui.FavViewModel

class FavViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: FavViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): FavViewModelFactory {
            if (INSTANCE == null) {
                synchronized(FavViewModelFactory::class.java) {
                    INSTANCE = FavViewModelFactory(application)
                }
            }
            return INSTANCE as FavViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavAddDeleteViewModel::class.java)) {
            return FavAddDeleteViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}