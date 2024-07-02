package com.senac.boasviagens.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.senac.boasviagens.entities.Viagem
import kotlinx.coroutines.flow.Flow

@Dao
interface ViagemDao {
    @Insert
    fun insert(viagem: Viagem) : Long

    @Update
    fun update(viagem: Viagem)

    @Upsert
    suspend fun upsert(viagem: Viagem) : Long

    @Delete
    suspend fun delete(viagem: Viagem)

    @Query("SELECT * FROM Viagem ORDER BY id DESC")
    fun getAll() : Flow<List<Viagem>>

    @Query("SELECT * FROM Viagem V WHERE V.id = :id")
    suspend fun findById(id: Long) : Viagem?
}