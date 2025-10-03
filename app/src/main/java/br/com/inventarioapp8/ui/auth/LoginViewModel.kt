package br.com.inventarioapp8.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class LoginResult {
    SUCCESS,
    INVALID_CREDENTIALS
}

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(identifier: String, password: String) {
        if (identifier.equals("admin", ignoreCase = true) && password == "admin123") {
            _loginResult.value = LoginResult.SUCCESS
        } else {
            _loginResult.value = LoginResult.INVALID_CREDENTIALS
        }
    }
}