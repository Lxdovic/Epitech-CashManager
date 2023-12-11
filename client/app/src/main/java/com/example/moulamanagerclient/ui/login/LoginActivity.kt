package com.example.moulamanagerclient.ui.login

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moulamanagerclient.R
import com.example.moulamanagerclient.data.model.auth.LoginResponse
import com.example.moulamanagerclient.ui.theme.Colors


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginActivity(viewModel: LoginViewModel = viewModel()) {
    val loginResult by viewModel.loginResult.collectAsState()
    val inputPassword by viewModel.inputPassword.collectAsState()
    val inputUsername by viewModel.inputUsername.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors =  TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Colors.BLACK_0,
                    titleContentColor = Colors.WHITE,
                ),
                title = {
                    Text("Top app bar")
                }
            )
        },
        content = {paddingValues ->
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Colors.BLACK_1
            ) {
                Column {
                    OutlinedTextField(
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Colors.BLACK_4,
                            focusedBorderColor = colorResource(id = R.color.black_4),
                            unfocusedBorderColor = colorResource(id = R.color.black_4),
                        ),
                        value = inputUsername,
                        onValueChange = { viewModel.setUsername(it)},
                        label = { Text(text = "Username") },
                    )
                    OutlinedTextField(
                        value = inputPassword,
                        onValueChange = { viewModel.setPassword(it)},
                        label = { Text(text = "Password") },
                    )
                    OutlinedButton(
                        shape = RoundedCornerShape(10),
                        border = BorderStroke(1.dp, Colors.YELLOW_1),
                        onClick = {
                            viewModel.performLogin(inputUsername, inputPassword)
                        }
                    ) {
                        Text("Login button for testing purpose", color = Colors.WHITE)
                    }
                    loginResult?.let { result: LoginResponse ->
                        Text("Login Result: ${result.token}", color = Colors.WHITE) // Replace someProperty with the actual property in LoginResponse
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginActivity()
}
