package com.example.dangames.db.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.dangames.network.domain.model.Model

@Entity(tableName = "favorite_game")
data class FavoriteGameEntity(
    @PrimaryKey
    val id: Int = 0,
    val name: String,
    val metacritic: Int? = null,
    @ColumnInfo(name = "background_image")
    val backgroundImage: String,
    val genres: List<Model.Genre>
)
