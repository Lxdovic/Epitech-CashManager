package com.example.moulamanagerclient.data.model.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse (
    @Json(name = "message")
    var message: String,
    @Json(name = "status")
    var status: Boolean
    )