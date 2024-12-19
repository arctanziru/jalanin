package com.example.goalsgetter.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.goalsgetter.features.auth.presentation.LoginScreen
import com.example.goalsgetter.features.auth.presentation.SignUpScreen
import com.example.goalsgetter.features.dashboard.presentation.DashboardScreen
import com.example.goalsgetter.features.goal.presentation.RoutineCreateEditScreen
import com.example.goalsgetter.features.goal.presentation.RoutineScreen
import com.example.goalsgetter.features.setting.presentation.SettingsScreen
import com.example.goalsgetter.features.splash.presentation.SplashScreen

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Dashboard : Screen("dashboard")
    data object RoutineList : Screen("routine")
    data object RoutineCreateEdit : Screen("routine_create_edit/{routineId}") {
        fun createRoute(routineId: String): String {
            return "routine_create_edit/$routineId"
        }
    }
    data object Login : Screen("login")
    data object SignUp : Screen("signup")
    data object Setting : Screen("setting")
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(navController)
        }

        composable(Screen.RoutineList.route) {
            RoutineScreen(navController)
        }

        composable(
            route = Screen.RoutineCreateEdit.route,
            arguments = listOf(
                navArgument("routineId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val routineId = backStackEntry.arguments?.getString("routineId")
            RoutineCreateEditScreen(
                navController = navController,
                routineId = if (routineId == "{routineId}") null else routineId
            )
        }


        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(navController)
        }

        composable(Screen.Setting.route) {
            SettingsScreen(navController)
        }
    }
}
