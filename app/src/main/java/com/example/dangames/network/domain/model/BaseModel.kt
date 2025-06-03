package com.example.dangames.network.domain.model

data class BaseModel<T : Model>(
    val count: Int,
    val results: List<T>
)