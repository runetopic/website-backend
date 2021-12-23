import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.github.johnrengelman.shadow")
    application
}

group = "com.runetopic.website"
version = "1.0.0-SNAPSHOT"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories(RepositoryHandler::mavenCentral)

dependencies {
    implementation(kotlin("stdlib"))
    implementation(deps.bundles.ktor)
    implementation(deps.bundles.koin)
    implementation(deps.bundles.kmongo)
    implementation(deps.jackson.datatype.jsr310)
    implementation(deps.argon2)
}

with(tasks) {
    withType<Test> {
        dependencies {
            testImplementation("org.jetbrains.kotlin:kotlin-test")
            testImplementation(deps.ktor.server.test.host)
            testImplementation(deps.mockk)
        }
    }
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs = listOf("-Xopt-in=kotlin.time.ExperimentalTime")
    }
    withType<ShadowJar> {
        manifest {
            attributes(Pair("Main-Class", "io.ktor.server.netty.EngineMain"))
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
