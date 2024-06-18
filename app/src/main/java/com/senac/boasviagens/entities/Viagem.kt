package com.senac.boasviagens.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TipoViagem(){
 Lazer,
 Negocio,
}

@Entity
data class Viagem(
 @PrimaryKey(autoGenerate = true) val id: Long = 0,
 val destino: String = "",
 val tipo: TipoViagem = TipoViagem.Lazer,
 val inicio: Long = 0,
 val fim: Long = 0,
 val orcamento: Float = 0.00f
) {
}