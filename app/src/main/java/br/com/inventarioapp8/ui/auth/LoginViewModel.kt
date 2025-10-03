package br.com.inventarioapp8.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.inventarioapp8.data.local.AppDatabase
import br.com.inventarioapp8.data.repository.UserRepository
import kotlinx.coroutines.launch

enum class LoginResult {
    SUCCESS,
    INVALID_CREDENTIALS,
    ERROR
}

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    // 👇 MUDANÇA 1: Adicionar '?' para permitir valor nulo
    private val _loginResult = MutableLiveData<LoginResult?>()
    val loginResult: LiveData<LoginResult?> = _loginResult

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun login(identifier: String, password: String) {
        // Evita múltiplos cliques enquanto já está carregando (será útil no futuro)
        if (_loginResult.value != null) {
            // Se já houver um resultado, limpa antes de tentar de novo
            onLoginResultHandled()
        }

        viewModelScope.launch {
            try {
                val user = repository.findUserForLogin(identifier)
                if (user != null && user.passwordHash == password && user.isActive) {
                    _loginResult.postValue(LoginResult.SUCCESS)
                } else {
                    _loginResult.postValue(LoginResult.INVALID_CREDENTIALS)
                }
            } catch (e: Exception) {
                _loginResult.postValue(LoginResult.ERROR)
            }
        }
    }

    // 👇 MUDANÇA 2: Adicionar a função de limpeza
    fun onLoginResultHandled() {
        _loginResult.value = null
    }
}