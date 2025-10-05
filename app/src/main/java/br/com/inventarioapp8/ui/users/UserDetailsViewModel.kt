package br.com.inventarioapp8.ui.users

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.inventarioapp8.data.local.AppDatabase
import br.com.inventarioapp8.data.local.entity.Profile
import br.com.inventarioapp8.data.local.entity.User
import br.com.inventarioapp8.data.repository.UserRepository
import kotlinx.coroutines.launch

// Enum para os resultados da atualização de dados
enum class UserUpdateResult {
    SUCCESS,
    EMPTY_FIELDS,
    ERROR
}

// Enum para o resultado do reset de senha
enum class ResetResult {
    SUCCESS,
    ERROR
}

class UserDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    // LiveData para o resultado da operação de salvar
    private val _updateResult = MutableLiveData<UserUpdateResult?>()
    val updateResult: LiveData<UserUpdateResult?> = _updateResult

    // LiveData para o resultado do reset de senha
    private val _resetResult = MutableLiveData<ResetResult?>()
    val resetResult: LiveData<ResetResult?> = _resetResult

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun loadUser(userId: Long) {
        viewModelScope.launch {
            _user.postValue(repository.getUserById(userId))
        }
    }

    // Função que estava faltando
    fun saveChanges(name: String, username: String, profile: Profile, isActive: Boolean) {
        if (name.isBlank() || username.isBlank()) {
            _updateResult.value = UserUpdateResult.EMPTY_FIELDS
            return
        }

        val currentUser = _user.value
        if (currentUser == null) {
            _updateResult.value = UserUpdateResult.ERROR
            return
        }

        currentUser.apply {
            this.name = name
            this.username = username.lowercase()
            this.profile = profile
            this.isActive = isActive
        }

        viewModelScope.launch {
            try {
                repository.updateUser(currentUser)
                _updateResult.postValue(UserUpdateResult.SUCCESS)
            } catch (_: Exception) {
                _updateResult.postValue(UserUpdateResult.ERROR)
            }
        }
    }

    // Função que estava faltando
    fun resetPassword(userId: Long) {
        viewModelScope.launch {
            try {
                val userToReset = repository.getUserById(userId)
                if (userToReset != null) {
                    userToReset.passwordHash = "user123"
                    repository.updateUser(userToReset)
                    _user.postValue(userToReset)
                    _resetResult.postValue(ResetResult.SUCCESS)
                } else {
                    _resetResult.postValue(ResetResult.ERROR)
                }
            } catch (_: Exception) {
                _resetResult.postValue(ResetResult.ERROR)
            }
        }
    }

    // Funções que estavam faltando
    fun onUpdateResultHandled() {
        _updateResult.value = null
    }

    fun onResetResultHandled() {
        _resetResult.value = null
    }
}