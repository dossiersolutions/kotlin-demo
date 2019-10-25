package no.dossier.app.kotlindemo.api

import no.dossier.app.kotlindemo.config.AppConfig

enum class Topic(val value: String) {
    UserInfo(Urls.USER_INFO),
    Chat(Urls.CHAT);

    class Urls {
        companion object {
            const val USER_INFO = "/topic/userinfo"
            const val CHAT = "/topic/chat"
        }
    }
}

enum class WsEndpoint(val value: String) {
    SendChatMessage(Urls.SEND_CHAT_MESSAGE);

    class Urls {
        companion object {
            const val SEND_CHAT_MESSAGE = "/chat"
        }
    }

    val prefixedUrl = AppConfig.ApplicationDestinationPrefix + value
}

enum class RestEndpoint(val value: String) {
    GetUser(Urls.GET_USER),
    GetAllConnections(Urls.GET_ALL_CONNECTIONS),
    GetAllDockerContainers(Urls.GET_DOCKER_CONTAINERS),
    KillDockerContainer(Urls.KILL_DOCKER_CONTAINER);

    class Urls {
        companion object {
            const val GET_USER = "/users/{userId}"
            const val GET_ALL_CONNECTIONS = "/connections/all"
            const val GET_DOCKER_CONTAINERS = "/dockercontainers/all"
            const val KILL_DOCKER_CONTAINER = "/dockercontainers/{containerId}"
        }
    }
}