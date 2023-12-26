package com.example.moulamanagerclient.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Pagination<T>(
	@Json(name = "content")
	val content: List<T>,
	val pageable: Pageable,
	@Json(name = "last")
	val last: Boolean,
	@Json(name = "totalElements")
	val totalElements: Int,
	@Json(name = "totalPages")
	val totalPages: Int,
	@Json(name = "size")
	val size: Int,
	@Json(name = "number")
	val number: Int,
	@Json(name = "sort")
	val sort: Sort,
	@Json(name = "first")
	val first: Boolean,
	@Json(name = "numberOfElements")
	val numberOfElements: Int,
	@Json(name = "empty")
	val empty: Boolean,
)

@JsonClass(generateAdapter = true)
data class Pageable(
	@Json(name = "pageNumber")
	val pageNumber: Int,
	@Json(name = "pageSize")
	val pageSize: Int,
	@Json(name = "sort")
	val sort: Sort,
	@Json(name = "offset")
	val offset: Int,
	@Json(name = "paged")
	val paged: Boolean,
	@Json(name = "unpaged")
	val unpaged: Boolean,
)

@JsonClass(generateAdapter = true)
data class Sort(
	@Json(name = "empty")
	val empty: Boolean,
	@Json(name = "sorted")
	val sorted: Boolean,
	@Json(name = "unsorted")
	val unsorted: Boolean,
)
