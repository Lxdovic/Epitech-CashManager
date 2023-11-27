package com.example.moulamanagerclient.ui.home

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavbarComponent(
    navController: NavController,
    gameViewModel: NavbarViewModel = viewModel()
) {

    NavigationBar {
        var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

        gameViewModel.items.forEachIndexed() { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.route)
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badgeCount != null) {
                                Badge {
                                    Text(text = item.badgeCount.toString())
                                }
                            }

                            else if (item.hasNews) {
                                Badge {

                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (selectedItemIndex == index) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title,
                        )
                    }
                }
            )
        }
    }


}