package com.example.moulamanagerclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
//import com.example.moulamanagerclient.ui.login.LoginActivity
import com.example.moulamanagerclient.ui.navbar.NavigationHost
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.moulamanagerclient.data.network.AuthInterceptor
import com.example.moulamanagerclient.ui.auth.login.LoginActivity
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
				// A surface container using the 'background' color from the theme
				Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
					if (authInterceptor.isLoggedIn()) {
						NavigationHost(navigationController = rememberNavController())
					} else {
						LoginActivity()
					}
				}
			}
		}
	}
}