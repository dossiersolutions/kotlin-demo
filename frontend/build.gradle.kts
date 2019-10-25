group = Project.groupName
version = Project.version

plugins {
    kotlin("js")
}

repositories {
    mavenCentral()
    maven { url = uri(Repositories.kotlin_kotlinDev) }
    maven { url = uri(Repositories.kotlin_kotlinx) }
    maven { url = uri(Repositories.kotlinx_kotlinx) }
    maven { url = uri(Repositories.kotlinx_kotlinJsWrappers) }
}

kotlin {
    target {
        compilations["main"].kotlinOptions {
            sourceMap = true
            moduleKind = "commonjs"
            metaInfo = true
        }
        browser()
    }

    sourceSets {
        val main by getting {
            dependencies {
                implementation(project(Modules.common))
                implementation(kotlin("stdlib-js"))
                implementation(npm("core-js", Versions.core_js))
                implementation(npm("react", Versions.react))
                implementation(npm("react-dom", Versions.react))
                implementation(npm("inline-style-prefixer"))
                implementation(npm("styled-components"))
                compileOnly(Dependencies.kotlin_react)
                compileOnly(Dependencies.kotlin_react_dom)
                implementation(Dependencies.kotlinx_serialization_runtime_js)
                implementation(Dependencies.kotlin_styled)
            }
        }
    }
}