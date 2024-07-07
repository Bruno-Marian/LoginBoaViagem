package com.senac.boasviagens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.senac.boasviagens.dao.ViagemDao
import com.senac.boasviagens.database.AppDatabase
import com.senac.boasviagens.entities.TipoViagem
import com.senac.boasviagens.entities.Viagem
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class ViagemViewModelFatory(val db: AppDatabase) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViagemViewModel(db.viagemDao) as T
    }
}
class ViagemViewModel(val viagemDao: ViagemDao): ViewModel() {
    private val _uiState = MutableStateFlow(Viagem())
    val uiState: StateFlow<Viagem> = _uiState.asStateFlow()

    fun updateDestino(destino: String){
        _uiState.update {
            it.copy(destino = destino)
        }
    }

    fun updateTipo(tipo: TipoViagem){
        _uiState.update {
            it.copy(tipo = tipo)
        }
    }

    fun updateInicio(inicio: Date?){
        _uiState.update {
            it.copy(inicio = inicio)
        }
    }

    fun updateFim(fim: Date?){
        _uiState.update {
            it.copy(fim = fim)
        }
    }

    fun updateOrcamento(orcamento: Float?){
        _uiState.update {
            it.copy(orcamento = orcamento)
        }
    }

    fun updateId(id: Long){
        _uiState.update {
            it.copy(id = id)
        }
    }

    fun save(){
        viewModelScope.launch {
            val id = viagemDao.upsert(uiState.value)
            if (id > 0){
                updateId(id)
            }
        }
    }

    fun delete(viagem: Viagem){
        viewModelScope.launch {
            viagemDao.delete(viagem)
        }
    }

    suspend fun findById(id: Long): Viagem? {
        val deferred : Deferred<Viagem?> = viewModelScope.async {
            viagemDao.findById(id)
        }
        return deferred.await()
    }

    fun setUiState(viagem: Viagem) {
        _uiState.value = uiState.value.copy(
            id = viagem.id,
            destino = viagem.destino,
            tipo = viagem.tipo,
            inicio = viagem.inicio,
            fim = viagem.fim,
            orcamento = viagem.orcamento
        )
    }

}