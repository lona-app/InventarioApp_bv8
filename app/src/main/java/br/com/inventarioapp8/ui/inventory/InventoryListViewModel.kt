package br.com.inventarioapp8.ui.inventory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import br.com.inventarioapp8.InventarioApplication
import br.com.inventarioapp8.data.local.entity.Inventory
import br.com.inventarioapp8.data.repository.InventoryRepository

class InventoryListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: InventoryRepository
    val allInventories: LiveData<List<Inventory>>

    init {
        repository = (application as InventarioApplication).inventoryRepository
        allInventories = repository.allInventories
    }
}