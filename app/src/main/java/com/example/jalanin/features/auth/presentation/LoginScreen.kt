package com.example.jalanin.features.auth.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.jalanin.ui.components.CustomButton
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jalanin.ui.components.ButtonType

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val loginState by viewModel.loginState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(loginState.isLoginSuccessful) {
        if (loginState.isLoginSuccessful) {
            Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = loginState.email,
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text("Email") },
            placeholder = { Text("Enter your email") },
            isError = loginState.email.isEmpty(), // Basic validation example
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = loginState.password,
            onValueChange = { viewModel.updatePassword(it) },
            label = { Text("Password") },
            placeholder = { Text("Enter your password") },
            isError = loginState.password.isEmpty(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomButton(
            onClick = { viewModel.login() },
            color = MaterialTheme.colorScheme.primary,
            textColor = Color.White,
            text = "Login",
            buttonType = ButtonType.CONTAINED,
            icon = null,
            enabled = !loginState.isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        loginState.errorMessage?.let {
            Text(it, color = Color.Red)
        }

        if (loginState.isLoading) {
            CircularProgressIndicator()
        }
    }
}
