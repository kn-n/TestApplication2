package ru.kn_n.testapplication2.domain.containers

import kotlinx.serialization.Serializable

@Serializable
data class Repository(
    var full_name: String,
    var description : String? = null,
    var forks : String,
    var watchers : String,
    var created_at: String,
    var owner : Owner
)
