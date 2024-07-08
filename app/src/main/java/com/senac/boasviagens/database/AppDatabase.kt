package com.senac.boasviagens.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.senac.boasviagens.dao.UsuarioDao
import com.senac.boasviagens.dao.ViagemDao
import com.senac.boasviagens.entities.Converters
import com.senac.boasviagens.entities.Viagem
import com.senac.boasviagens.entities.Usuario

@Database(entities = [Viagem::class, Usuario::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val viagemDao: ViagemDao
    abstract val usuarioDao: UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase = INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "appviagem-db-last"
            )
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            instance
        }
    }
}