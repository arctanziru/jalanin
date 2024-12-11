package com.example.goalsgetter.features.auth.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.goalsgetter.ui.components.CustomButton
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goalsgetter.R
import com.example.goalsgetter.ui.components.ButtonType
import com.example.goalsgetter.ui.theme.Dark
import com.example.goalsgetter.ui.theme.Primary
import com.example.goalsgetter.ui.theme.White

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
            navController.navigate("dashboard") {
                popUpTo("login") { inclusive = true } // Clears the back stack
            }
        }
    }

    LaunchedEffect(loginState.errorMessage) {
        loginState.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Box(
            modifier = Modifier
                .size(100.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with your logo resource
                contentDescription = "App Logo"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = loginState.email,
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text("Email") },
            isError = loginState.email.isEmpty(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = loginState.password,
            onValueChange = { viewModel.updatePassword(it) },
            label = { Text("Password") },
            isError = loginState.password.isEmpty(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Login Button
        CustomButton(
            onClick = { viewModel.login() },
            color = Primary,
            textColor = White,
            text = stringResource(R.string.login),
            buttonType = ButtonType.CONTAINED,
            icon = null,
            enabled = !loginState.isLoading,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Divider with "Atau"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = Dark)
            Text(
                text = stringResource(R.string.or),
                style = MaterialTheme.typography.bodyMedium,
                color = Dark,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            HorizontalDivider(modifier = Modifier.weight(1f), color = Dark)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Google Login Button
        CustomButton(
            onClick = { /* Handle Google login */ },
            color = Dark,
            textColor = Dark,
            text = stringResource(R.string.loginWithGoogle),
            buttonType = ButtonType.OUTLINED,
            enabled = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.didntHaveAccount),
                style = MaterialTheme.typography.bodyMedium,
                color = Dark,
                modifier = Modifier.padding(end = 4.dp)
            )
            TextButton(
                onClick = { navController.navigate("signup") },
                modifier = Modifier.padding(0.dp)
            ) {
                Text(
                    text = stringResource(R.string.signup),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Primary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.forgotPassword),
            style = MaterialTheme.typography.bodyMedium,
            color = Primary,
            textAlign = TextAlign.Center
        )
    }
}
