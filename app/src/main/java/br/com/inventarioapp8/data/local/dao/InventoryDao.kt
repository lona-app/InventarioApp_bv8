package br.com.inventarioapp8.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.inventarioapp8.data.local.entity.Inventory

@Dao
interface InventoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(inventory: Inventory)

    // 👇 MUDANÇA AQUI: de 'suspend fun' para retornar 'LiveData' 👇
    @Query("SELECT * FROM inventories ORDER BY date DESC")
    fun getAllInventories(): LiveData<List<Inventory>>

    @Query("SELECT * FROM inventories WHERE id LIKE :yearMonth || '%' ORDER BY id DESC LIMIT 1")
    suspend fun getLastInventoryOfMonth(yearMonth: String): Inventory?
}