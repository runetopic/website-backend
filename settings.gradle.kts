rootProject.name = "website-backend"

enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    defaultLibrariesExtensionName.set("deps")
    repositories(RepositoryHandler::mavenCentral)
}

pluginManagement {
    plugins {
        kotlin("jvm") version "1.6.10"
        kotlin("plugin.serialization") version "1.6.10"
        id("com.github.johnrengelman.shadow") version "7.0.0"
    }
}
