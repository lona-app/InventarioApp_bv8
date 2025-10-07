package br.com.inventarioapp8.data.local.dao
import androidx.room.*
import br.com.inventarioapp8.data.local.entity.Sector
@Dao
interface SectorDao {
    @Insert
    suspend fun insert(sector: Sector)
}