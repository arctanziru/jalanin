package com.example.goalsgetter.features.auth.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goalsgetter.R
import com.example.goalsgetter.ui.components.CustomButton
import com.example.goalsgetter.ui.components.ButtonType
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
                popUpTo("signup") {
                    inclusive = true
                } // Clears the back stack
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
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = stringResource(R.string.signup),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = signUpState.email,
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text("Email") },
            isError = signUpState.email.isEmpty(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = signUpState.password,
            onValueChange = { viewModel.updatePassword(it) },
            label = { Text("Password") },
            isError = signUpState.password.isEmpty(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = signUpState.confirmPassword,
            onValueChange = { viewModel.updateConfirmPassword(it) },
            label = { Text("Confirm Password") },
            isError = signUpState.confirmPassword.isEmpty(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.alreadyHaveAccount),
                style = MaterialTheme.typography.bodyMedium,
                color = Primary,
                modifier = Modifier.padding(end = 4.dp)
            )
            TextButton(
                onClick = { navController.navigate("login") },
                modifier = Modifier.padding(0.dp)
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
