package kz.tolegen.mechtatest.model.data.local.dto

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favorite_table")
@Immutable
data class FavoriteData(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val code: String,
    val title: String,
)