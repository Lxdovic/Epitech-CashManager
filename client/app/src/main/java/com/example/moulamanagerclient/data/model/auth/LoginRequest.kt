package com.example.moulamanagerclient.data.model.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest (
    @Json(name = "username")
    val username: String,
    @Json(name = "password")
    val password: String,
)