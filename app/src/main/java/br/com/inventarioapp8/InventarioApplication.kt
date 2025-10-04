package br.com.inventarioapp8

import android.app.Application
import br.com.inventarioapp8.data.local.AppDatabase
import br.com.inventarioapp8.data.repository.UserRepository

class InventarioApplication : Application() {
    // Usando 'lazy' para que o banco e o repositório só sejam criados quando
    // forem acessados pela primeira vez.
    val database by lazy { AppDatabase.getDatabase(this) }
    val userRepository by lazy { UserRepository(database.userDao()) }
}