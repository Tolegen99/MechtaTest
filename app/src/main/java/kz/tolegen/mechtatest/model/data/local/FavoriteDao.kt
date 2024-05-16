package kz.tolegen.mechtatest.model.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kz.tolegen.mechtatest.model.data.local.dto.FavoriteData

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite_table")
    suspend fun getAll(): List<FavoriteData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteData)

    @Query("DELETE FROM favorite_table WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM favorite_table WHERE code = :code")
    fun getByCode(code: String): FavoriteData?
}