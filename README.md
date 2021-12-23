# RuneTopic Website Backend API
The backend of the RuneTopic website.

## Example Configuration
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
    secret = (JWT_SECRET_KEY)
}

mongodb {
    driver = "mongodb://localhost:27017"
}
```