package com.senac.boasviagens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.senac.boasviagens.dao.UsuarioDao
import com.senac.boasviagens.database.AppDatabase
import com.senac.boasviagens.entities.Usuario
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModelFatory(val db: AppDatabase) : ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return UsuarioViewModel(db.usuarioDao) as T
    }
}

class UsuarioViewModel(val usuarioDao: UsuarioDao) : ViewModel(){
    private val _uiState = MutableStateFlow(Usuario())
    val uiState : StateFlow<Usuario> = _uiState.asStateFlow()

    fun updateLogin(newLogin : String){
        _uiState.update { it.copy(usuario = newLogin) }
    }

    fun updateSenha(newSenha : String){
        _uiState.update { it.copy(senha = newSenha) }
    }

    fun updateEmail (newEmail : String){
        _uiState.update { it.copy(email = newEmail) }
    }

    fun updateId(id: Long){
        _uiState.update {
            it.copy(id = id)
        }
    }

    fun save(){
        viewModelScope.launch {
            val id = usuarioDao.upsert(uiState.value)
            if (id > 0){
                updateId(id)
            }
        }
    }

    suspend fun findById(id: Long): Usuario? {
        val deferred : Deferred<Usuario?> = viewModelScope.async {
            usuarioDao.findById(id)
        }
        return deferred.await()
    }

    suspend fun findByLogin(login: String, senha: String) : Long?{
        val deferred : Deferred<Usuario?> = viewModelScope.async {
            usuarioDao.findByLogin(login)
        }
        val user = deferred.await()

        if (login == user?.usuario && senha == user.senha){
            return user.id
        }
        else{
            return null
        }
    }

    fun setUiState(usuario: Usuario) {
        _uiState.value = uiState.value.copy(
            id = usuario.id,
            usuario = usuario.usuario,
            senha = usuario.senha,
            email = usuario.email
        )
    }

}