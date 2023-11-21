package com.example.moulamanagerclient.data.network

import javax.inject.Inject
import com.example.moulamanagerclient.di.EmptyString

class ApiHeader {
    class PublicApiHeader  @Inject constructor( @EmptyString var apiKey: String)
}