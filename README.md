# RuneTopic Website Backend API
[![Discord](https://img.shields.io/discord/212385463418355713?color=%237289DA&logo=Discord&logoColor=%237289DA)](https://discord.gg/3scgBkrfMG)

## Powered By
- Kotlin (https://github.com/JetBrains/kotlin)
- Ktor (https://github.com/ktorio/ktor)
- Koin (https://github.com/InsertKoinIO/koin)
- KMongo (https://github.com/Litote/kmongo)
- Jackson (https://github.com/FasterXML/jackson)
- Docker Compose (https://github.com/docker/compose)

## Example Configuration
For security reasons, we do not push our own. You will have to setup your own configuration file like the example provided below.

```/resource/application.conf```
```
ktor {
    development = true
    deployment {
        port = 8081
        port = ${?PORT}
        watch = [ classes, resources ]
    }
    application {
        modules = [ com.runetopic.ApplicationKt.module ]
    }
}

jwt {
    secret = a-generated-token-goes-here
}

mongodb {
    driver = "mongodb://localhost:27017"
}
```