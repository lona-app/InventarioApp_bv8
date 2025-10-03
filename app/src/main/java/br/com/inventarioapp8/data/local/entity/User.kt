package br.com.inventarioapp8.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false) // Desativado para podermos definir o ID do Admin
    var id: Long,
    val name: String,
    val username: String,
    val passwordHash: String,
    val profile: Profile,
    val isActive: Boolean,
    val creationDate: Date,
    var inactivationDate: Date?
)