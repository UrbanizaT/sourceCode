package com.example.urbanizat.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AppDestinations {

    @Serializable
    data object Login : AppDestinations()

    @Serializable
    data object Register : AppDestinations()

    @Serializable
    data object Terms : AppDestinations()

    @Serializable
    data object Home : AppDestinations()

    @Serializable
    data object Incidents : AppDestinations()

    @Serializable
    data object CreateIncident : AppDestinations()

    @Serializable
    data object Calendario : AppDestinations()

    @Serializable
    data object Tablon : AppDestinations()


    @Serializable
    data object Normativa : AppDestinations()

    @Serializable
    data object Finanzas : AppDestinations()

    @Serializable
    data object Comunidad : AppDestinations()
}