package br.com.inventarioapp8.ui.users

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.inventarioapp8.data.local.AppDatabase
import br.com.inventarioapp8.data.local.entity.User
import br.com.inventarioapp8.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun loadUser(userId: Long) {
        viewModelScope.launch {
            // Busca o usuário no banco de dados em uma thread de fundo
            val loadedUser = repository.getUserById(userId)
            // Posta o resultado para a LiveData, que notificará a UI na thread principal
            _user.postValue(loadedUser)
        }
    }
}