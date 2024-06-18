package com.senac.boasviagens.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.senac.boasviagens.dao.ViagemDao
import com.senac.boasviagens.entities.Viagem

@Database(entities = [Viagem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val viagemDao: ViagemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase = INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "viagem-db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}