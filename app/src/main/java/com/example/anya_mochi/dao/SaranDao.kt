package com.example.anya_mochi.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.anya_mochi.entity.SaranEntity

@Dao
interface SaranDao {
    @Query("SELECT * FROM saran")
    suspend fun getAllSaran(): List<SaranEntity>

    @Insert
    suspend fun insertSaran(saran: SaranEntity)

    @Delete
    suspend fun deleteSaran(saran: SaranEntity)
}