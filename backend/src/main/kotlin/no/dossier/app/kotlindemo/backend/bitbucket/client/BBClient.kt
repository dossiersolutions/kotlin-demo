package no.dossier.app.kotlindemo.backend.bitbucket.client

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import no.dossier.app.kotlindemo.backend.bitbucket.auth.Authorization
import no.dossier.app.kotlindemo.backend.bitbucket.auth.Consumer
import org.apache.commons.io.IOUtils
import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicNameValuePair
import java.util.*


fun getAccessToken(consumer: Consumer): Authorization? {
    val client = HttpClientBuilder.create().build()
    val request = HttpPost(consumer.authorizationUrl)

    request.addHeader("Content-Type", "application/x-www-form-urlencoded")
    request.addHeader(consumer.authHeader.first, consumer.authHeader.second)

    val params = ArrayList<NameValuePair>()
    params.add(BasicNameValuePair(consumer.grantType.first, consumer.grantType.second))
    request.entity = UrlEncodedFormEntity(params)

    val response: CloseableHttpResponse? = client.execute(request)
    val rawResponse: String = IOUtils.toString(response?.entity?.content, "UTF-8")
    val json = Json(JsonConfiguration.Stable)
    println("rawResponse")
    println(rawResponse)
    return json.parse(Authorization.serializer(), rawResponse)
}

fun startPipelineBuild(branchName: String) {
    val consumer = Consumer("", "")
    val authorization = getAccessToken(consumer)

    val reqBody = "{\n" +
            "    \"target\": {\n" +
            "      \"type\": \"pipeline_ref_target\",\n" +
            "      \"ref_type\": \"branch\",\n" +
            "      \"ref_name\": \"$branchName\",\n" +
            "      \"selector\": {\n" +
            "        \"type\": \"custom\",\n" +
            "        \"pattern\": \"mvn-install-deploy\"\n" +
            "      }\n" +
            "    }\n "+
            "  }"

    println(reqBody)

    val client = HttpClientBuilder.create().build()
    val request = HttpPost("https://api.bitbucket.org/2.0/repositories/dossiersolutions/dossier-profile/pipelines/")
    request.addHeader("Content-Type", "application/json")
    request.addHeader("Authorization", "Bearer " + authorization?.accessToken)
    val requestEntity = StringEntity(reqBody, ContentType.APPLICATION_JSON)
    request.entity = requestEntity
    val response: CloseableHttpResponse? = client.execute(request)

    println(response)
}

fun getAllBranches(): String {
    val consumer = Consumer("", "")
    val authorization = getAccessToken(consumer)

    val client = HttpClientBuilder.create().build()
    val request = HttpGet("https://api.bitbucket.org/2.0/repositories/dossiersolutions/dossier-profile/refs/branches?pagelen=100")

    request.addHeader("Content-Type", "application/json")
    request.addHeader("Authorization", "Bearer " + authorization?.accessToken)

    val response: CloseableHttpResponse? = client.execute(request)
    return IOUtils.toString(response?.entity?.content, "UTF-8")
}