package com.example.goalsgetter.features.auth.presentation

import CustomTextField
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goalsgetter.R
import com.example.goalsgetter.ui.components.CustomButton
import com.example.goalsgetter.ui.components.ButtonType
import com.example.goalsgetter.ui.theme.Black
import com.example.goalsgetter.ui.theme.Dark
import com.example.goalsgetter.ui.theme.Primary
import com.example.goalsgetter.ui.theme.White

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val signUpState by viewModel.signUpState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(signUpState.isSignUpSuccessful) {
        if (signUpState.isSignUpSuccessful) {
            Toast.makeText(context, "Sign Up Successful!", Toast.LENGTH_SHORT).show()
            navController.navigate("dashboard") {
                popUpTo("signup") { inclusive = true }
            }
        }
    }

    LaunchedEffect(signUpState.errorMessage) {
        signUpState.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
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
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "App Logo"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(R.string.signup),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Email Field
        CustomTextField(
            value = signUpState.email,
            onValueChange = { viewModel.updateEmail(it) },
            label = "Email",
            placeholder = "Enter your email",
            isPassword = false,
            errorMessage = if (signUpState.email.isEmpty()) "Email cannot be empty" else null,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Password Field
        CustomTextField(
            value = signUpState.password,
            onValueChange = { viewModel.updatePassword(it) },
            label = "Password",
            placeholder = "Enter your password",
            isPassword = true,
            errorMessage = if (signUpState.password.isEmpty()) "Password cannot be empty" else null,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Confirm Password Field
        CustomTextField(
            value = signUpState.confirmPassword,
            onValueChange = { viewModel.updateConfirmPassword(it) },
            label = "Confirm Password",
            placeholder = "Re-enter your password",
            isPassword = true,
            errorMessage = if (signUpState.confirmPassword.isEmpty()) "Confirm Password cannot be empty" else null,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Sign Up Button
        CustomButton(
            onClick = { viewModel.signUp() },
            color = Primary,
            textColor = White,
            text = stringResource(R.string.signup),
            buttonType = ButtonType.CONTAINED,
            icon = null,
            enabled = !signUpState.isLoading,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (signUpState.isLoading) {
            CircularProgressIndicator()
        }

        Spacer(modifier = Modifier.height(20.dp))

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
                onClick = { navController.navigate("signin") },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                Text(
                    text = stringResource(R.string.login),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Primary
                )
            }
        }
    }
}
