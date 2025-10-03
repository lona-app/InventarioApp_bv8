package br.com.inventarioapp8.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.inventarioapp8.data.local.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Long): User?

    @Query("SELECT * FROM users WHERE username = :username COLLATE NOCASE")
    suspend fun getUserByUsername(username: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT COUNT(*) FROM users")
    suspend fun count(): Int

    // 👇 QUERY NOVA ADICIONADA 👇
    @Query("SELECT MAX(id) FROM users")
    suspend fun getLastUserId(): Long?
}