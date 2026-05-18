package com.example.urbanizat.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.urbanizat.ui.screens.CalendarioScreen
import com.example.urbanizat.ui.screens.CreateIncidentScreen
import com.example.urbanizat.ui.screens.HomeScreen
import com.example.urbanizat.ui.screens.IncidentsScreen
import com.example.urbanizat.ui.screens.LoginScreen
import com.example.urbanizat.ui.screens.RegisterScreen
import com.example.urbanizat.ui.screens.TablonScreen
import com.example.urbanizat.ui.screens.NormativaScreen
import com.example.urbanizat.ui.screens.FinanzasScreen
import com.example.urbanizat.ui.screens.ComunidadScreen
import com.example.urbanizat.ui.screens.TermsScreen
import com.example.urbanizat.ui.viewmodels.IncidentViewModel

@Composable
fun AppNavHost() {

    val navController = rememberNavController()
    val incidentViewModel: IncidentViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.Login
    ) {

        composable<AppDestinations.Login> {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(route = AppDestinations.Register)
                },
                onNavigateToHome = {
                    navController.navigate(route = AppDestinations.Home) {
                        popUpTo(AppDestinations.Login) { inclusive = true }
                    }
                }
            )
        }

        composable<AppDestinations.Register> {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(route = AppDestinations.Login)
                },
                onNavigateToTerms = {
                    navController.navigate(route = AppDestinations.Terms)
                }
            )
        }

        composable<AppDestinations.Terms> {
            TermsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<AppDestinations.Home> {
            HomeScreen(
                onNavigateToIncidencias = {
                    navController.navigate(route = AppDestinations.Incidents)
                },
                onNavigateToCalendario = {
                    navController.navigate(route = AppDestinations.Calendario)
                },
                onNavigateToTablon = {
                    navController.navigate(route = AppDestinations.Tablon)
                },
                onNavigateToNormativa = {
                    navController.navigate(route = AppDestinations.Normativa)
                },
                onNavigateToFinanzas = {
                    navController.navigate(route = AppDestinations.Finanzas)
                },
                onNavigateToComunidad = {
                    navController.navigate(route = AppDestinations.Comunidad)
                },
                onLogout = {
                    navController.navigate(route = AppDestinations.Login) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable<AppDestinations.Incidents> {
            IncidentsScreen(
                incidentViewModel = incidentViewModel,
                onNavigateToCreateIncident = {
                    navController.navigate(route = AppDestinations.CreateIncident)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<AppDestinations.CreateIncident> {
            CreateIncidentScreen(
                incidentViewModel = incidentViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<AppDestinations.Calendario> {
            CalendarioScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<AppDestinations.Tablon> {
            TablonScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<AppDestinations.Normativa> {
            NormativaScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<AppDestinations.Finanzas> {
            FinanzasScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<AppDestinations.Comunidad> {
            ComunidadScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}