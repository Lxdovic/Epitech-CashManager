package com.example.moulamanagerclient.data.repositories

object ApiHeader {
    private var _accessToken:String? = null;
    val getAccessToken get() = _accessToken;

    fun setAccessToken (accessToken:String) {this._accessToken = accessToken};
}