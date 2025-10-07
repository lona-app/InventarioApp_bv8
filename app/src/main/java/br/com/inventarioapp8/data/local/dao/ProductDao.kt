package br.com.inventarioapp8.data.local.dao
import androidx.room.*
import br.com.inventarioapp8.data.local.entity.Product
@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)
}