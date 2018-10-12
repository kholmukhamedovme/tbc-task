package me.kholmukhamedov.tbc.models.data

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

/**
 * Data layer bean
 */
data class UserBean(@JsonProperty("id") var id: Int,
                    @JsonProperty("name") var name: String,
                    @JsonProperty("hasPhoto") var hasPhoto: Boolean,
                    @JsonProperty("status") var status: Boolean) : Serializable
