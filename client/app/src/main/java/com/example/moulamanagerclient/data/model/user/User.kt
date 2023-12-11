package com.example.moulamanagerclient.data.model.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class UserLoginResponse (
    @Json(name = "id")
    val id: Number,
    @Json(name = "username")
    val username: String,
    @Json(name = "email")
    val email: String,
)
