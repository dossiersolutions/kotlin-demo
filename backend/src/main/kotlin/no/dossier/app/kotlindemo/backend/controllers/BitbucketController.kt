package no.dossier.app.kotlindemo.backend.controllers

import no.dossier.app.kotlindemo.api.RestEndpoint
import no.dossier.app.kotlindemo.backend.bitbucket.client.JsonUtil
import no.dossier.app.kotlindemo.backend.bitbucket.client.getAllBranches
import no.dossier.app.kotlindemo.backend.bitbucket.client.startPipelineBuild
import no.dossier.app.kotlindemo.domain.bitbucket.BitBucketBranch
import no.dossier.app.kotlindemo.util.parseBitBucketDateTime
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

import org.springframework.web.bind.annotation.RestController

@RestController
class BitbucketController {

    @GetMapping(RestEndpoint.Urls.GET_BB_BRANCHES)
    fun getAllBitbucketBranches(): List<BitBucketBranch> {
        val rawData = getAllBranches()
        val ju = JsonUtil(rawData)
        val branchesJson = ju.split("$.values")

        val branches = ArrayList<BitBucketBranch>()
        for (branchJson in branchesJson) {
            val branchJu = JsonUtil(branchJson)
            val branchName = branchJu.getJsonPathString("$.name", "")
            val branchUrl = branchJu.getJsonPathString("$.links.html.href", "")

            // last commit info
            val stringDate: String = branchJu.getJsonPathString("$.target.date", "")
            val dateTime = parseBitBucketDateTime(stringDate)
            val message = branchJu.getJsonPathString("$.target.message", "")
            val type = branchJu.getJsonPathString("$.target.type", "")

            val latestCommitInfo = Triple(message, dateTime.unixMillisLong, type)
            val bitBucketBranch = BitBucketBranch(branchName, branchUrl, latestCommitInfo)
            branches.add(bitBucketBranch)
        }
        return branches
    }

    @GetMapping(RestEndpoint.Urls.START_BB_PIPELINE)
    fun getStartPipelineBuild(@PathVariable branchName: String){
        startPipelineBuild(branchName)
    }
}