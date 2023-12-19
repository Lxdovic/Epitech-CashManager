package com.example.moulamanagerclient.data.model.cart

import java.util.*

data class Cart(
	val id: Int,
	val userId: Int,
	val createdAt: Date,
	val checkedOut: Boolean
)
