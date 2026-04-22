package com.example.authorization

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Query("SELECT COUNT(*) FROM User")
    suspend fun getCount(): Int

    // TODO #2: write login query
}