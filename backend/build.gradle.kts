import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

group = Project.groupName
version = Project.version
java.sourceCompatibility = JavaVersion.VERSION_1_8

plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	kotlin("jvm")
	kotlin("plugin.spring")
}

repositories {
	mavenCentral()
	jcenter()
	maven { url = uri(Repositories.spring_snapshot) }
	maven { url = uri(Repositories.spring_milestone) }
	maven { url = uri(Repositories.kotlin_kotlinx) }
}

kotlin {
	sourceSets {
		val main by getting {
			dependencies {
				implementation(project(Modules.common))
				implementation(kotlin("reflect"))
				implementation(kotlin("stdlib-jdk8"))
				implementation(Dependencies.spring_boot_starter)
				implementation(Dependencies.spring_boot_starter_web)
				implementation(Dependencies.spring_boot_starter_websocket)
				implementation(Dependencies.jackson_module_kotlin)
				implementation(Dependencies.kotlinx_serialization_runtime_jvm)
				implementation("org.springframework.boot:spring-boot-devtools")
				implementation(Dependencies.klock_jvm)
			}
		}
		val test by getting {
			dependencies {
				implementation(Dependencies.spring_boot_starter_test)
			}
		}
	}
}

tasks {
	withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "1.8"
		}
	}

	val copyTaskName = "copyFeResources"
	register(copyTaskName, Copy::class) {
		val frontendProjectPath = rootProject.childProjects[Modules.frontend.trim(':')]!!.projectDir.path
		from("$frontendProjectPath/build/distributions")
		from("$frontendProjectPath/src/main/resources")
		into("build/resources/main/static")
		dependsOn(Modules.frontend + ":browserWebpack")
		dependsOn(Modules.frontend + ":build")
	}

	withType<BootJar> {
		dependsOn(copyTaskName)
	}

	withType<BootRun> {
		dependsOn(copyTaskName)
	}
}