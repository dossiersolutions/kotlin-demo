package no.dossier.app.kotlindemo.backend.controllers

import no.dossier.app.kotlindemo.api.RestEndpoint
import no.dossier.app.kotlindemo.backend.docker.DockerSshUtil
import no.dossier.app.kotlindemo.domain.docker.DockerContainer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class DockerController {

    @GetMapping(RestEndpoint.Urls.GET_DOCKER_CONTAINERS)
    fun getAllDockerContainers(): List<DockerContainer> {
        return DockerSshUtil.getDockerContainers();
    }

    @GetMapping(RestEndpoint.Urls.STOP_DOCKER_CONTAINER)
    fun stopDockerContainer(@PathVariable containerId: String){
         DockerSshUtil.stopContainer(containerId);
    }
}