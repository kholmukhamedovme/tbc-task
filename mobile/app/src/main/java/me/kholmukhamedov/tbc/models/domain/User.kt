package me.kholmukhamedov.tbc.models.domain

import java.io.Serializable

/**
 * Domain layer entity
 */
data class User(var id: Int,
                var name: String,
                var hasPhoto: Boolean,
                var status: Boolean) : Serializable
