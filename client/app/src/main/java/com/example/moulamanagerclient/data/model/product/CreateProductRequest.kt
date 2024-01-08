package com.example.moulamanagerclient.data.model.product

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateProductRequest (
    @Json(name = "name")
    val name: String,

    @Json(name = "description")
    val description: String,

    @Json(name = "price")
    val price: Float,

    @Json(name = "barcode")
    val barcode: String,
)