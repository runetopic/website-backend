# RuneTopic Website Backend API
The backend of the RuneTopic website.

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