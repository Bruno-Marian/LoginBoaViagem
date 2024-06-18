package com.senac.boasviagens.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.senac.boasviagens.entities.Viagem
import kotlinx.coroutines.flow.Flow
import org.intellij.lang.annotations.JdkConstants.ListSelectionMode

@Dao
interface ViagemDao {
    @Insert
    fun insert(viagem: Viagem) : Long

    @Update
    fun update(viagem: Viagem)

    @Upsert
    suspend fun upsert(viagem: Viagem) : Long

    @Delete
    fun delete(viagem: Viagem)

    @Query("SELECT * FROM Viagem ORDER BY id DESC")
    fun getAll() : Flow<List<Viagem>>

    @Query("SELECT * FROM Viagem V WHERE V.id = :id")
    fun findById(id: Long) : Viagem?
}