package com.example.moulamanagerclient.ui.navbar

import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moulamanagerclient.shared.AppRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavbarComponent(
	navController: NavController,
	navViewModel: NavbarViewModel = viewModel()
) {
	NavigationBar {
		navViewModel.items.forEachIndexed { index, item ->
			NavigationBarItem(
				colors = NavigationBarItemDefaults
					.colors(
						indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(LocalAbsoluteTonalElevation.current)
					),
				selected = navViewModel.selectedItemIndex.intValue == index,
				onClick = {
					navViewModel.selectedItemIndex.intValue = index
					if (item.route == AppRoutes.LOGOUT.path) {
						navViewModel.onLogoutClick(navController)
					} else {
						navController.navigate(item.route)
					}
				},
				icon = {
					BadgedBox(
						badge = {
							if (item.badgeCount != null) {
								Badge {
									Text(text = item.badgeCount.toString())
								}
							} else if (item.hasNews) {
								Badge {

								}
							}
						}
					) {
						Image(
							painter = painterResource(
								id = if (navViewModel.selectedItemIndex.intValue == index) {
									item.selectedIcon
								} else {
									item.unselectedIcon
								}
							),
							contentDescription = item.title
						)
					}
				},
			)
		}
	}
}