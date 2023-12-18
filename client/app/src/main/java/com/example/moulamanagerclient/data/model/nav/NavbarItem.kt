package com.example.moulamanagerclient.data.model.nav

import androidx.compose.ui.graphics.vector.ImageVector

data class NavbarItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
    val hasNews: Boolean = false
)