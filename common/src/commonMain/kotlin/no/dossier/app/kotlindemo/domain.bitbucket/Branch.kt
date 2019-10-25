package no.dossier.app.kotlindemo.domain.bitbucket

import kotlinx.serialization.Serializable

@Serializable
data class BitBucketBranch(
        val name: String,
        val url: String
)