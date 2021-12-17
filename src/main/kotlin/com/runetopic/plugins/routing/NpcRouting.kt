package com.runetopic.plugins.routing

import com.runetopic.Authentications
import com.runetopic.api.tools.npc.Npc
import com.runetopic.api.tools.npc.NpcService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureNpcRouting() {

    val npcService by inject<NpcService>()

    routing {
        authenticate(Authentications.LOGGED_IN) {
            get("/api/tools/npcs") {
                val sorted = call.request.queryParameters["sorted"] == true.toString()
                call.respond(npcService.all(sorted))
            }
            post("/api/tools/npcs") {
                val npc = call.receive<Npc>()
                npcService.add(npc)
                call.respond(HttpStatusCode.Created)
            }
        }
    }
}