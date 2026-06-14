package com.example.anya_mochi.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saran")
data class SaranEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val judulSaran: String,
    val rincianSaran: String,
    val tanggalSaran: Long
)