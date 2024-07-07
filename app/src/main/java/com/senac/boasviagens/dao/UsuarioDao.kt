package com.senac.boasviagens.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.senac.boasviagens.entities.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Insert
    fun insert(dados: Usuario) : Long

    @Update
    fun update(dados: Usuario)

    @Upsert
    suspend fun upsert(dados: Usuario) : Long

    @Delete
    suspend fun delete(dados: Usuario)

    @Query("SELECT * FROM Usuario ORDER BY id DESC")
    fun getAll() : Flow<List<Usuario>>

    @Query("SELECT * FROM Usuario V WHERE V.id = :id")
    suspend fun findById(id: Long) : Usuario?

    @Query("SELECT * FROM Usuario p where p.usuario = :usuario")
    suspend fun findByLogin(usuario : String) : Usuario?
}