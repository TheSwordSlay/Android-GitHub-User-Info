package com.fiqri.githubusersinfo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(fav: Fav)

    @Update
    fun update(fav: Fav)

    @Delete
    fun delete(fav: Fav)

    @Query("SELECT * from fav ORDER BY id ASC")
    fun getAllFav(): LiveData<List<Fav>>

    @Query("SELECT * FROM fav WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<Fav>
}