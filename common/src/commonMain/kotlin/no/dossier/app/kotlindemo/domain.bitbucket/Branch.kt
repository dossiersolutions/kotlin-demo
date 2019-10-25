package no.dossier.app.kotlindemo.domain.bitbucket

import kotlinx.serialization.Serializable
import com.soywiz.klock.DateTime

@Serializable
data class BitBucketBranch(
        val name: String,
        val url: String,
        // first: commit message, second: dateTime of commit, third: type of commit
        val lastCommitInfo: Triple<String, DateTime, String>
)