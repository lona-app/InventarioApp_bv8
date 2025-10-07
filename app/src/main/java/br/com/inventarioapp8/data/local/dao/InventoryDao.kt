package br.com.inventarioapp8.data.local.dao
import androidx.room.*
import br.com.inventarioapp8.data.local.entity.Inventory
@Dao
interface InventoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(inventory: Inventory)
    @Query("SELECT * FROM inventories ORDER BY date DESC")
    suspend fun getAllInventories(): List<Inventory>
    @Query("SELECT * FROM inventories WHERE id LIKE :yearMonth || '%' ORDER BY id DESC LIMIT 1")
    suspend fun getLastInventoryOfMonth(yearMonth: String): Inventory?
}