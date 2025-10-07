package br.com.inventarioapp8.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "products")
data class Product(
    @PrimaryKey val code: String,
    val description: String,
    var stockQuantity: Int = 0
)