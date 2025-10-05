package br.com.inventarioapp8.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "inventories")
data class Inventory(
    @PrimaryKey
    val id: String,
    val name: String,
    val date: Date
)