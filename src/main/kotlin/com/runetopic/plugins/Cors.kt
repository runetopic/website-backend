import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*

fun Application.installCors() {
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header(HttpHeaders.AccessControlAllowOrigin)
        allowNonSimpleContentTypes = true
        allowCredentials = true
        allowSameOrigin = true
        host("localhost:3000", listOf("http", "https")) // frontendHost might be "*"
    }
}
