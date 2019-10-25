package no.dossier.app.kotlindemo.backend.bitbucket.client

import no.dossier.app.kotlindemo.backend.bitbucket.auth.Authorization
import no.dossier.app.kotlindemo.backend.bitbucket.auth.Consumer
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.impl.client.HttpClientBuilder
import kotlinx.serialization.json.*
import org.apache.commons.io.IOUtils
import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.message.BasicNameValuePair
import java.util.ArrayList


fun getAccessToken(consumer: Consumer): Authorization? {
    val client = HttpClientBuilder.create().build()
    val request = HttpPost(consumer.authorizationUrl)

    request.addHeader("Content-Type", "application/x-www-form-urlencoded")
    request.addHeader(consumer.authHeader.first, consumer.authHeader.second)

    val params = ArrayList<NameValuePair>()
    params.add(BasicNameValuePair(consumer.grantType.first, consumer.grantType.second))
    request.entity = UrlEncodedFormEntity(params)

    val response: CloseableHttpResponse? = client.execute(request)
    val rawResponse: String = IOUtils.toString(response?.entity?.content,"UTF-8")
    val json = Json(JsonConfiguration.Stable)
    println("rawResponse")
    println(rawResponse)
    return json.parse(Authorization.serializer(), rawResponse)

}
