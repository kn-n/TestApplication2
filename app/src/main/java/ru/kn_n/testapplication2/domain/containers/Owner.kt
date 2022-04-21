package ru.kn_n.testapplication2.domain.containers

import kotlinx.serialization.Serializable

@Serializable
data class Owner(
    var login : String,
    var avatar_url : String
)
