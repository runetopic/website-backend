package com.runetopic.tools.obj

import com.runetopic.Authentications
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author Jordan Abraham
 */
class ObjController(
    routing: Routing
) : KoinComponent {

    init {
        val objService by inject<ObjService>()

        with(routing) {
            authenticate(Authentications.LOGGED_IN) {
                route("/api/tools/objs") {
                    get {
                        val sorted = call.request.queryParameters["sorted"] == true.toString()
                        call.respond(objService.all(sorted))
                    }
                    post {
                        val obj = call.receive<Obj>()
                        objService.add(obj)
                        call.respond(HttpStatusCode.Created)
                    }
                }
            }
        }
    }
}
