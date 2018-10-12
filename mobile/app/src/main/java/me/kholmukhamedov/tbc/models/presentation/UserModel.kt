package me.kholmukhamedov.tbc.models.presentation

import me.kholmukhamedov.tbc.data.remote.ApiService
import java.io.Serializable

/**
 * Domain layer entity
 */
data class UserModel(var id: Int,
                     var name: String,
                     var hasPhoto: Boolean,
                     var status: Boolean) : Serializable {

    fun getPhotoUrl() = ApiService.HOST.plus("/photos/").plus(id).plus(".jpg")

}
