package com.example.lab13

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Query("SELECT COUNT(*) FROM User")
    suspend fun getCount(): Int

    @Query("SELECT * FROM User WHERE username = :username AND password = :password LIMIT 1")
    suspend fun login(username: String, password: String): User?
}