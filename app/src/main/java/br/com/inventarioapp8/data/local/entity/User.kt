package br.com.inventarioapp8.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false)
    var id: Long,
    var name: String,
    var username: String,
    var passwordHash: String,
    var profile: Profile,
    var isActive: Boolean,
    val creationDate: Date,
    var inactivationDate: Date?
)