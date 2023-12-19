package com.example.moulamanagerclient.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Pagination<T>(
	@Json(name = "content")
	val content: List<T>
)