package br.com.inventarioapp8.data.repository

import androidx.lifecycle.LiveData
import br.com.inventarioapp8.data.local.dao.InventoryDao
import br.com.inventarioapp8.data.local.entity.Inventory

class InventoryRepository(private val inventoryDao: InventoryDao) {

    // 👇 NOVA PROPRIEDADE PARA ACESSAR A LISTA OBSERVÁVEL 👇
    val allInventories: LiveData<List<Inventory>> = inventoryDao.getAllInventories()

    suspend fun insert(inventory: Inventory) {
        inventoryDao.insert(inventory)
    }

    suspend fun getLastInventoryOfMonth(yearMonth: String): Inventory? {
        return inventoryDao.getLastInventoryOfMonth(yearMonth)
    }
}