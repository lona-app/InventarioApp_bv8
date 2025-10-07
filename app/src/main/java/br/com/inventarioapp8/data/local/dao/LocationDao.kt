package br.com.inventarioapp8.data.local.dao
import androidx.room.*
import br.com.inventarioapp8.data.local.entity.Location
@Dao
interface LocationDao {
    @Insert
    suspend fun insert(location: Location)
}