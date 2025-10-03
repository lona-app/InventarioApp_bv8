package br.com.inventarioapp8.data.repository

import br.com.inventarioapp8.data.local.dao.UserDao
import br.com.inventarioapp8.data.local.entity.User

class UserRepository(private val userDao: UserDao) {

    suspend fun findUserForLogin(identifier: String): User? {
        val id = identifier.toLongOrNull()
        if (id != null) {
            val userById = userDao.getUserById(id)
            if (userById != null) return userById
        }
        return userDao.getUserByUsername(identifier)
    }

    // 👇 FUNÇÕES NOVAS ADICIONADAS 👇
    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    suspend fun getLastUserId(): Long? {
        return userDao.getLastUserId()
    }

    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }
}