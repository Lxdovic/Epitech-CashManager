package com.example.moulamanagerclient.shared

data class Route(val title: String, val path: String)
object AppRoutes {
    val LOGIN = Route("Login", "login")
    val CART = Route("Cart", "cart")
    val SCAN = Route("Scan", "scan")
    val PRODUCT = Route("Product", "product")
    val CHECKOUT = Route("Checkout", "checkout")
    val LOGOUT = Route("Logout", "logout")
}