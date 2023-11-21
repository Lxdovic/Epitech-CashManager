package org.moulamanager.client.features.basket.model


data class Basket (
    val id: Int,
    val user_id: Int,
    val is_checked_out: Boolean
)