package br.com.inventarioapp8.data.repository

import androidx.lifecycle.LiveData
import br.com.inventarioapp8.data.local.dao.UserDao
import br.com.inventarioapp8.data.local.entity.User

class UserRepository(private val userDao: UserDao) {

    // 👇 FUNÇÃO NOVA ADICIONADA 👇
    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    // ... (outras funções: findUserForLogin, insertUser, etc.)
    suspend fun findUserForLogin(identifier: String): User? {
        val id = identifier.toLongOrNull()
        if (id != null) {
            val userById = userDao.getUserById(id)
            if (userById != null) return userById
        }
        return userDao.getUserByUsername(identifier)
    }

    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    suspend fun getLastUserId(): Long? {
        return userDao.getLastUserId()
    }

    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    suspend fun getUserById(id: Long): User? {
        return userDao.getUserById(id)
    }

    suspend fun updateUser(user: User) {
        userDao.update(user)
    }
}