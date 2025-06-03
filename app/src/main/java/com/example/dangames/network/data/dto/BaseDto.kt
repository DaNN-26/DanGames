package com.example.dangames.network.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BaseDto<T : Dto>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>
)