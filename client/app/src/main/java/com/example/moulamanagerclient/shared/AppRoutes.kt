package com.example.moulamanagerclient.shared

data class Route(val title: String, val path: String)
object AppRoutes {
    val cart = Route("Cart", "cart")
    val scan = Route("Scan", "scan")
    val checkout = Route("Checkout", "checkout")
    val logout = Route("Logout", "logout")
}