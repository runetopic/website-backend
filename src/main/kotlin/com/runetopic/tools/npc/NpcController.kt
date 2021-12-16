package com.runetopic.tools.npc

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
class NpcController(
    routing: Routing
) : KoinComponent {

    init {
        val npcService by inject<NpcService>()

        with(routing) {
            authenticate("logged-in") {
                route("/api/tools/npcs") {
                    get {
                        val sorted = call.request.queryParameters["sorted"] == true.toString()
                        call.respond(npcService.all(sorted))
                    }
                    post {
                        val npc = call.receive<Npc>()
                        npcService.add(npc)
                        call.respond(HttpStatusCode.Created)
                    }
                }
            }
        }
    }
}
