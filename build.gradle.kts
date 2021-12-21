val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_version: String by project
val mockk_version: String by project
val kmongo_version: String by project

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    application
}

group = "com.runetopic.website"
version = "1.0.0-SNAPSHOT"

tasks {
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "com.runetopic.ApplicationKt"))
        }
    }
}

application {
    mainClass.set("com.runetopic.ApplicationKt")
}

repositories(RepositoryHandler::mavenCentral)

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-auth-jwt:$ktor_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")
    implementation("io.ktor:ktor-jackson:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    implementation("org.litote.kmongo:kmongo-async:$kmongo_version")
    implementation("org.litote.kmongo:kmongo-coroutine:$kmongo_version")
    implementation("org.litote.kmongo:kmongo-reactor:$kmongo_version")
    implementation("org.litote.kmongo:kmongo-id:$kmongo_version")

    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")
}

tasks.withType<Test> {
    dependencies {
        testImplementation("org.jetbrains.kotlin:kotlin-test")
        testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
        testImplementation("io.mockk:mockk:$mockk_version")
    }
}

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs = listOf("-Xopt-in=kotlin.time.ExperimentalTime")
    }
}
