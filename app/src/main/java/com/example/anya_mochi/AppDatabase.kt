package com.example.anya_mochi

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.anya_mochi.dao.NoteDao
import com.example.anya_mochi.dao.SaranDao
import com.example.anya_mochi.entity.NoteEntity
import com.example.anya_mochi.entity.SaranEntity

// 1. PASTIKAN VERSION SUDAH DINAIKKAN MENJADI 2 (karena ada tabel baru/perubahan skema)
@Database(entities = [NoteEntity::class, SaranEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun saranDao(): SaranDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "anyamochi_database"
                )
                    // 2. TAMBAHKAN BARIS INI (Sesuai Solusi Halaman 22 Modul)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}