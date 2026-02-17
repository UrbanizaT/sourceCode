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
}


