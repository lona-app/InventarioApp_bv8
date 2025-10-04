package br.com.inventarioapp8.ui.users

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import br.com.inventarioapp8.data.local.AppDatabase
import br.com.inventarioapp8.data.local.entity.User
import br.com.inventarioapp8.data.repository.UserRepository

class UserListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    val allUsers: LiveData<List<User>>

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        allUsers = repository.allUsers
    }
}