package com.senac.boasviagens.viewmodels

import androidx.lifecycle.ViewModel
import com.senac.boasviagens.models.Dados
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DadosViewModel : ViewModel(){

    private val _uiState = MutableStateFlow(Dados())
    val uiState : StateFlow<Dados> = _uiState.asStateFlow()

    fun updateLogin(newLogin : String){
        _uiState.update { it.copy(login = newLogin) }
    }

    fun updateSenha(newSenha : String){
        _uiState.update { it.copy(senha = newSenha) }
    }

    fun updadeVisivel (newVisivel : Boolean){
        _uiState.update { it.copy(visivel = newVisivel) }
    }

    fun updateEmail (newEmail : String){
        _uiState.update { it.copy(email = newEmail) }
    }

}