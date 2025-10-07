package br.com.inventarioapp8.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "locations")
data class Location(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)