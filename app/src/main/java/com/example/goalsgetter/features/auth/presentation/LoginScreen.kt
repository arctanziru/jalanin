package com.example.goalsgetter.features.auth.presentation

import CustomTextField
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                popUpTo(navController.graph.id) { inclusive = true }
            }
        }
    }

    LaunchedEffect(loginState.errorMessage) {
        loginState.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.resetError()
        }
    }

    // Remove extra padding from Column
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Logo
        Box(
            modifier = Modifier
                .size(100.dp)
                .padding(16.dp), // Add padding here instead
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                modifier = Modifier.size(120.dp),
                contentDescription = "App Logo"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center, fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Input Fields with Padding
        CustomTextField(
            value = loginState.email,
            onValueChange = { viewModel.updateEmail(it) },
            label = stringResource(R.string.emailLabel),
            placeholder = stringResource(R.string.emailPlaceholder),
            isPassword = false,
            errorMessage = if (loginState.email.isEmpty()) stringResource(R.string.emailEmptyError) else null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp) // Add padding here
        )

        Spacer(modifier = Modifier.height(12.dp))

        CustomTextField(
            value = loginState.password,
            onValueChange = { viewModel.updatePassword(it) },
            label = stringResource(R.string.passwordLabel),
            placeholder = stringResource(R.string.passwordPlaceholder),
            isPassword = true,
            errorMessage = if (loginState.password.isEmpty()) stringResource(R.string.passwordEmptyError) else null,
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Bottom Text (Signup & Forgot Password)
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.didntHaveAccount),
                style = MaterialTheme.typography.bodyMedium,
                color = Dark
            )
            Spacer(modifier = Modifier.width(4.dp))
            TextButton(
                onClick = { navController.navigate("signup") },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.height(IntrinsicSize.Min)
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
