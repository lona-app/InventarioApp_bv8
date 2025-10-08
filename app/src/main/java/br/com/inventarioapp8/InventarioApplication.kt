package br.com.inventarioapp8

import android.app.Application
import br.com.inventarioapp8.data.local.AppDatabase
import br.com.inventarioapp8.data.local.entity.Profile
import br.com.inventarioapp8.data.local.entity.User
import br.com.inventarioapp8.data.repository.InventoryRepository
import br.com.inventarioapp8.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class InventarioApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.IO)

    val database by lazy { AppDatabase.getDatabase(this) }
    val userRepository by lazy { UserRepository(database.userDao()) }
    // 👇 A LINHA QUE ESTAVA FALTANDO 👇
    val inventoryRepository by lazy { InventoryRepository(database.inventoryDao()) }

    override fun onCreate() {
        super.onCreate()
        seedDatabase()
    }

    private fun seedDatabase() {
        applicationScope.launch {
            val userDao = database.userDao()
            if (userDao.count() == 0) {
                val adminUser = User(
                    id = 1000, name = "Admin", username = "admin",
                    passwordHash = "@dmin@123", profile = Profile.ADMINISTRADOR,
                    isActive = true, creationDate = Date(), inactivationDate = null
                )
                userDao.insert(adminUser)
            }
        }
    }
}