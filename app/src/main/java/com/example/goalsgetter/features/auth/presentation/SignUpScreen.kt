package com.example.goalsgetter.features.auth.presentation

import CustomTextField
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goalsgetter.R
import com.example.goalsgetter.ui.components.ButtonType
import com.example.goalsgetter.ui.components.CustomButton
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
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                        modifier = Modifier.size(120.dp)

            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = stringResource(R.string.signup),
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        CustomTextField(
            value = signUpState.fullName,
            onValueChange = { viewModel.updateFullName(it) },
            label = stringResource(R.string.fullNameLabel),
            placeholder = stringResource(R.string.fullNamePlaceholder),
            isPassword = false,
            errorMessage = if (signUpState.fullName.isEmpty()) stringResource(R.string.fullNameEmptyError) else null,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Email Field
        CustomTextField(
            value = signUpState.email,
            onValueChange = { viewModel.updateEmail(it) },
            label = stringResource(R.string.emailLabel),
            placeholder = stringResource(R.string.emailPlaceholder),
            isPassword = false,
            errorMessage = if (signUpState.email.isEmpty()) stringResource(R.string.emailEmptyError) else null,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Password Field
        CustomTextField(
            value = signUpState.password,
            onValueChange = { viewModel.updatePassword(it) },
            label = stringResource(R.string.passwordLabel),
            placeholder = stringResource(R.string.passwordPlaceholder),
            isPassword = true,
            errorMessage = if (signUpState.password.isEmpty()) stringResource(R.string.passwordEmptyError) else null,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Confirm Password Field
        CustomTextField(
            value = signUpState.confirmPassword,
            onValueChange = { viewModel.updateConfirmPassword(it) },
            label = stringResource(R.string.confirmPasswordLabel),
            placeholder = stringResource(R.string.confirmPasswordPlaceholder),
            isPassword = true,
            errorMessage = if (signUpState.confirmPassword.isEmpty()) stringResource(R.string.confirmPasswordEmptyError) else null,
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
                onClick = { navController.navigate("login") },
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
