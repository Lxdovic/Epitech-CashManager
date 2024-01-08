package com.example.moulamanagerclient.data.model.nav

data class NavbarItem(
	val title: String,
	val route: String,
	val selectedIcon: Int,
	val unselectedIcon: Int,
	val badgeCount: Int? = null,
	val hasNews: Boolean = false
)