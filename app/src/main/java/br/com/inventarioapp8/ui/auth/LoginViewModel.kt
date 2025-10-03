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

    private val _loginResult = MutableLiveData<LoginResult?>()
    val loginResult: LiveData<LoginResult?> = _loginResult

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun login(identifier: String, password: String) {
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

    fun onLoginResultHandled() {
        _loginResult.value = null
    }
}