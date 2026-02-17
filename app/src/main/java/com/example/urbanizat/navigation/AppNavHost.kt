package com.example.urbanizat.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.urbanizat.ui.screens.LoginScreen
import com.example.urbanizat.ui.screens.RegisterScreen

@Composable
fun AppNavHost() {

//Crea el controlador de navegacion y se guarda en memoria
    val navController = rememberNavController()

//Define quien controla la navegacion, pantalla inicial y renderiza el composable correspondiente
    NavHost(
        navController = navController,
        startDestination = AppDestinations.Login) {

//Si el backstack contiene Login renderiza el LoginScreen

        composable<AppDestinations.Login> {
            LoginScreen(
                onNavigateToRegister = {

//Serializa register, lo mete en backstack, navhost detecta el cambio y renderiza el Register Screen
                    navController.navigate(route =AppDestinations.Register)
                }
            )
        }

        composable<AppDestinations.Register> {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(route = AppDestinations.Login)
                },
                onNavigateToTerms = {
                    navController.navigate(route= AppDestinations.Terms)
                }
            )
        }
    }
}

