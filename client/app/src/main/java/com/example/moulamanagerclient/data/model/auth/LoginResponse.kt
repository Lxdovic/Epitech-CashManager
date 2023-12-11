package com.example.moulamanagerclient.data.model.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "id")
    var id: Int,

    @Json(name = "username")
    var username: String,

    @Json(name = "email")
    var email: String
)

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "token")
    var token: String,

    @Json(name = "user")
    var user: User
)