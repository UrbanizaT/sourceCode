package com.example.urbanizat.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.icons.Icons
import androidx.compose.material3.icons.filled.Info
import androidx.compose.material3.icons.filled.SurveillanceCamera
import androidx.compose.material3.icons.filled.AttachMoney
import androidx.compose.material3.icons.filled.Poll
import androidx.compose.material3.icons.filled.EventAvailable
import androidx.compose.material3.icons.filled.Person
import androidx.compose.navigation.ComposableSingletons
import androidx.compose.navigation.NavHost
import androidx.compose.navigation.NavHostController
import androidx.compose.navigation.compose.Composable
import androidx.compose.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.urbanizat.navigation.AppDestinations
import com.example.urbanizat.ui.screens.CommunityScreen
import com.example.urbanizat.ui.screens.FinanceScreen
import com.example.urbanizat.ui.screens.IncidentScreen
import com.example.urbanizat.ui.screens.MaintenanceScreen
import com.example.urbanizat.ui.screens.ProfileScreen
import com.example.urbanizat.ui.screens.SurveyScreen

@Composable
fun MainAppNavHost(
    navController: NavHostController
) {
    // Main app navigation with bottom bar
    androidx.compose.material3.Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = navController.currentDestination?.route == AppDestinations.IncidentScreen.serialName(),
                    onClick = {
                        navController.navigate(AppDestinations.IncidentScreen.serialName()) {
                            // Pop up to the start destination of the graph to avoid building up a large back stack
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { androidx.compose.material3.Icon(Icons.Default.SurveillanceCamera, contentDescription = "Incidencias") },
                    label = { Text("Incidencias") }
                )

                NavigationBarItem(
                    selected = navController.currentDestination?.route == AppDestinations.CommunityScreen.serialName(),
                    onClick = {
                        navController.navigate(AppDestinations.CommunityScreen.serialName()) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { androidx.compose.material3.Icon(Icons.Default.Person, contentDescription = "Comunidad") },
                    label = { Text("Comunidad") }
                )

                NavigationBarItem(
                    selected = navController.currentDestination?.route == AppDestinations.FinanceScreen.serialName(),
                    onClick = {
                        navController.navigate(AppDestinations.FinanceScreen.serialName()) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { androidx.compose.material3.Icon(Icons.Default.AttachMoney, contentDescription = "Finanzas") },
                    label = { Text("Finanzas") }
                )

                NavigationBarItem(
                    selected = navController.currentDestination?.route == AppDestinations.SurveyScreen.serialName(),
                    onClick = {
                        navController.navigate(AppDestinations.SurveyScreen.serialName()) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { androidx.compose.material3.Icon(Icons.Default.Poll, contentDescription = "Encuestas") },
                    label = { Text("Encuestas") }
                )

                NavigationBarItem(
                    selected = navController.currentDestination?.route == AppDestinations.MaintenanceScreen.serialName(),
                    onClick = {
                        navController.navigate(AppDestinations.MaintenanceScreen.serialName()) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { androidx.compose.material3.Icon(Icons.Default.EventAvailable, contentDescription = "Mantenimiento") },
                    label = { Text("Mantenimiento") }
                )

                NavigationBarItem(
                    selected = navController.currentDestination?.route == AppDestinations.ProfileScreen.serialName(),
                    onClick = {
                        navController.navigate(AppDestinations.ProfileScreen.serialName()) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { androidx.compose.material3.Icon(Icons.Default.Info, contentDescription = "Perfil") },
                    label = { Text("Perfil") }
                )
            }
        }
    ) {
        // Padding for bottom navigation
        androidx.compose.foundation.layout.PaddingValues(bottom = 56.dp) {
            NavHost(
                navController = navController,
                startDestination = AppDestinations.IncidentScreen
            ) {
                composable<AppDestinations.IncidentScreen> {
                    IncidentScreen()
                }
                composable<AppDestinations.CommunityScreen> {
                    CommunityScreen()
                }
                composable<AppDestinations.FinanceScreen> {
                    FinanceScreen()
                }
                composable<AppDestinations.SurveyScreen> {
                    SurveyScreen()
                }
                composable<AppDestinations.MaintenanceScreen> {
                    MaintenanceScreen()
                }
                composable<AppDestinations.ProfileScreen> {
                    ProfileScreen()
                }
            }
        }
    }
}