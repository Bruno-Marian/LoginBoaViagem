package com.senac.boasviagens.models

import com.senac.boasviagens.R
import java.time.LocalDate

enum class TipoViagem(){
    Lazer,
    Negocio,
}
data class Viagem(

    val id: Long = 1,
    val destino: String = "",
    val tipo: TipoViagem = TipoViagem.Lazer,
    val inicio: LocalDate,
    val fim: LocalDate,
    val orcamento: Float = 10.00f
)