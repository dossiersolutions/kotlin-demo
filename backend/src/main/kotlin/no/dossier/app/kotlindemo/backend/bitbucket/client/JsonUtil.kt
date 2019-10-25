package no.dossier.app.kotlindemo.backend.bitbucket.client

import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.TextNode
import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.ParseContext
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider
import java.util.ArrayList

class JsonUtil(jsonStr: String) {
    val documentContext: DocumentContext;

    init {
        val parseContext = getParseContext()
        documentContext = parseContext.parse(jsonStr)

    }

    fun getJsonValue(jsonPath: String): String {
        return getJsonPathString(jsonPath, "")
    }

    fun split(jsonPath: String): List<String> {
        val dataItems = ArrayList<String>()
        val jsonArray = documentContext.read<ArrayNode>(jsonPath)
        for (node in jsonArray) {
            dataItems.add(node.toString())
        }
        return dataItems
    }

    fun getJsonPathString(jsonPathSelector: String, defaultValue: String): String {
        try {
            val value: TextNode = documentContext.read(jsonPathSelector)
            return value.asText()
        } catch (e: Exception) {
            println(e)
            return defaultValue
        }

    }

    private fun getParseContext(): ParseContext {
        val config = Configuration.builder()
                .jsonProvider(JacksonJsonNodeJsonProvider())
                .mappingProvider(JacksonMappingProvider())
                .build()

        return JsonPath.using(config)
    }
}

