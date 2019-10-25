package no.dossier.app.kotlindemo.backend.bitbucket.auth

data class Consumer(val clientId: String, val clientSecret: String) {

    val authorizationUrl: String
        get() = "https://bitbucket.org/site/oauth2/access_token"

    val grantType = Pair("grant_type", "client_credentials")

    val authHeader = Pair("Authorization", "Basic ODdURFZhOWFRRk1XOTJRelozOmh3THNUZWhXa0Q2WkRHN1lYUmt5eFJ6YnhteXpZQXhS")

}