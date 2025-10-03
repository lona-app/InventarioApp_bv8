package br.com.inventarioapp8.data.local

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.inventarioapp8.data.local.converter.Converters
import br.com.inventarioapp8.data.local.dao.UserDao
import br.com.inventarioapp8.data.local.entity.Profile
import br.com.inventarioapp8.data.local.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "inventario_database"
                )
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database.userDao())
                }
            }
        }

        suspend fun populateDatabase(userDao: UserDao) {
            if (userDao.count() == 0) {
                val adminUser = User(
                    id = 1000,
                    name = "Admin",
                    username = "admin",
                    passwordHash = "@dmin@123",
                    profile = Profile.ADMINISTRADOR, // Perfil atualizado
                    isActive = true,
                    creationDate = Date(),
                    inactivationDate = null
                )
                userDao.insert(adminUser)
            }
        }
    }
}