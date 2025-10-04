package br.com.inventarioapp8.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.inventarioapp8.data.local.AppDatabase
import br.com.inventarioapp8.data.local.entity.User // Import necessário
import br.com.inventarioapp8.data.repository.UserRepository
import kotlinx.coroutines.launch

// Um objeto de dados para carregar o resultado do login
data class LoginState(
    val result: LoginResult,
    val user: User? = null // Incluímos o usuário no resultado
)

enum class LoginResult {
    SUCCESS,
    FORCE_PASSWORD_CHANGE,
    INVALID_CREDENTIALS,
    ERROR
}

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    private val _loginState = MutableLiveData<LoginState?>()
    val loginState: LiveData<LoginState?> = _loginState

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun login(identifier: String, password: String) {
        viewModelScope.launch {
            try {
                val user = repository.findUserForLogin(identifier)

                if (user != null && user.passwordHash == password && user.isActive) {
                    if (password == "user123") {
                        _loginState.postValue(LoginState(LoginResult.FORCE_PASSWORD_CHANGE, user))
                    } else {
                        // 👇 AGORA PASSAMOS O OBJETO 'user' NO SUCESSO 👇
                        _loginState.postValue(LoginState(LoginResult.SUCCESS, user))
                    }
                } else {
                    _loginState.postValue(LoginState(LoginResult.INVALID_CREDENTIALS))
                }
            } catch (e: Exception) {
                _loginState.postValue(LoginState(LoginResult.ERROR))
            }
        }
    }

    fun onLoginResultHandled() {
        _loginState.value = null
    }
}