//fun main() {
//    val json = "{\n" +
//            "    \"pagelen\": 1,\n" +
//            "    \"size\": 85,\n" +
//            "    \"values\": [\n" +
//            "        {\n" +
//            "            \"name\": \"bugfix/PF-7278-fix-disable-plan-tool-so-disable\",\n" +
//            "            \"links\": {\n" +
//            "                \"commits\": {\n" +
//            "                    \"href\": \"https://api.bitbucket.org/2.0/repositories/dossiersolutions/dossier-profile/commits/bugfix/PF-7278-fix-disable-plan-tool-so-disable\"\n" +
//            "                },\n" +
//            "                \"self\": {\n" +
//            "                    \"href\": \"https://api.bitbucket.org/2.0/repositories/dossiersolutions/dossier-profile/refs/branches/bugfix/PF-7278-fix-disable-plan-tool-so-disable\"\n" +
//            "                },\n" +
//            "                \"html\": {\n" +
//            "                    \"href\": \"https://bitbucket.org/dossiersolutions/dossier-profile/branch/bugfix/PF-7278-fix-disable-plan-tool-so-disable\"\n" +
//            "                }\n" +
//            "            },\n" +
//            "            \"default_merge_strategy\": \"fast_forward\",\n" +
//            "            \"merge_strategies\": [\n" +
//            "                \"merge_commit\",\n" +
//            "                \"squash\",\n" +
//            "                \"fast_forward\"\n" +
//            "            ],\n" +
//            "            \"type\": \"branch\",\n" +
//            "            \"target\": {\n" +
//            "                \"hash\": \"89335b0edd975d0c35a9b061fb9696ea737485bf\",\n" +
//            "                \"repository\": {\n" +
//            "                    \"links\": {\n" +
//            "                        \"self\": {\n" +
//            "                            \"href\": \"https://api.bitbucket.org/2.0/repositories/dossiersolutions/dossier-profile\"\n" +
//            "                        },\n" +
//            "                        \"html\": {\n" +
//            "                            \"href\": \"https://bitbucket.org/dossiersolutions/dossier-profile\"\n" +
//            "                        },\n" +
//            "                        \"avatar\": {\n" +
//            "                            \"href\": \"https://bytebucket.org/ravatar/%7Bd115e9ad-3c45-49fa-b348-6512e9ba4f3d%7D?ts=2307926\"\n" +
//            "                        }\n" +
//            "                    },\n" +
//            "                    \"type\": \"repository\",\n" +
//            "                    \"name\": \"Dossier ProFile\",\n" +
//            "                    \"full_name\": \"dossiersolutions/dossier-profile\",\n" +
//            "                    \"uuid\": \"{d115e9ad-3c45-49fa-b348-6512e9ba4f3d}\"\n" +
//            "                },\n" +
//            "                \"links\": {\n" +
//            "                    \"self\": {\n" +
//            "                        \"href\": \"https://api.bitbucket.org/2.0/repositories/dossiersolutions/dossier-profile/commit/89335b0edd975d0c35a9b061fb9696ea737485bf\"\n" +
//            "                    },\n" +
//            "                    \"comments\": {\n" +
//            "                        \"href\": \"https://api.bitbucket.org/2.0/repositories/dossiersolutions/dossier-profile/commit/89335b0edd975d0c35a9b061fb9696ea737485bf/comments\"\n" +
//            "                    },\n" +
//            "                    \"patch\": {\n" +
//            "                        \"href\": \"https://api.bitbucket.org/2.0/repositories/dossiersolutions/dossier-profile/patch/89335b0edd975d0c35a9b061fb9696ea737485bf\"\n" +
//            "                    },\n" +
//            "                    \"html\": {\n" +
//            "                        \"href\": \"https://bitbucket.org/dossiersolutions/dossier-profile/commits/89335b0edd975d0c35a9b061fb9696ea737485bf\"\n" +
//            "                    },\n" +
//            "                    \"diff\": {\n" +
//            "                        \"href\": \"https://api.bitbucket.org/2.0/repositories/dossiersolutions/dossier-profile/diff/89335b0edd975d0c35a9b061fb9696ea737485bf\"\n" +
//            "                    },\n" +
//            "                    \"approve\": {\n" +
//            "                        \"href\": \"https://api.bitbucket.org/2.0/repositories/dossiersolutions/dossier-profile/commit/89335b0edd975d0c35a9b061fb9696ea737485bf/approve\"\n" +
//            "                    },\n" +
//            "                    \"statuses\": {\n" +
//            "                        \"href\": \"https://api.bitbucket.org/2.0/repositories/dossiersolutions/dossier-profile/commit/89335b0edd975d0c35a9b061fb9696ea737485bf/statuses\"\n" +
//            "                    }\n" +
//            "                },\n" +
//            "                \"author\": {\n" +
//            "                    \"raw\": \"Audun Øygard <audun.jacobsen.oygard@dossier.no>\",\n" +
//            "                    \"type\": \"author\",\n" +
//            "                    \"user\": {\n" +
//            "                        \"display_name\": \"Audun Jacobsen Øygard\",\n" +
//            "                        \"uuid\": \"{4ead5a86-0f27-4e15-b85b-c875af22dd82}\",\n" +
//            "                        \"links\": {\n" +
//            "                            \"self\": {\n" +
//            "                                \"href\": \"https://api.bitbucket.org/2.0/users/%7B4ead5a86-0f27-4e15-b85b-c875af22dd82%7D\"\n" +
//            "                            },\n" +
//            "                            \"html\": {\n" +
//            "                                \"href\": \"https://bitbucket.org/%7B4ead5a86-0f27-4e15-b85b-c875af22dd82%7D/\"\n" +
//            "                            },\n" +
//            "                            \"avatar\": {\n" +
//            "                                \"href\": \"https://avatar-management--avatars.us-west-2.prod.public.atl-paas.net/557058:95054164-c0d8-4e8a-a784-19c819bce581/abb5c464-15ea-448f-82e3-4a6eaaa54e41/128\"\n" +
//            "                            }\n" +
//            "                        },\n" +
//            "                        \"nickname\": \"Audun Jacobsen Øygard\",\n" +
//            "                        \"type\": \"user\",\n" +
//            "                        \"account_id\": \"557058:95054164-c0d8-4e8a-a784-19c819bce581\"\n" +
//            "                    }\n" +
//            "                },\n" +
//            "                \"parents\": [\n" +
//            "                    {\n" +
//            "                        \"hash\": \"a046d961b29e4fd6dda5f8df5b4d2106527c66d7\",\n" +
//            "                        \"type\": \"commit\",\n" +
//            "                        \"links\": {\n" +
//            "                            \"self\": {\n" +
//            "                                \"href\": \"https://api.bitbucket.org/2.0/repositories/dossiersolutions/dossier-profile/commit/a046d961b29e4fd6dda5f8df5b4d2106527c66d7\"\n" +
//            "                            },\n" +
//            "                            \"html\": {\n" +
//            "                                \"href\": \"https://bitbucket.org/dossiersolutions/dossier-profile/commits/a046d961b29e4fd6dda5f8df5b4d2106527c66d7\"\n" +
//            "                            }\n" +
//            "                        }\n" +
//            "                    }\n" +
//            "                ],\n" +
//            "                \"date\": \"2019-10-23T08:19:49+00:00\",\n" +
//            "                \"message\": \"PF-7278: Employment can be disabled even if you disable a plan (for the disable plan type tool).\\n\",\n" +
//            "                \"type\": \"commit\"\n" +
//            "            }\n" +
//            "        }\n" +
//            "    ],\n" +
//            "    \"page\": 1,\n" +
//            "    \"next\": \"https://api.bitbucket.org/2.0/repositories/dossiersolutions/dossier-profile/refs/branches?pagelen=1&page=2\"\n" +
//            "}"
//    val ju = JsonUtil(json)
//
//    val branchesJson = ju.split("$.values")
//
//    println(branchesJson.size)
//
//    for (branchJson in branchesJson) {
//        val branchJu = JsonUtil(branchJson)
//        val bout2 = branchJu.getJsonPathString("$.name", "");
//        println(bout2)
//
//    }
//}