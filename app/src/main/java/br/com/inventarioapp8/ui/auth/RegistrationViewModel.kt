package br.com.inventarioapp8.ui.auth

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
import java.util.Date

enum class RegistrationResult {
    SUCCESS,
    EMPTY_FIELDS,
    USERNAME_ALREADY_EXISTS,
    ERROR
}

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    private val _registrationResult = MutableLiveData<RegistrationResult>()
    val registrationResult: LiveData<RegistrationResult> = _registrationResult

    // 👇 LIVEDATA NOVO PARA O PRÓXIMO ID 👇
    private val _nextUserId = MutableLiveData<Long>()
    val nextUserId: LiveData<Long> = _nextUserId

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        loadNextUserId() // Carrega o ID assim que o ViewModel é criado
    }

    private fun loadNextUserId() {
        viewModelScope.launch {
            val lastId = repository.getLastUserId() ?: 1000
            val newId = if (lastId < 1001) 1001 else lastId + 1
            _nextUserId.postValue(newId)
        }
    }

    fun registerUser(name: String, username: String, profile: Profile) {
        if (name.isBlank() || username.isBlank()) {
            _registrationResult.value = RegistrationResult.EMPTY_FIELDS
            return
        }

        viewModelScope.launch {
            try {
                if (repository.getUserByUsername(username) != null) {
                    _registrationResult.postValue(RegistrationResult.USERNAME_ALREADY_EXISTS)
                    return@launch
                }

                // Usamos o ID que já foi calculado e está no LiveData
                val newId = _nextUserId.value ?: return@launch

                val newUser = User(
                    id = newId,
                    name = name,
                    username = username.lowercase(),
                    passwordHash = "user123",
                    profile = profile,
                    isActive = true,
                    creationDate = Date(),
                    inactivationDate = null
                )

                repository.insertUser(newUser)
                _registrationResult.postValue(RegistrationResult.SUCCESS)

            } catch (e: Exception) {
                _registrationResult.postValue(RegistrationResult.ERROR)
            }
        }
    }
}