package com.example.dangames.db.data.converters

import androidx.room.TypeConverter
import com.example.dangames.network.domain.model.Model

class FavoritesConverters {

    @TypeConverter
    fun fromGenres(genres: List<Model.Genre>) =
        genres.joinToString(",") { "${it.id}:${it.name}" }

    @TypeConverter
    fun toGenres(genres: String) =
        genres.split(",")
            .map {
                it.split(":").let { (id, name) ->
                    Model.Genre(id.toInt(), name)
                }
            }
}