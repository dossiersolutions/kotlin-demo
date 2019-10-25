package no.dossier.app.kotlindemo.backend.controllers

import no.dossier.app.kotlindemo.api.RestEndpoint
import no.dossier.app.kotlindemo.domain.bitbucket.BitBucketBranch
import org.springframework.web.bind.annotation.GetMapping

import org.springframework.web.bind.annotation.RestController

@RestController
class BitbucketController {

    @GetMapping(RestEndpoint.Urls.GET_BB_BRANCHES)
    fun getAllDockerContainers(): List<BitBucketBranch> {
        return emptyList()
    }
}