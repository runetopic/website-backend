package com.runetopic.plugins.routing

import com.runetopic.Authentications
import com.runetopic.api.tools.obj.Obj
import com.runetopic.api.tools.obj.ObjService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureObjRouting() {

    val objService by inject<ObjService>()

    routing {
        authenticate(Authentications.LOGGED_IN) {
            get("/api/tools/objs") {
                val sorted = call.request.queryParameters["sorted"] == true.toString()
                if (sorted) {
                    call.respond(objService.findSorted(Obj::id))
                } else {
                    call.respond(objService.find<Obj>())
                }
            }
            post("/api/tools/objs") {
                val obj = call.receive<Obj>()
                objService.add(obj)
                call.respond(HttpStatusCode.Created)
            }
        }
    }
}
