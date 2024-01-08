package com.example.moulamanagerclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.moulamanagerclient.data.network.AuthInterceptor
import com.example.moulamanagerclient.shared.MyAppTheme
import com.example.moulamanagerclient.ui.navbar.NavigationHost
import com.example.moulamanagerclient.ui.theme.MoulamanagerclientTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	@Inject
	lateinit var authInterceptor: AuthInterceptor

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MoulamanagerclientTheme {
				MyAppTheme {
					val navController = rememberNavController()
					NavigationHost(navController, authInterceptor)
				}
			}
		}
	}
}