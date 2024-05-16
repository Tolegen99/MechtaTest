package kz.tolegen.mechtatest.model.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import kz.tolegen.mechtatest.model.data.local.dto.FavoriteData

@Database(entities = [FavoriteData::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
}
