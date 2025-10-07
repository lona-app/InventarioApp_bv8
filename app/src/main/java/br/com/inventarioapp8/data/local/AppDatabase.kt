package br.com.inventarioapp8.data.local
import android.content.Context
import androidx.room.*
import br.com.inventarioapp8.data.local.converter.Converters
import br.com.inventarioapp8.data.local.dao.*
import br.com.inventarioapp8.data.local.entity.*
@Database(
    entities = [User::class, Inventory::class, Sector::class, Location::class, Product::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun inventoryDao(): InventoryDao
    abstract fun sectorDao(): SectorDao
    abstract fun locationDao(): LocationDao
    abstract fun productDao(): ProductDao
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
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}