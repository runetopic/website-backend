val ktorVersion by extra("1.6.7")
val koinVersion by extra("3.1.4")

plugins {
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.serialization") version "1.6.0"
    application
}

group = "com.runetopic.website"
version = "1.0.0-SNAPSHOT"

application {
    mainClass.set("com.runetopic.ApplicationKt")
}

repositories(RepositoryHandler::mavenCentral)

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-jackson:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.2.8")

    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-ktor:$koinVersion")
}

tasks.withType<Test> {
    dependencies {
        testImplementation("org.jetbrains.kotlin:kotlin-test")
        testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
        testImplementation("io.mockk:mockk:1.12.1")
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
