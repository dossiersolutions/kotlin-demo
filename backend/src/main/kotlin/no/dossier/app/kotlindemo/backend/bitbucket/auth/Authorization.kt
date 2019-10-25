package no.dossier.app.kotlindemo.backend.bitbucket.auth

import kotlinx.serialization.*

@Serializable
data class Authorization(
        @SerialName("access_token") val accessToken: String,
        @SerialName("scopes") val scopes: String,
        @SerialName("expires_in") val expiresIn: Int,
        @SerialName("refresh_token") val refreshToken: String,
        @SerialName("token_type") val tokenType: String)