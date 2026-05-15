package com.example.urbanizat.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AppDestinations {

    // Authentication screens
    @Serializable
    data object Login : AppDestinations()

    @Serializable
    data object Register : AppDestinations()

    @Serializable
    data object Terms : AppDestinations()

    // Main app screens
    @Serializable
    data object MainApp : AppDestinations()

    @Serializable
    data object IncidentScreen : AppDestinations()

    @Serializable
    data object CommunityScreen : AppDestinations()

    @Serializable
    data object FinanceScreen : AppDestinations()

    @Serializable
    data object SurveyScreen : AppDestinations()

    @Serializable
    data object MaintenanceScreen : AppDestinations()

    @Serializable
    data object ProfileScreen : AppDestinations()
}


