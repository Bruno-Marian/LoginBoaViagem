package com.senac.boasviagens.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date

enum class TipoViagem(){
 Lazer,
 Negocio,
}

@Entity
data class Viagem(
 @PrimaryKey(autoGenerate = true) val id: Long = 0,
 val destino: String = "",
 val tipo: TipoViagem = TipoViagem.Lazer,
 val inicio: Date? = null,
 val fim: Date? = null,
 val orcamento: Float? = null,
 val usuario: Long = 0
) {
}

class Converters {
 @TypeConverter
 fun fromTipoViagem(tipo: TipoViagem): String {
  return tipo.name
 }

 @TypeConverter
 fun toTipoViagem(value: String): TipoViagem {
  return TipoViagem.valueOf(value)
 }

 @TypeConverter
 fun fromDate(date: Date?): Long? {
  return date?.time
 }

 @TypeConverter
 fun toDate(value: Long?): Date? {
  return value?.let { Date(it) }
 }
}