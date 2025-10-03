package br.com.inventarioapp8.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.inventarioapp8.data.local.AppDatabase
import br.com.inventarioapp8.data.repository.UserRepository
import kotlinx.coroutines.launch

enum class UpdateResult {
    SUCCESS,
    PASSWORD_MISMATCH,
    PASSWORD_TOO_SHORT,
    ERROR
}

class ChangePasswordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    private val _updateResult = MutableLiveData<UpdateResult>()
    val updateResult: LiveData<UpdateResult> = _updateResult

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun updatePassword(userId: Long, newPass: String, confirmPass: String) {
        // Validações
        if (newPass.length < 6) {
            _updateResult.value = UpdateResult.PASSWORD_TOO_SHORT
            return
        }
        if (newPass != confirmPass) {
            _updateResult.value = UpdateResult.PASSWORD_MISMATCH
            return
        }

        viewModelScope.launch {
            try {
                // Busca o usuário no banco
                val userToUpdate = repository.getUserById(userId)
                if (userToUpdate != null) {
                    // Atualiza a senha e salva
                    userToUpdate.passwordHash = newPass
                    repository.updateUser(userToUpdate)
                    _updateResult.postValue(UpdateResult.SUCCESS)
                } else {
                    // Erro: usuário não encontrado
                    _updateResult.postValue(UpdateResult.ERROR)
                }
            } catch (e: Exception) {
                _updateResult.postValue(UpdateResult.ERROR)
            }
        }
    }
}