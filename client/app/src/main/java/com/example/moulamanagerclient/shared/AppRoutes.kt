package com.example.moulamanagerclient.shared

data class Route(val title: String, val path: String)
object AppRoutes {
	val LOGIN = Route("Login", "login")
	val REGISTER = Route("Register", "register")
	val CART = Route("Cart", "cart")
	val SCAN = Route("Scan", "scan")
	val PRODUCT = Route("Product", "product")
	val LOGOUT = Route("Logout", "logout")
}