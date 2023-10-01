package com.fiqri.githubusersinfo.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.fiqri.githubusersinfo.database.Fav
import com.fiqri.githubusersinfo.database.FavDao
import com.fiqri.githubusersinfo.database.FavRoomDatabase

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavRepository(application: Application) {
    private val mFavDao: FavDao

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavRoomDatabase.getDatabase(application)
        mFavDao = db.favDao()
    }

    fun getAllFavs(): LiveData<List<Fav>> = mFavDao.getAllFav()

    fun insert(fav: Fav) {
        executorService.execute { mFavDao.insert(fav) }
    }

    fun getUsernameFav(username: String): LiveData<Fav> = mFavDao.getFavoriteUserByUsername(username)
    fun delete(fav: Fav) {
        executorService.execute { mFavDao.delete(fav) }
    }

    fun update(fav: Fav) {
        executorService.execute { mFavDao.update(fav) }
    }
}