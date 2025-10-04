package br.com.inventarioapp8.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.inventarioapp8.data.local.entity.User

@Dao
interface UserDao {

    // 👇 QUERY MODIFICADA PARA EXCLUIR O ID 1000 👇
    @Query("SELECT * FROM users WHERE id != 1000 ORDER BY name ASC")
    fun getAllUsers(): LiveData<List<User>>

    // O resto das queries continua igual...
    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Long): User?

    @Query("SELECT * FROM users WHERE username = :username COLLATE NOCASE")
    suspend fun getUserByUsername(username: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Query("SELECT COUNT(*) FROM users")
    suspend fun count(): Int

    @Query("SELECT MAX(id) FROM users")
    suspend fun getLastUserId(): Long?
}